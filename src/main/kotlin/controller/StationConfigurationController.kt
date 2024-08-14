package org.boyers.radio.controller

import org.boyers.radio.model.Station
import org.boyers.radio.persist.StationConfigurationPersister
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stationConfiguration")
class StationConfigurationController @Autowired constructor(
        private val stationConfigurationPersister: StationConfigurationPersister) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/stations")
    fun getStationList(): List<Station> {
        return stationConfigurationPersister.getStations()
    }

    @PutMapping("/stations")
    fun saveStations(@RequestBody stations: List<Station>) {
        log.info("Saving stations: {}", stations)
        stationConfigurationPersister.saveStations(stations)
    }
}