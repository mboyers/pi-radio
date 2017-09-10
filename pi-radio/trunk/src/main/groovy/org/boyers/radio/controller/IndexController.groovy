package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
class IndexController {

    @Autowired
    Player player

    @RequestMapping(value = '/')
    String index(Model model) {
        model.addAttribute('nowPlayingSong', player.nowPlaying)
        'index'
    }
}
