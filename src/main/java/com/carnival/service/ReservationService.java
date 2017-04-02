package com.carnival.service;

import com.carnival.model.Reservation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    RestTemplate restTemplate;

    public List<Reservation> getReservations() {
        String reservationURL
                = "http://localhost:8001/reservations";
        List<Reservation> reservations = new ArrayList<>();

        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Making REST API call to " + reservationURL);

        String response = restTemplate.getForObject(reservationURL, String.class);
        watch.stop();

        log.info("Time taken in millis: " + watch.getTotalTimeMillis());
        log.debug(response);

        if(!StringUtils.isEmpty(response)) {
            Gson gson = new GsonBuilder().create();
            Reservation[] resArray = gson.fromJson(response, Reservation[].class);
            reservations = Arrays.asList(resArray);
        }

        return reservations;
    }
}
