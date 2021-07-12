package com.bdd.project.weather_info_collector.services;

import com.bdd.project.weather_info_collector.configuration.ApiConfiguration;
import com.bdd.project.weather_info_collector.model.Station;
import com.bdd.project.weather_info_collector.model.StationCollectedData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class WeatherCollector {
    private static final String WEATHER_INFO_ROW_TOPIC = "weather_info_latest_raw_data";
    private static final String GET_ALL_STATIONS = "allstations";
    private static final String GET_STATION_LATEST_DATA = "latest/{stationsId}";
    @Autowired
    private KafkaTemplate<String, StationCollectedData> kafkaTemplate;

    private ApiConfiguration config;

    WeatherCollector(ApiConfiguration config) {
        this.config = config;
    }

   @Scheduled(fixedDelayString = "${weatherinfo.collect.fixedDelay.in.milliseconds}")
    public void collectWeatherData() {
        WebClient weatherInfoWebClient = WebClient.create(config.getWeatherInfoUrl());
        //TODO shall I create in the other service a more precise request - getAllStationId ? thus I will not need to extract the stationId
        List<Station> stationsList = weatherInfoWebClient.get()
                .uri(GET_ALL_STATIONS)
                .retrieve()
                .bodyToFlux(Station.class)
                .collectList()
                .block();

        List<Integer> stationsIds = stationsList.stream()
                .mapToInt(Station::getStationId)
                .boxed()
                .collect(Collectors.toList());

        for (Integer stationId : stationsIds) {
            log.debug("collecting weatherInfo from : " + stationId);
            StationCollectedData stationCollectedData = weatherInfoWebClient.get()
                    .uri(GET_STATION_LATEST_DATA, stationId)
                    .retrieve()
                    .bodyToMono(StationCollectedData.class)
                    .block();
            log.debug("station " + stationId + " collected data >>>>" + stationCollectedData);
         // write to kafka the data here
            kafkaTemplate.send(WEATHER_INFO_ROW_TOPIC,stationCollectedData);
            log.debug("after sending the data to kafka");

        }

    }
}

