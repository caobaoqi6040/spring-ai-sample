package dev.caobaoqi6040.ai.modules.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * OpenAIController
 *
 * @author caobaoqi6040
 * @since 2025/9/18 14:44
 */
@RestController
@RequestMapping("/api/v1/openai")
public class OpenAIController {

	private final ChatClient openAi;

	public OpenAIController(@Qualifier("open-ai") ChatClient openAi) {
		this.openAi = openAi;
	}


	@GetMapping("/sample-chat")
	public ResponseEntity<String> sampleChat() {

		String content = openAi.prompt()
			.user("向我介绍你自己")
			.call().content();

		return ResponseEntity.ok(content);
	}

	@GetMapping("/stream-chat")
	public ResponseEntity<Flux<String>> streamChat() {

		Flux<String> content = openAi.prompt()
			.user("向我介绍你自己")
			.stream().content();

		return ResponseEntity.ok(content);
	}


}
