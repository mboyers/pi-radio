package org.boyers.radio.potentiometer

import org.boyers.radio.actor.Actor
import org.slf4j.LoggerFactory

class Potentiometer constructor(val channel: Int,
                                val tolerance: Int,
                                maxValue: Int,
                                val ignoreFiringOnStartup: Boolean,
                                val actor: Actor) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var rawValue = Integer.MAX_VALUE
    private var adjustmentDivisor: Double
    private var stabilized = false
    private var notChangedCount = 0

    init {
        adjustmentDivisor = 1024.0 / maxValue
        log.info("Set adjustmentDivisor to {}", adjustmentDivisor)
    }

    private fun getAdjustedValue(): Int {
        return (rawValue / adjustmentDivisor).toInt()
    }

    fun update(newValue: Int) {

        // If it's not in the range of what is valid, ignore it
        if (!isInValidRange(newValue)) {
            log.warn("Ignoring invalid potentiometer value: {}", newValue)
            return
        }

        // If this is the first update, just take it
        if (rawValue == Integer.MAX_VALUE) {
            if (ignoreFiringOnStartup) {
                rawValue = newValue
            } else {
                acceptNewValue(newValue)
            }
            return
        }

        val difference = Math.abs(newValue - rawValue)

        if (difference > tolerance) {
            acceptNewValue(newValue)
        } else {
            checkStabilization(newValue)
            log.trace("New value of {} must not be different enough", newValue)

        }
    }

    private fun isInValidRange(value: Int): Boolean {
        return value in 0..1024
    }

    private fun acceptNewValue(newValue: Int) {
        log.debug("Channel {} changing raw value from {} to {}", channel, rawValue, newValue)
        rawValue = newValue
        actor.handleChange(getAdjustedValue())
        stabilized = false
        notChangedCount = 0
    }

    // Even though a value might be in the tolerance, we might "center" it
    private fun checkStabilization(newValue: Int) {
        if (stabilized) {
            return
        }

        if (notChangedCount++ > 20) {
            rawValue = newValue
            log.info("Channel {} stabilized at {}", channel, rawValue)
            stabilized = true
        }
    }
    
}