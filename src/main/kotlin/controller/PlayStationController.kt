package org.boyers.radio.controller

import org.boyers.radio.model.Station
import org.boyers.radio.player.Player
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/play")
class PlayStationController @Autowired constructor(val player: Player){

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @PostMapping("/testStation")
    fun playUserSuppliedStation(@RequestBody stationUri: String) {
        player.playStation(Station(stationUri, "User Supplied Station for Testing", 0))
        log.info("Playing user supplied station with uri: {}", stationUri)
    }

    @PostMapping("/station")
    fun playStation(@RequestBody station: Station) {
        player.playStation(station)
        log.info("Playing user supplied station: {}", station)
    }

}