package ir.cloud.spring.gatewayservice;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order", r -> r
                        .path("/order/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c.setName("orderBreak").setFallbackUri("/defaultFallback"))
                                .requestRateLimiter().configure(c -> c.setRateLimiter(redisRateLimiter()))
                        ).uri("lb://order-service"))
                .route("customer", r -> r
                        .path("/customer/**")
                        .filters(f -> f.filter((exchange, chain) -> {
                                    ServerHttpRequest req = exchange.getRequest();
                                    ServerWebExchangeUtils.addOriginalRequestUrl(exchange, req.getURI());
                                    return chain.filter(exchange);
                                }).rewritePath("/customer/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("local", "fa"))
                        .uri("lb://customer-service"))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultFactory(){

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(2))
                        .build())
                .build());
    }

    @Bean
    public RedisRateLimiter redisRateLimiter(){
        return new RedisRateLimiter(5, 10);
    }

    @Bean
    KeyResolver userKeyResolver(){
        return exchange -> Mono.just("1");
    }
}
