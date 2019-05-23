package org.boyers.radio.persist

import com.google.gson.Gson
import org.boyers.radio.model.TunePoint
import java.io.File

class CalibrationPersister {

    private val tunePointFileName = "tune-points.json"

    fun getTunePoints(): List<TunePoint> {
        val stationConfigurationFile = File(tunePointFileName)
        if (stationConfigurationFile.exists()) {
            return Gson().fromJson(stationConfigurationFile.reader() , Array<TunePoint>::class.java).toMutableList()
        } else {
            return mutableListOf()
        }
    }

    fun saveTunePoints(tunePoints: List<TunePoint>) {
        File(tunePointFileName).writeText(Gson().toJson(tunePoints))
    }
}