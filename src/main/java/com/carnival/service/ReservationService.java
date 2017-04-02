package com.carnival.service;

import com.carnival.db.entity.Reservation;
import com.carnival.db.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher;
import static org.springframework.data.domain.ExampleMatcher.matchingAny;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ReservationRepository repository;


    public List<Reservation> getReservations() {
        return repository.findAll();
    }

    public void delete(Reservation reservation) {
        repository.delete(reservation);
    }

    public List<Reservation> filterByName(String name) {
        Reservation reservation = new Reservation(name);
        Example<Reservation> exammple = Example.of((reservation),
                matchingAny()
                        .withIgnorePaths("createdDate", "modifiedDate", "createdAt")
                        .withIgnoreCase(true)
                        .withMatcher("firstName", match -> match.stringMatcher(StringMatcher.CONTAINING))
                        .withMatcher("lastName", match -> match.stringMatcher(StringMatcher.CONTAINING)));
        List<Reservation> reservations = repository.findAll(exammple);

        log.info("filterByName size: " + reservations.size());
        return reservations;
    }

}
