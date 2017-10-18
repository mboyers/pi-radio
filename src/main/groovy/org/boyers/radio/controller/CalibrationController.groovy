package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.config.CalibrationPersister
import org.boyers.radio.model.Status
import org.boyers.radio.model.TunePoint
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
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

    @RequestMapping('/availableTunePoints')
    @ResponseBody
    List<Integer> getAvailableTunePoints() {
        availableTunePoints
    }

    @RequestMapping('/tunePoint/{displayPosition}')
    @ResponseBody
    Status saveTunePoint(@PathVariable Integer displayPosition) {
        addTunePoint(displayPosition)
        calibrationPersister.saveTunePoints(tunePoints)
        new Status(message: "Saved ${tunerPot.adjustedValue} in tune point ${displayPosition}")
    }

    private void addTunePoint(Integer displayPosition) {
        TunePoint tunePoint = new TunePoint(displayPosition: displayPosition, potentiometerValue: tunerPot.adjustedValue)
        addOrReplaceTunePointInList(tunePoint)
    }

    private addOrReplaceTunePointInList(TunePoint tunePoint) {
        Integer index = tunePoints.findIndexOf {
            it.displayPosition == tunePoint.displayPosition
        }

        if (index != -1) {
            tunePoints.putAt(index, tunePoint)
        } else {
            tunePoints.add(tunePoint)
        }
    }
}
