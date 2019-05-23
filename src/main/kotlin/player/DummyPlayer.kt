package org.boyers.radio.player

import org.boyers.radio.model.Station
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class DummyPlayer: Player {
    
    override fun setVolume(newVolume: Int) {
    }

    override fun playStatic() {
    }

    override fun playStation(station: Station) {
    }

    override fun getNowPlayingStation() = "102.5 WDVE - Pittsburgh"
    
    override fun getNowPlayingSong() = "Conniption Fit - The Rest Is Yet To Come"

    override fun pause() {
    }

    override fun play() {
    }

}