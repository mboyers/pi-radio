package org.boyers.radio.controller

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.boyers.radio.config.TunePoint
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
class ConfigurationController {

    @Autowired
    Player player

    @RequestMapping(value = '/')
    @ResponseBody
    String index() {
        writeJson()
        readJson()
        player.nowPlaying
    }

    void writeJson() {
        TunePoint tp = new TunePoint(displayValue: 10, potentiometerValue: 23)
        Map<Integer, TunePoint> tpm = [:]
        tpm.put(10, tp)
        new File("tuner").write(new JsonBuilder(tpm).toPrettyString())
    }

    void readJson() {
        def inputFile = new File("tuner")
        def InputJSON = new JsonSlurper().parseText(inputFile.text)
        InputJSON.each{ println it }
    }
}
