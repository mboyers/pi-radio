package org.boyers.radio.config

import org.boyers.radio.actor.Announcer
import org.boyers.radio.actor.Tuner
import org.boyers.radio.actor.Volume
import org.boyers.radio.model.Station
import org.boyers.radio.model.TunePoint
import org.boyers.radio.persist.CalibrationPersister
import org.boyers.radio.persist.StationConfigurationPersister
import org.boyers.radio.player.Player
import org.boyers.radio.potentiometer.Potentiometer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

// Note: I'm explicitly listing the return types here even though it's not required.  This is because classes who
// get these beans autowired are expecting whatever is listed here as the return type.  The stuff below will compile
// without it, but if listOf becomes arrayOf, the following still compiles even though people won't get the bean.
@Configuration
class Config @Autowired constructor(val player: Player){

    private val threadKeepAliveTime = 3000L
    private val corePoolSize = 1
    private val maxPoolSize = 1

    @Bean
    fun availableTunePoints(): List<Int> = listOf(30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 49, 50)

    @Bean
    fun tunePoints(): List<TunePoint> = calibrationPersister().getTunePoints()

    @Bean
    fun stations(): List<Station> = stationConfigurationPersister().getStations()

    @Bean
    fun calibrationPersister() = CalibrationPersister()

    @Bean
    fun stationConfigurationPersister() = StationConfigurationPersister()

    @Bean
    fun volumeExecutor() = ThreadPoolExecutor(corePoolSize, maxPoolSize, threadKeepAliveTime, TimeUnit.SECONDS, LinkedBlockingQueue())

    // This executor is set up with a single thread and a synchronous queue so that it fails fast when busy.  The idea is to
    // not play two announcements immediately one after the next if someone kept twisting the knob waiting for the announcement.
    @Bean
    fun announcerExecutor() = ThreadPoolExecutor(corePoolSize, maxPoolSize, threadKeepAliveTime, TimeUnit.SECONDS, SynchronousQueue())

    @Bean
    fun tunerExecutor() = ThreadPoolExecutor(corePoolSize, maxPoolSize, threadKeepAliveTime, TimeUnit.SECONDS, LinkedBlockingQueue())

    @Bean
    fun volumeActor() = Volume(player, volumeExecutor())

    @Bean
    fun announcerActor() = Announcer(player, announcerExecutor())

    @Bean
    fun tunerActor() = Tuner(player, tunerExecutor(), tunePoints(), stations())

    @Bean
    fun volumePot() = Potentiometer(1, 20, 1024, false, volumeActor())

    @Bean
    fun squelchPot() = Potentiometer(2, 20, 10, true, announcerActor())

//    @Bean
//    fun tunerPot() = Potentiometer(3, 30, 100, false, tunerActor())
}