package org.boyers.radio.potentiometer

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Slf4j
@Service
@Profile('test')
class FakeMCP3008 {

    @Autowired
    List<Potentiometer> potentiometers

    //@PostConstruct
    void run() {
        int incrementor = 0
        while (true) {
            for(Potentiometer potentiometer : potentiometers) {
                potentiometer.update(incrementor++)
                if (incrementor > 1024) {
                    incrementor = 0
                }
            }

            Thread.sleep(500L)
        }
    }
}