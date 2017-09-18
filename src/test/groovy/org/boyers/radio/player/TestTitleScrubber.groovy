package org.boyers.radio.player

import org.junit.Before
import org.junit.Test

class TestTitleScrubber {

    private TitleScrubber titleScrubber

    @Before
    void setup() {
        titleScrubber = new TitleScrubber()
    }

    @Test
    void 'test when valid i-heart-radio string with song and artist'() {
        String nowPlaying = 'Eric Clapton - text="Forever Man" song_spot="M" MediaBaseId="1086663" itunesTrackId="0" amgTrackId="-1" amgArtistId="0" TAID="39635" TPID="1055580" cartcutId="6040473001"'
        assert 'Eric Clapton - Forever Man' == titleScrubber.stripIHeartRadioJunk(nowPlaying)
    }

    @Test
    void 'test with empty string'() {
        String nowPlaying = ''
        assert '' == titleScrubber.stripIHeartRadioJunk(nowPlaying)
    }

    @Test
    void 'test with non i-heart-radio string'() {
        String nowPlaying = 'Rush - 2112'
        assert nowPlaying == titleScrubber.stripIHeartRadioJunk(nowPlaying)
    }

    @Test
    void testMarkerStrip() {
        String title = '+ Foo @'
        assert titleScrubber.stripLeadingAndTrailingMarkers(title) == 'Foo'
        title = '* Bar *'
        assert titleScrubber.stripLeadingAndTrailingMarkers(title) == 'Bar'
    }
}
