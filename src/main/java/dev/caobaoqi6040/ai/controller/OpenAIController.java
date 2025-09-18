package dev.caobaoqi6040.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * OpenAIController
 *
 * @author caobaoqi6040
 * @since 2025/9/18 14:44
 */
@RestController
@RequestMapping("/api/v1/openai")
public class OpenAIController {

	private final ChatClient chatClient;

	public OpenAIController(ChatClient.Builder builder) {
		this.chatClient = builder
			.defaultAdvisors(List.of(new SimpleLoggerAdvisor()))
			.defaultSystem("You are a helpful assistant,you should response user`s question with chinese")
			.build();
	}

	@GetMapping("/sample-chat")
	public ResponseEntity<String> sampleChat() {

		String content = chatClient.prompt()
			.user("向我介绍你自己")
			.call().content();

		return ResponseEntity.ok(content);
	}

	@GetMapping("/stream-chat")
	public ResponseEntity<Flux<String>> streamChat() {

		Flux<String> content = chatClient.prompt()
			.user("向我介绍你自己")
			.stream().content();

		return ResponseEntity.ok(content);
	}

}
