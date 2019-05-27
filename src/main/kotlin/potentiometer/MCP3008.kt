package org.boyers.radio.potentiometer

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.Exception

/**
 * This code was taken from the example here: http://www.lediouris.net/RaspberryPI/ADC/readme.html
 * and converted to kotlin.
 *
 * There are higher-level abstractions provided by pi4j that I have tried to use without success.  An
 * example is here: https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/MCP3008GpioExample.java
 * It compiles and runs, but reads all zeroes.  Even after installed the pi4j libraries and examples separately
 * to the pi via apt, and using their shell script to ensure the classpath is complete and correct.
 */
@Service
@Profile("prod")
class MCP3008 @Autowired constructor(val potentiometers: List<Potentiometer>){

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private val spiClk  = RaspiPin.GPIO_01 // Pin #18, clock
    private val spiMiso = RaspiPin.GPIO_04 // Pin #23, data in.  MISO: Master In Slave Out
    private val spiMosi = RaspiPin.GPIO_05 // Pin #24, data out. MOSI: Master Out Slave In
    private val spiCs   = RaspiPin.GPIO_06 // Pin #25, Chip Select

    private val misoInput: GpioPinDigitalInput
    private val mosiOutput: GpioPinDigitalOutput
    private val clockOutput: GpioPinDigitalOutput
    private val chipSelectOutput: GpioPinDigitalOutput

    init {
        val gpio = GpioFactory.getInstance()
        mosiOutput = gpio.provisionDigitalOutputPin(spiMosi,"MOSI", PinState.LOW)
        clockOutput = gpio.provisionDigitalOutputPin(spiClk,"CLK",  PinState.LOW)
        chipSelectOutput = gpio.provisionDigitalOutputPin(spiCs,"CS", PinState.LOW)
        misoInput = gpio.provisionDigitalInputPin(spiMiso, "MISO")

        log.info("MCP3008 initialized.  Found {} potentiometers", potentiometers.size)
    }

    @Scheduled(fixedRate = 100L)
    fun readPotentiometers() {
        for (potentiometer in potentiometers) {
            try {
                potentiometer.update(readAdc(potentiometer.channel))
            } catch (e: Exception) {
                log.error("Exception occurred updating pot {}: ", potentiometer, e)
            }
        }
    }

    private fun readAdc(channelId: Int): Int {
        chipSelectOutput.high()

        clockOutput.low()
        chipSelectOutput.low()

        var adcCommand = channelId
        adcCommand = adcCommand.or(0x18)

        adcCommand  = adcCommand.shl(3)
        // Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
        (1..5).forEach {
            if ((adcCommand.and(0x80) != 0x0)) {
                mosiOutput.high()
            } else {
                mosiOutput.low()
            }

            adcCommand = adcCommand.shl(1)
            clockOutput.high()
            clockOutput.low()
        }

        // Read in one empty bit, one null bit and 10 ADC bits
        var adcOut = 0
        (1..12).forEach {
            clockOutput.high()
            clockOutput.low()
            adcOut = adcOut.shl(1)

            if (misoInput.isHigh()) {
                // Shift one bit on the adcOut
                adcOut = adcOut.or(0x1)
            }
        }

        chipSelectOutput.high()

        adcOut = adcOut.shr(1) // Drop first bit

        log.trace("Read {} from channel {}", adcOut, channelId)

        return adcOut
    }
}