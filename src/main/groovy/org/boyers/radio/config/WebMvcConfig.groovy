package org.boyers.radio.config

import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Slf4j
@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler('/html/**').addResourceLocations('classpath:/html/')
        registry.addResourceHandler('/js/**').addResourceLocations('classpath:/js/')
        registry.addResourceHandler('/favicon.ico').addResourceLocations('classpath:/images/favicon.ico')
    }

    @Override
    void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController('/').setViewName('forward:/html/index.html')
    }

    @Override
    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable()
    }
}