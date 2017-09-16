package org.boyers.radio.player

import org.boyers.radio.model.Station
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile('test')
@Service('player')
class DummyPlayer implements Player {
    @Override
    void setVolume(Integer newVolume) {

    }

    @Override
    void playStatic() {

    }

    @Override
    void playStation(Station station) {

    }

    @Override
    String getNowPlayingStation() {
        '102.5 WDVE - Pittsburgh'
    }

    @Override
    String getNowPlayingSong() {
        'Conniption Fit - The Rest Is Yet To Come'
    }

    @Override
    void pause() {

    }

    @Override
    void play() {

    }
}
