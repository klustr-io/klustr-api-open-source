//package io.klustr.console;
//
//import io.micrometer.common.KeyValue;
//import io.micrometer.common.KeyValues;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.observation.DefaultServerRequestObservationConvention;
//import org.springframework.http.server.observation.ServerRequestObservationContext;
//import org.springframework.http.server.observation.ServerRequestObservationConvention;
//import org.springframework.stereotype.Component;
//
//@Configuration
//@Component
//public class MetricsConfiguration {
//    @Bean
//    public ServerRequestObservationConvention customConvention() {
//        return new DefaultServerRequestObservationConvention() {
//            @Override
//            public KeyValues getLowCardinalityKeyValues(ServerRequestObservationContext context) {
//                KeyValues kvs = super.getLowCardinalityKeyValues(context);
//                // Detect if default convention set uri=UNKNOWN
//                boolean hasUnknownUri = kvs.stream()
//                        .anyMatch(kv -> kv.getKey().equals("uri") && "UNKNOWN".equals(kv.getValue()));
//
//                if (hasUnknownUri) {
//                    HttpServletRequest request = context.getCarrier();
//
//                    // Rebuild KeyValues manually, skipping the bad "uri" tag
//                    KeyValues result = KeyValues.empty();
//                    for (KeyValue kv : kvs) {
//                        if (!kv.getKey().equals("uri")) {
//                            result = result.and(kv);
//                        }
//                    }
//                    // Add raw URI instead
//                    result = result.and("uri", request.getRequestURI());
//                    return result;
//                }
//
//                return kvs;
//            }
//        };
//    }
//}
