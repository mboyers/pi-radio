package org.boyers.radio.player

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import player.TitleScrubber

class TitleScrubberTest {

    private val scrubber = TitleScrubber()

    @Test
    fun testAnIHeartRadioStyleTitle() {
        val scrubbedTitle = scrubber.scrub("""Eric Clapton - text="Forever Man" song_spot="M" MediaBaseId="1086663" itunesTrackId="0" amgTrackId="-1" amgArtistId="0" TAID="39635" TPID="1055580" cartcutId="6040473001"""")
        assertEquals("Eric Clapton - Forever Man", scrubbedTitle)
    }

    @Test
    fun testWithEmptyString() {
        val emptyString = ""
        assertEquals(emptyString, scrubber.scrub(emptyString))
    }

    @Test
    fun testWithTitleThatNeedsNothingDoneToIt() {
        val nowPlaying = "Rush - 2112"
        assertEquals(nowPlaying, scrubber.scrub(nowPlaying))
    }

//    @Test
//    fun foo() {
//        val unwanted = "+"
//        val d = "+ Foo +"
//
//        //(^\+\s|\s\+$)
//        val z = "(^\\$unwanted\\s|\\s\\$unwanted\$)".toRegex()
//        assertEquals("z", z.replace(d, ""))
//
//
//    }

    @Test
    fun testMarkerStripping() {
        val hasPlusAndAmpersand = "+ Foo @"
        assertEquals("Foo", scrubber.scrub(hasPlusAndAmpersand))
        val hasAsterisks = "* Fighters *"
        assertEquals("Fighters", scrubber.scrub(hasAsterisks))
    }
}

