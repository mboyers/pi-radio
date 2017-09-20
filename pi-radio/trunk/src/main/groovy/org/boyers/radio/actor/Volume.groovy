package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import java.util.concurrent.ThreadPoolExecutor

@Slf4j
class Volume implements Actor {

    @Autowired
    Player player

    @Autowired
    @Qualifier('volumeExecutor')
    ThreadPoolExecutor executor

    Integer currentVolume = 0

    @Override
    void handleChange(Integer newVolume) {

        Integer adjustedNewVolume = performLogarithmicAdjustment(newVolume)

        // Slide to the new volume so it doesn't sound too abrupt
        (currentVolume..adjustedNewVolume).each {
            setVolume(it)
        }
    }

    private void setVolume(Integer newVolume) {
        Runnable volumeChanger = {
            player.setVolume(newVolume)
        } as Runnable

        executor.execute(volumeChanger)
        currentVolume = newVolume
    }

    private Integer performLogarithmicAdjustment(Integer newVolume) {
        def d = newVolume / 1024.0
        if (!d) {
            d = d + 0.001
        }
        def l = Math.log(d) + 7
        l * 14.2857
    }
}
