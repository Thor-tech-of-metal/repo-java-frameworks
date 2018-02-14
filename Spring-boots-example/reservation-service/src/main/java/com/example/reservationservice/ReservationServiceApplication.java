package com.example.reservationservice;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;


@EnableDiscoveryClient
@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@Component
class SampleDataInitializer implements ApplicationRunner {

	private final ReservationRepository reservationRepository;

	SampleDataInitializer(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Stream.of("Josh", "Jaya", "John", "Danny",
				"Brian", "Quyen", "Dan", "Alex")
				.forEach(name -> reservationRepository.save(new Reservation(name)));

		reservationRepository.findAll().forEach(System.out::println);
	}
}


@RestController
class ReservationRestController {

	private final ReservationRepository reservationRepository;

	ReservationRestController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@GetMapping("/reservations")
	Collection<Reservation> reservations() {
		return this.reservationRepository.findAll();
	}
}

interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Collection<Reservation> findByReservationName(String rn);
}

@RestController
@RefreshScope
class MessageRestController {

	private final String value;

	MessageRestController(@Value("${message}") String value) {
		this.value = value;
	}

	@GetMapping("/message")
	String read() {
		return this.value;
	}
}

@Entity
class Reservation {

	@Id
	@GeneratedValue
	private Long id;

	public Reservation() {
	}

	public Reservation(String reservationName) {
		this.reservationName = reservationName;
	}

	private String reservationName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", reservationName='" + reservationName + '\'' +
				'}';
	}
}