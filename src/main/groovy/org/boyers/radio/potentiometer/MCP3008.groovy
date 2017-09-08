package org.boyers.radio.potentiometer

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Slf4j
@Service
@Profile('prod')
class MCP3008 {

    private Pin spiClk  = RaspiPin.GPIO_01 // Pin #18, clock
    private Pin spiMiso = RaspiPin.GPIO_04 // Pin #23, data in.  MISO: Master In Slave Out
    private Pin spiMosi = RaspiPin.GPIO_05 // Pin #24, data out. MOSI: Master Out Slave In
    private Pin spiCs   = RaspiPin.GPIO_06 // Pin #25, Chip Select

    private GpioPinDigitalInput  misoInput
    private GpioPinDigitalOutput mosiOutput
    private GpioPinDigitalOutput clockOutput
    private GpioPinDigitalOutput chipSelectOutput

    @Autowired
    List<Potentiometer> potentiometers

    MCP3008() {
        GpioController gpio = GpioFactory.getInstance()
        mosiOutput = gpio.provisionDigitalOutputPin(spiMosi,'MOSI', PinState.LOW)
        clockOutput = gpio.provisionDigitalOutputPin(spiClk,'CLK',  PinState.LOW)
        chipSelectOutput = gpio.provisionDigitalOutputPin(spiCs,'CS', PinState.LOW)
        misoInput = gpio.provisionDigitalInputPin(spiMiso, 'MISO')
    }

    @Scheduled(fixedRate = 500L)
    void readPotentiometers() {
        for (Potentiometer potentiometer : potentiometers) {
            try {
                potentiometer.update(readAdc(potentiometer.channel))
            } catch (Exception e) {
                log.error('Exception occurred updating pot {}: ', potentiometer, e)
            }
        }
    }

    private Integer readAdc(Integer channelId) {
        chipSelectOutput.high()

        clockOutput.low()
        chipSelectOutput.low()

        Integer adcCommand = channelId

        adcCommand |= 0x18 // 0x18: 00011000
        adcCommand <<= 3
        // Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
        5.times {
            if ((adcCommand & 0x80) != 0x0) // 0x80 = 0&10000000
                mosiOutput.high()
            else
                mosiOutput.low()

            adcCommand <<= 1
            clockOutput.high()
            clockOutput.low()
        }

        // Read in one empty bit, one null bit and 10 ADC bits
        Integer adcOut = 0
        12.times {
            clockOutput.high()
            clockOutput.low()
            adcOut <<= 1

            if (misoInput.isHigh()) {
                // Shift one bit on the adcOut
                adcOut |= 0x1
            }
        }

        chipSelectOutput.high()

        adcOut >>= 1 // Drop first bit

        log.trace('Read {} from channel {}', adcOut, channelId)

        return adcOut
    }
}