package org.boyers.radio.persist

import com.google.gson.Gson
import org.boyers.radio.model.Station
import java.io.File

class StationConfigurationPersister {

    private val stationConfigurationFileName = "station-configuration.json"

    fun getStations(): List<Station> {
        val stationConfigurationFile = File(stationConfigurationFileName)
        if (stationConfigurationFile.exists()) {
            return Gson().fromJson(stationConfigurationFile.reader() , Array<Station>::class.java).toMutableList()
        } else {
            return mutableListOf()
        }
    }

    fun saveStations(stations: List<Station>) {
        File(stationConfigurationFileName).writeText(Gson().toJson(stations))
    }
}