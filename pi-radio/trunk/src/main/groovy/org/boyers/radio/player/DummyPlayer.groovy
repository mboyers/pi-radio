package org.boyers.radio.player

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile('test')
@Service('player')
class DummyPlayer implements Player {
    @Override
    void setVolume(int newVolume) {

    }

    @Override
    void playStatic() {

    }

    @Override
    void playStation(String uri) {

    }
}
