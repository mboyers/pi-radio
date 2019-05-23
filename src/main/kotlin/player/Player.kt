package org.boyers.radio.player

import org.boyers.radio.model.Station

interface Player {
    fun setVolume(newVolume: Int)
    fun playStatic()
    fun playStation(station: Station)
    fun getNowPlayingStation(): String
    fun getNowPlayingSong(): String
    fun pause()
    fun play()
}