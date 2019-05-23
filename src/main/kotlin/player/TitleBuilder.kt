package org.boyers.radio.player

import org.bff.javampd.objects.MPDSong
import org.slf4j.LoggerFactory

class TitleBuilder {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    fun buildTitle(mpdSong: MPDSong): String {
        val sb = StringBuilder()

        if (!mpdSong.artistName.isNullOrEmpty()) {
            sb.append(mpdSong.artistName)
            sb.append(" - ")
        }

        if (!mpdSong.title.isNullOrEmpty()) {
            sb.append(mpdSong.title)
        }

        log.info("Built title {}", sb.toString())

        return sb.toString()
    }
}