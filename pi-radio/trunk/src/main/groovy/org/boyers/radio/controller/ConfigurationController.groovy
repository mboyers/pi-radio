package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
class ConfigurationController {

    @Autowired
    Player player

    @RequestMapping(value = '/')
    String index() {
        'index'
    }
}
