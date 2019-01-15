package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.model.Station
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@RequestMapping('/play')
class PlayStationController {

    @Autowired
    Player player

    @RequestMapping(value = '/testStation', method = RequestMethod.POST)
    @ResponseBody
    void playUserSuppliedStation(@RequestBody  String stationUri) {
        Station station = new Station(uri: stationUri, name: 'User Supplied Station for Testing')
        player.playStation(station)
        log.info('Playing user supplied station {}', stationUri)
    }

    @RequestMapping(value = '/station', method = RequestMethod.POST)
    @ResponseBody
    void playStation(@RequestBody Station station) {
        player.playStation(station)
        log.info('Playing station {}', station)
    }
}
