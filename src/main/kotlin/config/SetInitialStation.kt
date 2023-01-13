package org.boyers.radio.config

import org.boyers.radio.model.Station
import org.boyers.radio.player.Player
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class SetInitialStation @Autowired constructor(
        val stations: MutableList<Station>,
        val player: Player):ApplicationListener<ContextRefreshedEvent> {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val initialStation = findDmapStation()
        log.info("setting initial station of {}", initialStation)
        if (initialStation != null) {
            player.playStation(initialStation)
        }
    }

    private fun findDmapStation(): Station? {
        return stations.find {
            it.name == "DMAP"
        }
    }
}