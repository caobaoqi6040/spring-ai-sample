package dev.caobaoqi6040.ai.config;

import dev.caobaoqi6040.ai.infrastructure.enums.Models;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.embedding.observation.EmbeddingModelObservationConvention;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.ollama.autoconfigure.OllamaEmbeddingProperties;
import org.springframework.ai.model.ollama.autoconfigure.OllamaInitializationProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAIAutoConfigurationUtil;
import org.springframework.ai.model.openai.autoconfigure.OpenAiConnectionProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingProperties;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * AiConfiguration
 *
 * @author caobaoqi6040
 * @since 2025/9/18 17:43
 */
@Slf4j
@Configuration
public class AiConfiguration {

	/**
	 * your open api
	 *
	 * @param chatModel OpenAiChatModel
	 * @return open ai chat client[gpt 4o]
	 */
	@Bean("open-ai")
	public ChatClient openAI(OpenAiChatModel chatModel) {
		return ChatClient.create(chatModel).mutate()
			.defaultSystem("you are a helpful assistant")
			.defaultOptions(ChatOptions.builder()
				.model(Models.Openai.GPT_4O.getModel()).build())
			.defaultAdvisors(
				SimpleLoggerAdvisor.builder().build())
			.build();
	}


	/**
	 * ollama
	 *
	 * @param chatModel OllamaChatModel
	 * @return ollama chat client[gemma3 4b]
	 */
	@Bean("ollama")
	public ChatClient ollama(OllamaChatModel chatModel) {
		return ChatClient.create(chatModel).mutate()
			.defaultSystem("you are a helpful assistant")
			.defaultOptions(ChatOptions.builder()
				.model(Models.Ollama.GEMMA3_4B.getModel()).build())
			.defaultAdvisors(
				SimpleLoggerAdvisor.builder().build())
			.build();
	}

	@Bean("embedding-open-ai")
	public OpenAiEmbeddingModel openAiEmbeddingModel(OpenAiConnectionProperties commonProperties, OpenAiEmbeddingProperties embeddingProperties, ObjectProvider<RestClient.Builder> restClientBuilderProvider, ObjectProvider<WebClient.Builder> webClientBuilderProvider, RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler, ObjectProvider<ObservationRegistry> observationRegistry, ObjectProvider<EmbeddingModelObservationConvention> observationConvention) {
		OpenAiApi openAiApi = this.openAiApi(embeddingProperties, commonProperties, (RestClient.Builder) restClientBuilderProvider.getIfAvailable(RestClient::builder), (WebClient.Builder) webClientBuilderProvider.getIfAvailable(WebClient::builder), responseErrorHandler, "embedding");
		OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(openAiApi, embeddingProperties.getMetadataMode(), embeddingProperties.getOptions(), retryTemplate, (ObservationRegistry) observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP));
		Objects.requireNonNull(embeddingModel);
		observationConvention.ifAvailable(embeddingModel::setObservationConvention);
		return embeddingModel;
	}

	@Bean("embedding-ollama")
	public OllamaEmbeddingModel ollamaEmbeddingModel(OllamaApi ollamaApi, OllamaEmbeddingProperties properties, OllamaInitializationProperties initProperties, ObjectProvider<ObservationRegistry> observationRegistry, ObjectProvider<EmbeddingModelObservationConvention> observationConvention) {
		PullModelStrategy embeddingModelPullStrategy = initProperties.getEmbedding().isInclude() ? initProperties.getPullModelStrategy() : PullModelStrategy.NEVER;
		OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder().ollamaApi(ollamaApi).defaultOptions(properties.getOptions()).observationRegistry((ObservationRegistry) observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP)).modelManagementOptions(new ModelManagementOptions(embeddingModelPullStrategy, initProperties.getEmbedding().getAdditionalModels(), initProperties.getTimeout(), initProperties.getMaxRetries())).build();
		Objects.requireNonNull(embeddingModel);
		observationConvention.ifAvailable(embeddingModel::setObservationConvention);
		return embeddingModel;
	}

	private OpenAiApi openAiApi(OpenAiEmbeddingProperties embeddingProperties, OpenAiConnectionProperties commonProperties, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler, String modelType) {
		OpenAIAutoConfigurationUtil.ResolvedConnectionProperties resolved = OpenAIAutoConfigurationUtil.resolveConnectionProperties(commonProperties, embeddingProperties, modelType);
		return OpenAiApi.builder().baseUrl(resolved.baseUrl()).apiKey(new SimpleApiKey(resolved.apiKey())).headers(resolved.headers()).completionsPath("/v1/chat/completions").embeddingsPath(embeddingProperties.getEmbeddingsPath()).restClientBuilder(restClientBuilder).webClientBuilder(webClientBuilder).responseErrorHandler(responseErrorHandler).build();
	}

}
