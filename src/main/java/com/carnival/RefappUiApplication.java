package com.carnival;

import com.carnival.db.entity.Reservation;
import com.carnival.db.repository.ReservationRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RefappUiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RefappUiApplication.class, args);
	}

	@Bean
//	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
    ReservationRepository repository;

	@Override
	public void run(String... strings) throws Exception {
		Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/reservations.json"),
				"UTF-8");
		Gson gson = new GsonBuilder().create();
		Reservation[] resArray = gson.fromJson(reader, Reservation[].class);
		List<Reservation> reservations = Arrays.asList(resArray);
        repository.save(reservations);
	}
}
