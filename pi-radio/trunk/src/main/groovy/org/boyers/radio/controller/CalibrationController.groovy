package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.config.CalibrationPersister
import org.boyers.radio.config.TunePoint
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@RequestMapping('/calibrate')
class CalibrationController {

    @Autowired
    @Qualifier('availableTunePoints')
    private List<Integer> availableTunePoints

    @Autowired
    private List<TunePoint> tunePoints

    @Autowired
    @Qualifier('tunerPot')
    private Potentiometer tunerPot

    @Autowired
    private CalibrationPersister calibrationPersister

    @RequestMapping('')
    String presentCalibrationPage(Model model) {
        model.addAttribute('availableTunePoints', availableTunePoints)
        model.addAttribute('tunePoints', tunePoints)
        'calibrate'
    }

    @RequestMapping('/saveTunePoint/{displayPosition}')
    @ResponseBody
    String saveTunePoint(@PathVariable Integer displayPosition) {
        addTunePoint(displayPosition)
        calibrationPersister.saveTunePoints(tunePoints)
    }

    private void addTunePoint(Integer displayPosition) {
        removeTunePointIfExists(displayPosition)
        tunePoints.add(new TunePoint(displayPosition: displayPosition, potentiometerValue: tunerPot.adjustedValue))
    }

    private void removeTunePointIfExists(Integer displayPosition) {
        TunePoint removeMe = tunePoints.find {
            it.displayPosition == displayPosition
        }
        
        if (removeMe) {
            tunePoints.remove(removeMe)
        }
    }

}
