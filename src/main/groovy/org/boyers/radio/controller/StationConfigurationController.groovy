package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.actor.Station
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Slf4j
@Controller
@RequestMapping('/stationConfiguration')
class StationConfigurationController {

    @Autowired
    @Qualifier('availableTunePoints')
    private List<Integer> availableTunePoints

    @Autowired
    List<Station> stations

    @RequestMapping(value = '', method = RequestMethod.GET)
    String presentSectionConfigurationSection(Model model) {
        model.addAttribute('stations', buildStationList())
        'station-configuration'
    }

    @RequestMapping(value = '', method = RequestMethod.POST)
    String saveStation(Station station) {
        'station-configuration'
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
                stationList.add(new Station())
            }
        }
        stationList
    }

    private Station findStationAtTunePoint(Integer tunePointValue) {
        stations.find {
            it.dialPosition == tunePointValue
        }
    }
}
