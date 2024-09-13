package org.boyers.radio.actor

import org.boyers.radio.player.Player
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.util.UriComponentsBuilder
import java.io.BufferedInputStream
import java.net.URL
import java.util.concurrent.ThreadPoolExecutor
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class Announcer constructor(private val player: Player,
                            private val executor: ThreadPoolExecutor): Actor {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @Value("\${voicerss.key}")
    private final val voiceRssKey: String? = null

    override fun handleChange(newValue: Int) {
        val runnable =  {
            makeAnnouncement()
        }

        try {
            executor.execute(runnable)
        } catch (e: Exception) {
            // Logging this at info since it's somewhat expected if someone keeps twisting the knob while an announcement is in progress
            log.info("Ignoring announcement request - already announcing")
        }
    }

    private fun makeAnnouncement() {
        try {
            player.pause()
            val url = buildUrl("${player.getNowPlayingSong()}. ${player.getNowPlayingStation()}")
            val clip = AudioSystem.getClip()
            clip.open(getInputStream(url))
            clip.start()
            sleepWhileAnnouncementClipIsPlaying(clip)
            player.play()
            clip.stop()
        } catch (e: Exception) {
            log.error("Exception making announcement: ", e)
        }

    }

    private fun sleepWhileAnnouncementClipIsPlaying(clip: Clip) {
        val sleepTime = clip.microsecondLength / 1000
        log.info("Sleeping for {} ms", sleepTime)
        Thread.sleep(sleepTime)
    }

    private fun getInputStream(url: URL): AudioInputStream {
        val connection = url.openConnection()
        val response = connection.getInputStream()
        val bufferedInputStream = BufferedInputStream(response)
        return AudioSystem.getAudioInputStream(bufferedInputStream)
    }

    private fun buildUrl(textToSpeak: String): URL {
        val builder = UriComponentsBuilder.fromHttpUrl("http://api.voicerss.org/")
        builder.queryParam("key", voiceRssKey)
        builder.queryParam("hl", "en-us")
        builder.queryParam("v", "amy")
        builder.queryParam("src", textToSpeak)
        builder.queryParam("c", "wav")
        builder.queryParam("f", "16khz_16bit_mono")
        return builder.build().encode().toUri().toURL()
    }
}