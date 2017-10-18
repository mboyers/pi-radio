package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.model.Station
import org.boyers.radio.config.StationConfigurationPersister
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@RequestMapping('/stationConfiguration')
class StationConfigurationController {

    @Autowired
    @Qualifier('availableTunePoints')
    private List<Integer> availableTunePoints

    @Autowired
    List<Station> stations

    @Autowired
    StationConfigurationPersister stationConfigurationPersister

    @RequestMapping('/stations')
    @ResponseBody
    List<Station> getStations() {
        buildStationList()
    }

    @RequestMapping(value = '', method = RequestMethod.POST)
    @ResponseBody
    void saveStation(@RequestBody Station station) {
        replaceStationInList(station)
        stationConfigurationPersister.saveStations(stations)
        log.info('Saved station: {}', station)
    }

    /**
     * In the case that there are less stations configured than are available, we want to build a list that contains
     * all possible tune points.
     */
    List<Station> buildStationList() {
        List<Station> stationList = []
        availableTunePoints.each {
            Station station = findStationAtTunePoint(it)
            if (station) {
                stationList.add(station)
            } else {
                stationList.add(new Station(dialPosition: it))
            }
        }
        log.info('Station list:  {}', stationList)
        stationList
    }

    private Station findStationAtTunePoint(Integer tunePointValue) {
        stations.find {
            it.dialPosition == tunePointValue
        }
    }

    private void replaceStationInList(Station station) {
        Integer index = stations.findIndexOf {
            it.dialPosition == station.dialPosition
        }
        
        if (index != -1) {
            stations.putAt(index, station)
        } else {
            stations.add(station)
        }
    }
}
