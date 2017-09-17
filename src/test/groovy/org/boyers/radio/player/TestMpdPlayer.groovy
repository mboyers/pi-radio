package org.boyers.radio.player

import org.boyers.radio.controller.NowPlayingController
import org.junit.Test

class TestMpdPlayer {

    @Test
    void 'test when valid i-heart-radio string with song and artist'() {
        String nowPlaying = 'Eric Clapton - text="Forever Man" song_spot="M" MediaBaseId="1086663" itunesTrackId="0" amgTrackId="-1" amgArtistId="0" TAID="39635" TPID="1055580" cartcutId="6040473001"'
        NowPlayingController nowPlayingController = new NowPlayingController()
        assert 'Eric Clapton - Forever Man' == nowPlayingController.cleanseNowPlaying(nowPlaying)
    }

    @Test
    void 'test with empty string'() {
        String nowPlaying = ''
        NowPlayingController nowPlayingController = new NowPlayingController()
        assert '' == nowPlayingController.cleanseNowPlaying(nowPlaying)
    }

    @Test
    void 'test with non i-heart-radio string'() {
        String nowPlaying = 'Rush - 2112'
        NowPlayingController nowPlayingController = new NowPlayingController()
        assert nowPlaying == nowPlayingController.cleanseNowPlaying(nowPlaying)
    }
}
