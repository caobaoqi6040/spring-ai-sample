package dev.caobaoqi6040.ai.modules.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;

/**
 * OllamaController
 *
 * @author caobaoqi6040
 * @since 2025/9/18 17:42
 */
@RestController
@RequestMapping("/api/v1/ollama")
public class OllamaController {

	private final ChatClient ollama;

	public OllamaController(@Qualifier("ollama") ChatClient ollama) {
		this.ollama = ollama;
	}

	@GetMapping("/sample-chat")
	public ResponseEntity<String> sampleChat() {

		String content = ollama.prompt()
			.user("向我介绍你自己")
			.call().content();

		return ResponseEntity.ok(content);
	}

	@GetMapping("/stream-chat")
	public ResponseEntity<Flux<String>> streamChat() {

		Flux<String> content = ollama.prompt()
			.user("向我介绍你自己")
			.stream().content();

		return ResponseEntity.ok(content);
	}

	@GetMapping("/image2text")
	public ResponseEntity<String> image2text(@RequestParam("img-url") String imgUrl) throws MalformedURLException {

		UrlResource resource = new UrlResource(imgUrl);

		String content = ollama.prompt()
			.user(spec -> spec
				.text("介绍图片中的内容")
				.media(MimeTypeUtils.IMAGE_JPEG, resource)
			).call().content();

		return ResponseEntity.ok(content);
	}
}
