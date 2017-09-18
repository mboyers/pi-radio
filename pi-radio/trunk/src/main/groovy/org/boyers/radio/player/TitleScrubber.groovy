package org.boyers.radio.player

import org.bff.javampd.objects.MPDSong

import java.util.regex.Matcher

class TitleScrubber {

    String getCleansedArtistAndTitle(MPDSong mpdSong) {
        String dirtyTitle = buildDirtyArtistAndTitleString(mpdSong)
        stripIHeartRadioJunk(stripLeadingAndTrailingMarkers(dirtyTitle))
    }

    String buildDirtyArtistAndTitleString(MPDSong mpdSong) {
        StringBuilder sb = new StringBuilder()

        if (mpdSong.artistName) {
            sb.append(mpdSong.artistName)
            sb.append(' - ')
        }

        if (mpdSong.title) {
            sb.append(mpdSong.title)
        }

        sb.toString()
    }

    String stripIHeartRadioJunk(String title) {
        Matcher matcher = title =~ /(.*)text="(.*?)"/
        if (matcher.matches()) {
            String artist = matcher[0][1]
            String song = matcher[0][2]
            return artist + song
        }

        title
    }

    String stripLeadingAndTrailingMarkers(String title) {
        ['@', '\\+', '\\*'].each {
            title = stripMarker(title, it)
        }
        title
    }

    String stripMarker(String title, String marker) {
        title.replaceAll('^' + marker + '\\s', '').replaceAll('\\s' + marker + '$', '')
    }
}
