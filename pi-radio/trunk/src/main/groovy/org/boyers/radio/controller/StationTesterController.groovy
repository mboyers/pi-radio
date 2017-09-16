package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.actor.Station
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@RequestMapping('/testStation')
class StationTesterController {

    @Autowired
    Player player

    @RequestMapping('')
    String presentStationTestSection() {
        'station-tester'
    }

    @RequestMapping(value = '/play', method = RequestMethod.POST)
    @ResponseBody
    void playUserSuppliedStation(@RequestBody  String stationUri) {
        Station station = new Station(uri: stationUri, name: 'User Supplied Station for Testing')
        player.playStation(station)
        log.info('Playing user supplied station {}', stationUri)
    }
}
