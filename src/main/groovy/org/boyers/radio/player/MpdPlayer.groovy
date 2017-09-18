package org.boyers.radio.player

import groovy.util.logging.Slf4j
import org.bff.javampd.MPD
import org.bff.javampd.objects.MPDSong
import org.boyers.radio.model.Station
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Slf4j
@Profile('prod')
@Service('player')
class MpdPlayer implements Player {

    private MPD mpd
    private Station nowPlayingStation
    private String nowPlayingSong
    private TitleScrubber titleScrubber = new TitleScrubber()

    MpdPlayer() {
        log.info('Building player...')
        MPD.Builder builder = new MPD.Builder()
        mpd = builder.build()

        // Set repeat to true so static doesn't end if someone takes a long time finding a station
        mpd.player.repeat = true
    }

    void setVolume(Integer newVolume) {
        mpd.player.volume = newVolume
        log.info('Set volume to {}', newVolume)
    }

    void playStatic() {
        nowPlayingStation = null
        playThis('radio-static.mp3')
    }

    void playStation(Station station) {
        nowPlayingStation = station
        playThis(station.uri)
    }

    private void playThis(String itemToPlay) {
        if (alreadyPlayingThis(itemToPlay)) {
            return
        }

        mpd.playlist.clearPlaylist()

        MPDSong mpdSong = new MPDSong()
        mpdSong.file = itemToPlay
        mpd.playlist.addSong(mpdSong)

        mpd.player.play()

        nowPlayingSong = itemToPlay

        log.info('Played {}', itemToPlay)
    }

    boolean alreadyPlayingThis(String item) {
        nowPlayingSong == item
    }

    String getNowPlayingStation() {
        if (nowPlayingStation) {
            nowPlayingStation.name
        } else {
            '.....Static.....'
        }
    }

    String getNowPlayingSong() {
        String cleansedTitle = titleScrubber.getCleansedArtistAndTitle(mpd.player.currentSong)
        log.info('now playing: {}', cleansedTitle)
        cleansedTitle
    }

    void pause() {
        mpd.player.pause()
    }

    void play() {
        mpd.player.play()
    }

    @Scheduled(fixedRate = 5_000L)
    void ping() {
        log.trace('Keeping mpd connection alive')
        mpd.player.status
    }

}