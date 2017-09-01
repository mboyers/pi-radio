package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
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
    List<Potentiometer> potentiometers

    @RequestMapping('/setVolume')
    @ResponseBody
    String setVolume(@RequestParam int value) {
        potentiometers.get(0).update(value)
        value
    }

    @RequestMapping('/setTuner')
    @ResponseBody
    String setTuner(@RequestParam int value) {
        potentiometers.get(1).update(value)
        value
    }

    @RequestMapping('/getTuner')
    @ResponseBody
    String getTuner() {
        potentiometers.get(1).adjustedValue
    }
}
