package org.tricode.hackathonserverside.config;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${spring.ai.azure.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.azure.openai.endpoint}")
    private String endpoint;

    @Getter
    @Value("${spring.ai.azure.openai.chat.options.model}")
    private String modelName;

    @Bean
    public OpenAIClient openAIClient() {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildClient();
    }

}
