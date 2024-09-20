package org.boyers.radio.potentiometer

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Profile("test")
class DummyMCP3008 @Autowired constructor(val potentiometers: List<Potentiometer>){

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var incrementor: Int = 0

    @Scheduled(fixedRate = 500L)
    fun run() {
        log.trace("Dummy MPC fired")
        for (potentiometer in potentiometers) {
            log.trace("Dummy potentiometer triggering potentiometer {}", potentiometer)
            potentiometer.update(incrementor++)
            if (incrementor > 1024) {
                incrementor = 0
            }
        }
    }

}