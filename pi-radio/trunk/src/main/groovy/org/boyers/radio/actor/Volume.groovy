package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
class Volume implements Actor {

    @Autowired
    Player player

    @Override
    void handleChange(int newVolume) {
        player.setVolume(performLogarithmicAdjustment(newVolume))
    }

    private int performLogarithmicAdjustment(int newVolume) {
        def d = newVolume / 1024.0
        if (!d) {
            d = d + 0.001
        }
        def l = Math.log(d) + 7
        l * 14.2857
    }
}
