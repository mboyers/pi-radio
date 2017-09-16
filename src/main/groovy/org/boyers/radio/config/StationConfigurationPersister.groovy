package org.boyers.radio.config

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.boyers.radio.model.Station
import org.springframework.stereotype.Service

@Service
class StationConfigurationPersister {
    private static final String STATION_CONFIGURATION_FILE_NAME = 'station-configuration.json'

    List<Station> getStations() {
        File inputFile = new File(STATION_CONFIGURATION_FILE_NAME)
        if (inputFile.exists()) {
            new JsonSlurper().parseText(inputFile.text)
        } else {
            []
        }
    }

    void saveStations(List<Station> stations) {
        new File(STATION_CONFIGURATION_FILE_NAME).write(new JsonBuilder(stations).toPrettyString())
    }
}
