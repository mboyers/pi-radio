package org.boyers.radio.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true)
@EqualsAndHashCode
class NowPlaying {
    String station
    String song
}
