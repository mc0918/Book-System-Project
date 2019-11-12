package com.trilogyed.booksystemconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BookSystemConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSystemConfigServerApplication.class, args);
	}

}
