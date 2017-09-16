package org.boyers.radio.config

import groovy.util.logging.Slf4j
import org.boyers.radio.actor.Announcer
import org.boyers.radio.actor.Station
import org.boyers.radio.actor.Tuner
import org.boyers.radio.actor.Volume
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Slf4j
@Configuration
class Config {

    @Autowired
    CalibrationPersister calibrationPersister

    @Autowired
    StationConfigurationPersister stationConfigurationPersister

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
        List<TunePoint> tunePoints = calibrationPersister.tunePoints
        log.info('Loaded tune points: {}', tunePoints)
        tunePoints
    }

    @Bean
    List<Station> getStations() {
        List<Station> stations = stationConfigurationPersister.getStations()
        log.info('Loaded stations: {}', stations)
        stations
    }
}
