package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.model.TunePoint
import org.boyers.radio.model.Station
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import java.util.concurrent.ThreadPoolExecutor

@Slf4j
class Tuner implements Actor {

    @Autowired
    List<TunePoint> tunePoints

    @Autowired
    List<Station> stations

    @Autowired
    Player player

    @Autowired
    @Qualifier('announcerExecutor')
    ThreadPoolExecutor executor
    
    @Override
    void handleChange(Integer potPosition) {

        def tunerChanger = {
            Station station = tryToFindStation(potPosition)
            if (station) {
                player.playStation(station)
            } else {
                player.playStatic()
            }
        } as Runnable

        executor.execute(tunerChanger)
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
