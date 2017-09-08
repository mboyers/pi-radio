package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Contains methods for manually setting values of potentiometers for cases where the application
 * is running on a machine that doesn't have them.
 */
@Slf4j
@Controller
@Profile('test')
@RequestMapping('/pot')
class PotentiometerOverrideController {

    @Autowired
    @Qualifier('volumePot')
    Potentiometer volumePot

    @Autowired
    @Qualifier('tunerPot')
    Potentiometer tunerPot

    @RequestMapping('/setVolume')
    @ResponseBody
    void setVolume(@RequestParam Integer value) {
        volumePot.update(value)
    }

    @RequestMapping('/getVolume')
    @ResponseBody
    String getVolume() {
        volumePot.adjustedValue
    }

    @RequestMapping('/setTuner')
    @ResponseBody
    void setTuner(@RequestParam Integer value) {
        tunerPot.update(value)
    }

    @RequestMapping('/getTuner')
    @ResponseBody
    String getTuner() {
        tunerPot.adjustedValue
    }
}
