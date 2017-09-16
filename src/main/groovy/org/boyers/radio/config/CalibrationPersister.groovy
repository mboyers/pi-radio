package org.boyers.radio.config

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.boyers.radio.model.TunePoint
import org.springframework.stereotype.Service

@Service
class CalibrationPersister {

    private static final String TUNE_POINT_FILE_NAME = 'tune-points.json'

    List<TunePoint> getTunePoints() {
        File inputFile = new File(TUNE_POINT_FILE_NAME)
        if (inputFile.exists()) {
            new JsonSlurper().parseText(inputFile.text)
        } else {
            []
        }
    }

    void saveTunePoints(List<TunePoint> tunePoints) {
        new File(TUNE_POINT_FILE_NAME).write(new JsonBuilder(tunePoints).toPrettyString())
    }
}
