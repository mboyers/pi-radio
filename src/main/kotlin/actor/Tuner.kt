package org.boyers.radio.actor

import org.boyers.radio.model.Station
import org.boyers.radio.model.TunePoint
import org.boyers.radio.player.Player
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadPoolExecutor

class Tuner constructor(private val player: Player,
                        private val executor: ThreadPoolExecutor,
                        private val tunePoints: List<TunePoint>,
                        private val stations: List<Station>): Actor {

    private val log = LoggerFactory.getLogger(this.javaClass.name)
    
    override fun handleChange(newValue: Int) {
        val  tunerChanger = {
            val station = tryToFindStation(newValue)
            if (station != null) {
                player.playStation(station)
            } else {
                player.playStatic()
            }
        }

        executor.execute(tunerChanger)
    }

    private fun tryToFindStation(potPosition: Int): Station? {
        val tunePoint = findTunePointAt(potPosition)
        if (tunePoint != null) {
            log.info("Found tune point of {}", tunePoint.displayPosition)
            val station = findStationAtTunePoint(tunePoint.displayPosition)
            if (station != null) {
                return station
            }

            log.info("No station found at tune point {}", tunePoint)
            log.info("Available stations: {}", stations)
        }

        return null
    }

    private fun findTunePointAt(potPosition: Int): TunePoint? {
        return tunePoints.find {
            it.potentiometerValue == potPosition
        }
    }

    private fun findStationAtTunePoint(tunePointValue: Int): Station? {
        return stations.find {
            it.dialPosition == tunePointValue
        }
    }

}