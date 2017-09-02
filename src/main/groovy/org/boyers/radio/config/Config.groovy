package org.boyers.radio.config

import org.boyers.radio.actor.Station
import org.boyers.radio.actor.Tuner
import org.boyers.radio.actor.Volume
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    Volume getVolume() {
        new Volume()
    }

    @Bean getTuner() {
        new Tuner()
    }

    @Bean
    List<Potentiometer> getPotentiometers() {
        Potentiometer volumePot = new Potentiometer(channel: 1, tolerance: 20, maxValue: 1024, jumpTolerance: 5000, actor: getVolume())
        Potentiometer tunerPot = new Potentiometer(channel: 3, tolerance: 10, maxValue: 100, jumpTolerance: 5000, actor: getTuner())

        [volumePot, tunerPot]
    }

    @Bean
    List<Station> getStations() {
        [new Station(dialPosition: 30, potPosition: 42, uri: 'http://ice5.securenetsystems.net/WKLC?type=.aac'),
         new Station(dialPosition: 32, potPosition: 49, uri: 'http://18053.live.streamtheworld.com:80/WLVQFMAAC_SC'),
         new Station(dialPosition: 34, potPosition: 56, uri: 'http://wdve-fm.akacast.akamaistream.net/7/364/20061/v1/auth.akacast.akamaistream.net/wdve-fm'),
         new Station(dialPosition: 36, potPosition: 62, uri: 'http://wmms-fm.akacast.akamaistream.net/7/920/20015/v1/auth.akacast.akamaistream.net/wmms-fm'),
         new Station(dialPosition: 38, potPosition: 67, uri: 'http://localhost/DCRock?lang=en-US%2cen%3bq%3d0.5'),
         new Station(dialPosition: 40, potPosition: 72, uri: 'http://192.168.1.105:9000/live.ogg'),
         new Station(dialPosition: 42, potPosition: 77, uri: 'http://listen.djcmedia.com/hrhradiohigh'),
         new Station(dialPosition: 44, potPosition: 81, uri: 'http://17413.live.streamtheworld.com/WBNSFMAAC_SC'),
         new Station(dialPosition: 46, potPosition: 87, uri: 'http://98.158.184.160:8008'),
         new Station(dialPosition: 48, potPosition: 93, uri: 'http://icy3.abacast.com/bowers-wclgfmaac-64'),
         new Station(dialPosition: 50, potPosition: 99, uri: 'http://localhost/100-HARD-ROCK')]
    }
}
