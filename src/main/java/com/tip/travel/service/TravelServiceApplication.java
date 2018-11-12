package com.tip.travel.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo
public class TravelServiceApplication {

    public static void main(String[] args) {
        // run with non-web
        new SpringApplicationBuilder(TravelServiceApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
