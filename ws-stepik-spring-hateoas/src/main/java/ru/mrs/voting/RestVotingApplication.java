package ru.mrs.voting;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class RestVotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestVotingApplication.class, args);
	}

}
