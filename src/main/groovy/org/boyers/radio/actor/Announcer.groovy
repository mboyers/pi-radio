package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.util.UriComponentsBuilder

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadPoolExecutor

@Slf4j
class Announcer implements Actor {

    @Autowired
    Player player

    @Autowired
    @Qualifier('announcerExecutor')
    ThreadPoolExecutor executor

    @Override
    void handleChange(Integer newValue) {
        def announcer = {
            makeAnnouncement()
        } as Runnable

        try {
            executor.execute(announcer)
        } catch (RejectedExecutionException e) {
            log.debug('Ignoring announcement request - already announcing')
        }
    }

    private makeAnnouncement() {
        try {
            player.pause()
            URL url = buildUrl("${player.nowPlayingSong}. ${player.nowPlayingStation}")
            Clip clip = AudioSystem.clip
            clip.open(getInputStream(url))
            clip.start()
            sleepWhileClipIsPlaying(clip)
            player.play()
            clip.close()
        } catch(Exception e) {
            log.error('Exception playing audio: ', e)
        }
    }

    private sleepWhileClipIsPlaying(Clip clip) {
        Integer sleepTime = clip.microsecondLength / 1000
        log.info('Sleeping for {} ms', sleepTime)
        Thread.sleep(sleepTime)
    }

    private InputStream getInputStream(URL url) {
        URLConnection connection = url.openConnection()
        InputStream response = connection.inputStream
        BufferedInputStream bufferedInputStream = new BufferedInputStream(response)
        AudioSystem.getAudioInputStream(bufferedInputStream)
    }

    private URL buildUrl(String textToSpeak) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl('http://api.voicerss.org/')
        builder.queryParam('key', 'bb1c82a4f0e7441fb641baf16e9cf195')
        builder.queryParam('hl', 'en-us')
        builder.queryParam('src', textToSpeak)
        builder.queryParam('c', 'wav')
        builder.queryParam('f', '16khz_16bit_mono')
        builder.build().encode().toUri().toURL()
    }
}
