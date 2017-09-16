package org.boyers.radio.actor

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true)
@EqualsAndHashCode
class Station {
    String uri
    String name
    Integer dialPosition
}
