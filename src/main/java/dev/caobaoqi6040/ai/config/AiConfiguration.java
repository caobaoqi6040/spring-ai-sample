package dev.caobaoqi6040.ai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AiConfiguration
 *
 * @author caobaoqi6040
 * @since 2025/9/18 17:43
 */
@Slf4j
@Configuration
public class AiConfiguration {

	@Bean("your-open-api")
	public ChatClient yourOpenAI(OpenAiChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

	@Bean("ollama")
	public ChatClient ollama(OllamaChatModel chatModel) {
		return ChatClient.create(chatModel);
	}

}
