package dev.caobaoqi6040.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@EventListener
	public void handleApplicationReadyEvent(ApplicationReadyEvent event) {
		log.info("BackendApplication.handleApplicationReadyEvent {}", event);
	}

}
