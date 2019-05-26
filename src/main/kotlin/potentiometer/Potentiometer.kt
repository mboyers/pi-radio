package org.boyers.radio.potentiometer

import org.boyers.radio.actor.Actor
import org.slf4j.LoggerFactory

class Potentiometer constructor(val channel: Int,
                                private val tolerance: Int,
                                maxValue: Int,
                                private val ignoreFiringOnStartup: Boolean,
                                private val actor: Actor) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private val erraticJumpDistance = 20

    private var rawValue = Integer.MAX_VALUE
    private var adjustmentDivisor: Double = 1024.0 / maxValue
    private var stabilized = false
    private var notChangedCount = 0
    private var erraticJumpIgnoreJustHappened = false

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

        if (difference > erraticJumpDistance && !erraticJumpIgnoreJustHappened) {
            log.debug("Ignoring new value of {} as it is greater than {} and considered erratic", newValue, erraticJumpDistance)
            erraticJumpIgnoreJustHappened = true
            return
        }
        erraticJumpIgnoreJustHappened = false

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