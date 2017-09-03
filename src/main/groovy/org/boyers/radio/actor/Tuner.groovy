package org.boyers.radio.actor

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@Slf4j
class Tuner implements Actor {

    private Map<Integer, Station> stationMap

    @Autowired
    List<Station> stations

    @Autowired
    Player player

    @PostConstruct
    void init() {       
        stationMap = [:]
        stations.each {
            stationMap.put(it.potPosition, it)
        }
    }
    
    @Override
    void handleChange(int position) {
        Station station = stationMap.get(position)
        if (station) {
            player.playStation(station.uri)
        } else {
            player.playStatic()
        }
    }
}
