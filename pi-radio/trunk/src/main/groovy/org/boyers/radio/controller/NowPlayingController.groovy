package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.boyers.radio.model.NowPlaying
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@RequestMapping('/nowPlaying')
class NowPlayingController {

    @Autowired
    Player player

    @RequestMapping(value = '/current')
    @ResponseBody
    NowPlaying getNowPlaying() {
        new NowPlaying(station: player.nowPlayingStation, song: player.nowPlayingSong)
    }
}
