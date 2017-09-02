package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
@RequestMapping('/calibrate')
class CalibrationController {

    // Though this is currently just a series of even numbers, in the future it might be less predictable (might want to include 47 for example)
    private List<Integer> tunePoints = [30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50]

    @RequestMapping('')
    String presentCalibrationPage(Model model) {
        model.addAttribute(tunePoints)
        'calibrate'
    }

}
