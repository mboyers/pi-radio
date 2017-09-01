import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

jmxConfigurator()

def sharedPattern = '%d{yyyy-MM-dd HH:mm:ss},%p,%c,%L,%M %m%n'

appender('Console', ConsoleAppender) {
    layout(PatternLayout) {
        pattern = sharedPattern
    }
}
appender('File', RollingFileAppender) {
    file = "logs/pi-radio.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "logs/pi-radio.%d{yyyy-MM}.log.zip"
    }
    layout(PatternLayout) {
        pattern = sharedPattern
    }
}
root(ERROR, ['Console', 'File'])
logger('org.springframework.web', INFO)
logger('org.boyers', DEBUG)