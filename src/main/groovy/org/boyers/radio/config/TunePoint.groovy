package org.boyers.radio.config

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames = true)
class TunePoint {
    Integer displayPosition
    Integer potentiometerValue
}
