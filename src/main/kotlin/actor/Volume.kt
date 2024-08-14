package org.boyers.radio.actor

import org.boyers.radio.player.Player
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadPoolExecutor

class Volume constructor(private val player: Player,
                         private val executor: ThreadPoolExecutor): Actor {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var currentVolume = 0
    
    override fun handleChange(newValue: Int) {
        val adjustedNewVolume = performLogarithmicAdjustment(newValue)

        // Slide to the new volume so it doesn't sound too abrupt
//        (currentVolume..adjustedNewVolume).forEach {
//            setVolume(it)
//        }
        setVolume(adjustedNewVolume)
    }

    private fun setVolume(newVolume: Int) {
        val runnable =  {
            player.setVolume(newVolume)
        }

        executor.execute(runnable)
        currentVolume = newVolume
        log.debug("Current volume: {}", currentVolume)
    }

    private fun performLogarithmicAdjustment(newVolume: Int): Int {
        var d = newVolume / 1024.0
        if (d == 0.0) {
            d += 0.001
        }

        val  l = Math.log(d) + 7

        return (l * 14.2857).toInt()
    }

}