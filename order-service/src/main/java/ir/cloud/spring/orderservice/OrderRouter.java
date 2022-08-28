package ir.cloud.spring.orderservice;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Service
@AllArgsConstructor
public class OrderRouter {

    private final OrderHandler orderHandler;

    @Bean
    public RouterFunction<ServerResponse> orderRouterFunction() {
        return RouterFunctions.nest(path("/order"),
                RouterFunctions.route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), orderHandler::hello)
                        .andRoute(GET("/find/{id}").and(accept(MediaType.APPLICATION_JSON)), orderHandler::find));
    }
}
