package org.boyers.radio.player

interface Player {
    void setVolume(Integer newVolume)
    void playStatic()
    void playStation(String uri)
    String getNowPlaying()
    void pause()
    void play()
}