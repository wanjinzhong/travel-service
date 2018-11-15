package com.tip.travel.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class SystemConfig {

    @Value("${travel.exception.db}")
    private Boolean logExceptionToDB;

    @Value("${travel.exception.email}")
    private Boolean mailException;

    public Boolean getLogExceptionToDB() {
        return logExceptionToDB;
    }

    public void setLogExceptionToDB(Boolean logExceptionToDB) {
        this.logExceptionToDB = logExceptionToDB;
    }

    public Boolean getMailException() {
        return mailException;
    }

    public void setMailException(Boolean mailException) {
        this.mailException = mailException;
    }
}
