package io.klustr.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpringJacksonConfiguration {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return io.klustr.utils.Json.mapperInstance(); // calls JsonFactory.build()
    }
}
