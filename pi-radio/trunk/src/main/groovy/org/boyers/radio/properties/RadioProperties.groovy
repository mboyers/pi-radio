package org.boyers.radio.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = 'radio')
class RadioProperties {

    Integer timeBetweenAlertsInSeconds
    Integer alertLevelInMillimeters

    List<String> alertEmailAddresses
    List<String> statisticsEmailAddresses
    String emailFromAddress
}
