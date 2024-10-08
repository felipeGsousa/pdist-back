package br.edu.ifpb.pdist_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class PdistBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdistBackApplication.class, args);
	}

}
