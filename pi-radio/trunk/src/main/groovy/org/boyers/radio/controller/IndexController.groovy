package org.boyers.radio.controller

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
class IndexController {

    @RequestMapping(value = '/')
    String index() {
        'index'
    }
}
