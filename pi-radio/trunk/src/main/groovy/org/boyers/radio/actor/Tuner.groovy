package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.config.TunePoint
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@Slf4j
class Tuner implements Actor {

    @Autowired
    List<TunePoint> tunePoints

    @Autowired
    List<Station> stations

    @Autowired
    Player player
    
    @Override
    void handleChange(Integer potPosition) {
        Station station = tryToFindStation(potPosition)
        if (station) {
            player.playStation(station)
        } else {
            player.playStatic()
        }
    }

    private Station tryToFindStation(Integer potPosition) {
        // First, see if we are on a tune point
        TunePoint tunePoint = findTunePointAt(potPosition)
        if (tunePoint) {
            log.info('Found tune point of {}', tunePoint.displayPosition)
            Station station = findStationAtTunePoint(tunePoint.displayPosition)
            if (station) {
                return station
            } else {
                log.info('No station found at tune point {}', tunePoint)
                log.info('Available stations: {}', stations)
            }
        }

        null
    }

    private TunePoint findTunePointAt(Integer potPosition) {
        tunePoints.find {
            it.potentiometerValue == potPosition
        }
    }

    private Station findStationAtTunePoint(Integer tunePointValue) {
        stations.find {
            it.dialPosition == tunePointValue
        }
    }
}
