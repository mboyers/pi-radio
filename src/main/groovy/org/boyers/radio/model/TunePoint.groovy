package org.boyers.radio.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames = true)
class TunePoint {
    Integer displayPosition
    Integer potentiometerValue
}
