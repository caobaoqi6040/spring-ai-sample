package dev.caobaoqi6040.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<String> image2text() throws MalformedURLException {

		UrlResource resource = new UrlResource("http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3NwcmluZy1haS1zYW1wbGUvYmFja2dyb3VuZC5qcGc_WC1BbXotQWxnb3JpdGhtPUFXUzQtSE1BQy1TSEEyNTYmWC1BbXotQ3JlZGVudGlhbD0xTEc2WUFIWUc3VUdDSExQRUNLSCUyRjIwMjUwOTE4JTJGdXMtZWFzdC0xJTJGczMlMkZhd3M0X3JlcXVlc3QmWC1BbXotRGF0ZT0yMDI1MDkxOFQxMDI4MzFaJlgtQW16LUV4cGlyZXM9NDMyMDAmWC1BbXotU2VjdXJpdHktVG9rZW49ZXlKaGJHY2lPaUpJVXpVeE1pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmhZMk5sYzNOTFpYa2lPaUl4VEVjMldVRklXVWMzVlVkRFNFeFFSVU5MU0NJc0ltVjRjQ0k2TVRjMU9ESXpNekEyT0N3aWNHRnlaVzUwSWpvaWJXbHVhVzhpZlEubXBzRWQyUjlvU2k5bVVsaTQzYk1SaFZlek9hYU5XeWNJVUZ4VXA5b1NKY0dYN0xfYk9IWlR3WXZWNVpDUi1pV2RYVXAxNWlneDZfdEk4SmxuZmxObVEmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JnZlcnNpb25JZD1udWxsJlgtQW16LVNpZ25hdHVyZT00MTUwMzQyNzA5N2NmOWRjMmQyZTdlMWM3YWQ2M2UzNWQ5ODNhZDEzYTFjZDdjM2YyMTQ2MjI2MTA4MmExOGMx");
//		UrlResource resource = new UrlResource("http://localhost:9001/api/v1/download-shared-object/aHR0cDovLzEyNy4wLjAuMTo5MDAwL3NwcmluZy1haS1zYW1wbGUvY2FvYmFvcWk2MDQwLmpwZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPTFMRzZZQUhZRzdVR0NITFBFQ0tIJTJGMjAyNTA5MTglMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwOTE4VDEwMjkwNVomWC1BbXotRXhwaXJlcz00MzIwMCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSXhURWMyV1VGSVdVYzNWVWREU0V4UVJVTkxTQ0lzSW1WNGNDSTZNVGMxT0RJek16QTJPQ3dpY0dGeVpXNTBJam9pYldsdWFXOGlmUS5tcHNFZDJSOW9TaTltVWxpNDNiTVJoVmV6T2FhTld5Y0lVRnhVcDlvU0pjR1g3TF9iT0haVHdZdlY1WkNSLWlXZFhVcDE1aWd4Nl90SThKbG5mbE5tUSZYLUFtei1TaWduZWRIZWFkZXJzPWhvc3QmdmVyc2lvbklkPW51bGwmWC1BbXotU2lnbmF0dXJlPWEwYjY3OTVhYzgxMTRkYzIwOTU0ZmRlOTI3ZDNjODhjNjc1ZGU5ZDUzODYyYzhjOGVjNzI2NGYzZjg3OTNhZDc");

		String content = ollama.prompt()
			.user(spec -> spec
					.text("介绍图片中的内容")
//				.media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("background.jpg"))
					.media(MimeTypeUtils.IMAGE_JPEG, resource)
			).call().content();

		return ResponseEntity.ok(content);
	}
}
