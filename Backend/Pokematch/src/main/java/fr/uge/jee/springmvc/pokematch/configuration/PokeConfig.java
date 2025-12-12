package fr.uge.jee.springmvc.pokematch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PokeConfig {

  @Bean
  public WebClient getWebClient (WebClient.Builder defaultBuilder) {
    return defaultBuilder.exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .build()
        ).build();
  }
}
