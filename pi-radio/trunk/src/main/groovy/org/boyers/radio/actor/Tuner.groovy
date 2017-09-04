package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.config.TunePoint
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@Slf4j
class Tuner implements Actor {

    private Map<Integer, Station> stationMap

    @Autowired
    List<TunePoint> tunePoints

    @Autowired
    List<Station> stations

    @Autowired
    Player player

    @PostConstruct
    void init() {       
        stationMap = [:]
        stations.each {
            stationMap.put(it.dialPosition, it)
        }
    }
    
    @Override
    void handleChange(int potPosition) {
        Station station = tryToFindStation(potPosition)
        if (station) {
            player.playStation(station.uri)
        } else {
            player.playStatic()
        }
    }

    private Station tryToFindStation(Integer potPosition) {
        // First, see if we are on a tune point
        TunePoint tunePoint = findTunePointAt(potPosition)
        if (tunePoint) {
            log.info('Found tune point of {}', tunePoint.displayPosition)
            Station station = stationMap.get(tunePoint.displayPosition)
            if (station) {
                return station
            }
        }

        null
    }

    private TunePoint findTunePointAt(Integer potPosition) {
        tunePoints.find() {
            it.potentiometerValue == potPosition
        }
    }
}
