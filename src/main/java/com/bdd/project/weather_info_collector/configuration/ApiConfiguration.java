package com.bdd.project.weather_info_collector.configuration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@EnableConfigurationProperties()
@ConfigurationProperties(prefix = "weatherinfo")
public class ApiConfiguration {
    private String weatherInfoUrl;
    private String bootstrupServer;

    public ApiConfiguration() {
        log.debug("ApiConfiguration constructor");
    }
}
