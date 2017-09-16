package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
@RequestMapping('/nowPlaying')
class NowPlayingController {

    @Autowired
    Player player

    @RequestMapping(value = '')
    String presentNowPlayingSection(Model model) {
        model.addAttribute('nowPlayingStation', player.nowPlayingStation)
        model.addAttribute('nowPlayingSong', player.nowPlayingSong)
        'now-playing'
    }
}
