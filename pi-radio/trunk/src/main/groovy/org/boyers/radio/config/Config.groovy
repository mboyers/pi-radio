package org.boyers.radio.config

import org.boyers.radio.actor.Announcer
import org.boyers.radio.actor.Station
import org.boyers.radio.actor.Tuner
import org.boyers.radio.actor.Volume
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Autowired
    CalibrationPersister calibrationPersister

    @Bean
    Volume getVolume() {
        new Volume()
    }

    @Bean
    Announcer getAnnouncer() {
        new Announcer()
    }

    @Bean
    Tuner getTuner() {
        new Tuner()
    }

    @Bean(name = 'volumePot')
    Potentiometer getVolumePot() {
        new Potentiometer(channel: 1, tolerance: 20, maxValue: 1024, jumpTolerance: 5000, actor: getVolume())
    }

    @Bean(name = 'squelchPot')
    Potentiometer getSquelchPot() {
        new Potentiometer(channel: 2, tolerance: 20, maxValue: 4, jumpTolerance: 5000, actor: getAnnouncer())
    }

    @Bean(name = 'tunerPot')
    Potentiometer getTunerPot() {
        new Potentiometer(channel: 3, tolerance: 10, maxValue: 100, jumpTolerance: 5000, actor: getTuner())
    }

    @Bean(name = 'availableTunePoints')
    List<Integer> getAvailableTunePoints() {
        // Though this is currently just a series of even numbers, in the future it might be less predictable (might want to include 47 for example)
        [30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50]
    }

    @Bean
    List<TunePoint> getTunePoints() {
        calibrationPersister.tunePoints
    }

    @Bean
    List<Station> getStations() {
        [new Station(dialPosition: 30, uri: 'http://ice5.securenetsystems.net/WKLC?type=.aac'),
         new Station(dialPosition: 32, uri: 'http://18053.live.streamtheworld.com:80/WLVQFMAAC_SC'),
         new Station(dialPosition: 34, uri: 'http://wdve-fm.akacast.akamaistream.net/7/364/20061/v1/auth.akacast.akamaistream.net/wdve-fm'),
         new Station(dialPosition: 36, uri: 'http://wmms-fm.akacast.akamaistream.net/7/920/20015/v1/auth.akacast.akamaistream.net/wmms-fm'),
         new Station(dialPosition: 38, uri: 'http://localhost/DCRock?lang=en-US%2cen%3bq%3d0.5'),
         new Station(dialPosition: 40, uri: 'http://192.168.1.105:9000/live.ogg'),
         new Station(dialPosition: 42, uri: 'http://listen.djcmedia.com/hrhradiohigh'),
         new Station(dialPosition: 44, uri: 'http://17413.live.streamtheworld.com/WBNSFMAAC_SC'),
         new Station(dialPosition: 46, uri: 'http://98.158.184.160:8008'),  // Classic Rock 92.1 WBIK-FM
         new Station(dialPosition: 48, uri: 'http://icy3.abacast.com/bowers-wclgfmaac-64'), // WCLQ -- Morgantown
         new Station(dialPosition: 50, uri: 'http://localhost/100-HARD-ROCK')]
    }
}
