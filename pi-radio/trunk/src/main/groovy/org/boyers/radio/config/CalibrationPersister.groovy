package org.boyers.radio.config

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.stereotype.Service

@Service
class CalibrationPersister {

    private static final String TUNE_POINT_FILE_NAME = 'tune-points.json'

    Map<Integer, TunePoint> getTunePointMap() {
        File inputFile = new File(TUNE_POINT_FILE_NAME)
        if (inputFile.exists()) {
            new JsonSlurper().parseText(inputFile.text)
        } else {
            [:]
        }
    }

    void saveTunePointMap(Map tunePointMap) {
        new File(TUNE_POINT_FILE_NAME).write(new JsonBuilder(tunePointMap).toPrettyString())
    }
}
