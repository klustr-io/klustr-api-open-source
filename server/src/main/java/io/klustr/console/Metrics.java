package io.klustr.console;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.observation.DefaultServerRequestObservationConvention;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.http.server.observation.ServerRequestObservationConvention;

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/production-ready-metrics.html
 */
public class Metrics {
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {

        return registry -> registry.config().commonTags(
                "env", System.getenv().getOrDefault("APP_ENV", "local"),
                "workload", System.getenv().getOrDefault("WORKLOAD", "console-api"),
                "version", getClass().getPackage().getImplementationVersion(),
                "region", System.getenv().getOrDefault("REGION", "tokyo")
        );
    }


}
