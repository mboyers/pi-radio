package org.boyers.radio.player

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
    void playStation(String uri) {

    }

    @Override
    String getNowPlaying() {
        'fakeNowPlayingSongTitle'
    }

    @Override
    void pause() {

    }

    @Override
    void play() {

    }
}
