package org.boyers.radio.player

import org.bff.javampd.MPD
import org.bff.javampd.objects.MPDSong
import org.boyers.radio.model.Station
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import player.TitleScrubber

@Service
@Profile("prod")
class MpdPlayer: Player {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private final val mpd: MPD
    private final val staticStation = Station("radio-static.mp3", ".....Static.....", 0)
    private var nowPlayingStation = Station("", "", 0)

    init {
        log.info("Building player...")
        mpd = MPD.Builder().build()

        // Set repeat to true so static doesn't end if someone takes a long time finding a station
        mpd.player.isRepeat = true
    }

    override fun setVolume(newVolume: Int) {
        mpd.player.volume = newVolume
        log.debug("Set volume to {}", newVolume)
    }

    override fun playStatic() {
        playStation(staticStation)
    }

    override fun playStation(station: Station) {
        if (alreadyPlayingThis(station)) {
            log.debug("Already playing station {}, ignoring", station)
            return
        }

        mpd.playlist.clearPlaylist()
        val mpdSong = MPDSong()
        mpdSong.file = station.uri
        mpd.playlist.addSong(mpdSong)
        mpd.player.play()

        nowPlayingStation = station

        log.info("Played {}", station)
    }

    private fun alreadyPlayingThis(station: Station): Boolean {
        return station == nowPlayingStation
    }

    override fun getNowPlayingStation(): String {
        return nowPlayingStation.name
    }

    override fun getNowPlayingSong(): String {
        val dirtyTitle = TitleBuilder().buildTitle(mpd.player.currentSong)
        return TitleScrubber().scrub(dirtyTitle)
    }

    override fun pause() {
        mpd.player.pause()
    }

    override fun play() {
        mpd.player.play()
    }

    @Scheduled(fixedRate = 5_000L)
    fun ping() {
        log.trace("Keeping mpd connection alive")
        mpd.player.status
    }

}