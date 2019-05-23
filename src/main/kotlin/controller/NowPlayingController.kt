package org.boyers.radio.controller

import org.boyers.radio.model.NowPlaying
import org.boyers.radio.player.Player
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nowPlaying")
class NowPlayingController @Autowired constructor(val player: Player) {

    @GetMapping("/current")
    fun getNowPlaying(): NowPlaying {
        return NowPlaying(player.getNowPlayingStation(), player.getNowPlayingSong())
    }
}