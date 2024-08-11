package org.boyers.radio.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    
//    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/html/")
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/")
//        registry.addResourceHandler("/favicon.png").addResourceLocations("classpath:/images/favicon.png")
//    }
//
//    override fun addViewControllers(registry: ViewControllerRegistry) {
//        registry.addViewController("/").setViewName("forward:/html/index.html")
//    }
//
//    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
//        configurer.enable()
//    }

}