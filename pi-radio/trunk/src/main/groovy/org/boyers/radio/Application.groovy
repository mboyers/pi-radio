package org.boyers.radio

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ComponentScan('org.boyers.radio')
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}
