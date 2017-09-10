package org.boyers.radio.player

import org.boyers.radio.actor.Station

interface Player {
    void setVolume(Integer newVolume)
    void playStatic()
    void playStation(Station station)
    String getNowPlayingStation()
    String getNowPlayingSong()
    void pause()
    void play()
}