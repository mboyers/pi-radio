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
        new Potentiometer(channel: 1, tolerance: 20, maxValue: 1024, actor: getVolume())
    }

    @Bean(name = 'squelchPot')
    Potentiometer getSquelchPot() {
        new Potentiometer(channel: 2, tolerance: 20, maxValue: 4, ignoreFiringOnStartup: true, actor: getAnnouncer())
    }

    @Bean(name = 'tunerPot')
    Potentiometer getTunerPot() {
        new Potentiometer(channel: 3, tolerance: 10, maxValue: 100, actor: getTuner())
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
        [new Station(dialPosition: 30, uri: 'http://ice5.securenetsystems.net/WKLC?type=.aac' , name: 'WKLC'),
         new Station(dialPosition: 32, uri: 'http://18053.live.streamtheworld.com:80/WLVQFMAAC_SC', name: '96.3 WLVQ FM - Iconic Rock'),
         new Station(dialPosition: 34, uri: 'http://wdve-fm.akacast.akamaistream.net/7/364/20061/v1/auth.akacast.akamaistream.net/wdve-fm', name: '102.5 WDVE - Pittsburgh'),
         new Station(dialPosition: 36, uri: 'http://wmms-fm.akacast.akamaistream.net/7/920/20015/v1/auth.akacast.akamaistream.net/wmms-fm', name: 'WMMS - Cleveland'),
         new Station(dialPosition: 38, uri: 'http://localhost/DCRock?lang=en-US%2cen%3bq%3d0.5', name: 'DC Rock Radio - Forgotten Classics'),
         new Station(dialPosition: 40, uri: 'http://192.168.1.105:9000/live.ogg', name: 'DMAP'),
         new Station(dialPosition: 42, uri: 'http://listen.djcmedia.com/hrhradiohigh', name: 'Hard Rock Heaven - 80s Hard Rock Radio Hard Rock Heaven'),
         new Station(dialPosition: 44, uri: 'http://17413.live.streamtheworld.com/WBNSFMAAC_SC', name: '97.1 - The Fan - WBNS'),
         new Station(dialPosition: 46, uri: 'http://98.158.184.160:8008', name: 'Classic Rock 92.1 WBIK-FM - Pleasant City, OH'),
         new Station(dialPosition: 48, uri: 'http://icy3.abacast.com/bowers-wclgfmaac-64', name: 'WCLQ -- Morgantown'),
         new Station(dialPosition: 50, uri: 'http://localhost/100-HARD-ROCK', name: '100% HARD ROCK')]
    }
}
