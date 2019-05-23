package org.boyers.radio.controller

import org.boyers.radio.model.Station
import org.boyers.radio.persist.StationConfigurationPersister
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stationConfiguration")
class StationConfigurationController @Autowired constructor(
        val availableTunePoints: List<Int>,
        val stations: MutableList<Station>,
        val stationConfigurationPersister: StationConfigurationPersister) {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/stations")
    fun getStationList(): List<Station> {
        return buildFullStationList()
    }
    
    fun buildFullStationList(): List<Station> {
        val fullStationList: MutableList<Station> = mutableListOf()
        for (tunePoint in availableTunePoints) {
            val filteredStationList = filterStationListToTunePoint(tunePoint)
            if (filteredStationList.isEmpty()) {
                fullStationList.add(Station("", "", tunePoint))
            } else {
                fullStationList.add(filteredStationList.single())
            }
        }
        return fullStationList
    }

    private fun filterStationListToTunePoint(tunePointValue: Int): List<Station> {
        return stations.filter {it.dialPosition == tunePointValue}
    }

    @PostMapping("")
    fun saveStation(@RequestBody station: Station) {
        log.info("Saving station: {}", station)
        addOrReplaceStationInList(station)
        stationConfigurationPersister.saveStations(stations)
        log.info("New station list is: {}", stations)
    }

    private fun addOrReplaceStationInList(station: Station) {
        val stationIndex = stations.indexOfFirst { station.dialPosition == it.dialPosition }
        if (stationIndex == -1) {
            log.info("Adding new station: {}", station)
            stations.add(station)
        } else {
            log.info("Saving existing station: {}", station)
            stations.set(stationIndex, station)
        }
    }
}