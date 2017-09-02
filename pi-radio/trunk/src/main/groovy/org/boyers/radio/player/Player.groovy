package org.boyers.radio.player

interface Player {
    void setVolume(int newVolume)
    void playStatic()
    void playStation(String uri)
}