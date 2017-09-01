package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
class ConfigurationController {

    @Autowired
    Player player

    @RequestMapping(value = '/')
    String index(Model model) {
        model.addAttribute('')
        'index'
    }
    
    @RequestMapping(value = '/play')
    @ResponseBody
    String play() {
        player.playStation('http://localhost/DCRock?lang=en-US%2cen%3bq%3d0.5')
    }

    @RequestMapping(value = '/playStatic')
    @ResponseBody
    String playStatic() {
        player.playStatic()
    }
}
