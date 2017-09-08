package org.boyers.radio.potentiometer

import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.boyers.radio.actor.Actor

@Slf4j
@ToString
class Potentiometer {
    int channel
    int tolerance = 1
    int jumpTolerance = Integer.MAX_VALUE
    Boolean ignoreFiringOnStartup = false
    Actor actor

    private int rawValue = Integer.MAX_VALUE
    private def adjustmentDivisor
    private boolean stabilized = false
    private int notChangedCount = 0

    void setMaxValue(int maxValue) {
        adjustmentDivisor = 1024.0 / maxValue
        log.info('Set adjustmentDivisor to {}', adjustmentDivisor)
    }

    int getAdjustedValue() {
        rawValue / adjustmentDivisor
    }

    void update(int newValue) {

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

        int difference = Math.abs(newValue - rawValue)

        // Sometimes pots report wildly, if the value seems too far off, ignore it
        if (difference > jumpTolerance) {
            log.info('Ignoring value {} because it is outside of jump tolerance of {}', newValue, jumpTolerance)
            return
        }

        if (difference > tolerance) {
            acceptNewValue(newValue)
        } else {
            checkStabilization(newValue)
            log.trace('New value of {} must not be different enough', newValue)

        }
    }

    private boolean isInValidRange(int value) {
        value >= 0 && value <= 1024
    }

    private void acceptNewValue(int newValue) {
        log.debug('Channel {} changing raw value from {} to {}', channel, rawValue, newValue)
        rawValue = newValue
        actor.handleChange(adjustedValue)
        stabilized = false
        notChangedCount = 0
    }

    // Even though a value might be in the tolerance, we might "center" it
    private checkStabilization(int newValue) {
        if (stabilized) {
            return
        }

        if (notChangedCount++ > 20) {
            rawValue = newValue
            log.info('Channel {} stabilized at {}', channel, rawValue)
            stabilized = true
        }
    }
}