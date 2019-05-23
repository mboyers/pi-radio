package org.boyers.radio.controller

import org.boyers.radio.model.TunePoint
import org.boyers.radio.persist.CalibrationPersister
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calibrate")
class CalibrationController @Autowired constructor(
        val availableTunePoints: List<Int>,
        val tunePoints: MutableList<TunePoint>,
        val calibrationPersister: CalibrationPersister) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/availableTunePoints")
    fun availableTunePoints(): List<Int> {
        return availableTunePoints
    }

    @PostMapping("/tunePoint/{displayPosition}")
    fun saveTunePoint(@PathVariable displayPosition: Int) {
        val tunePoint = TunePoint(displayPosition, 99999)
        addOrReplaceTunePointInList(tunePoint)
        calibrationPersister.saveTunePoints(tunePoints)
        log.info("New tune point list is: {}", tunePoints)
    }

    private fun addOrReplaceTunePointInList(tunePoint: TunePoint) {
        val tunePointIndex = tunePoints.indexOfFirst { tunePoint.displayPosition == it.displayPosition }
        if (tunePointIndex == -1) {
            log.info("Adding new tune point: {}", tunePoint)
            tunePoints.add(tunePoint)
        } else {
            log.info("Saving existing tune point: {}", tunePoint)
            tunePoints[tunePointIndex] = tunePoint
        }
    }
}