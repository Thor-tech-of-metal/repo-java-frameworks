package com.thor.tech.examples.docker.dockerExamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class DockerExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerExamplesApplication.class, args);
	}



	@RestController
	class ReservationRestController {

		@RequestMapping("/")
		public String home() {
			return "Hello Docker World";
		}


		private static final String template = "Hello, %s!";
		private final AtomicLong counter = new AtomicLong();

		@RequestMapping("/greeting")
		public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

			return new Greeting(counter.incrementAndGet(),String.format(template, name));
		}
	}



	public class Greeting {

		private final long id;
		private final String content;

		public Greeting(long id, String content) {
			this.id = id;
			this.content = content;
		}

		public long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}
	}

}
