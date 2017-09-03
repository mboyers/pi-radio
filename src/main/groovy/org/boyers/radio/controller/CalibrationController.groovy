package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.config.TunePoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
@RequestMapping('/calibrate')
class CalibrationController {

    @Autowired
    @Qualifier('tunePoints')
    private List<Integer> tunePoints

    @Autowired
    private Map<Integer, TunePoint> tunePointMap

    @RequestMapping('')
    String presentCalibrationPage(Model model) {
        model.addAttribute('tunePoints', tunePoints)
        model.addAttribute('tunePointMap', tunePointMap)
        'calibrate'
    }

}
