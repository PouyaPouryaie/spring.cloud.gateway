package ir.cloud.spring.orderservice;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {

    private final ExternalOrderService externalOrderService;

    public OrderHandler(ExternalOrderService externalOrderService) {
        this.externalOrderService = externalOrderService;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Order("hello world")));
    }

    public Mono<ServerResponse> find(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Order("Hello " + id)));
    }

    public Mono<ServerResponse> findExternalOrder(ServerRequest request) {
        String id = request.pathVariable("id");
        String result = externalOrderService.fetchData(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Order("Hello External " + result)));
    }
}
