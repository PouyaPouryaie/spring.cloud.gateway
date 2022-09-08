# spring.cloud.gateway

This project try to show how use spring cloud gateway in spring project <br>
and how can use spring cloud gateway features

## features of spring cloud gateway
- Able to match routes on any request attribute.
- we can use predicate for route request to any internal services by config file or class config
- use spring cloud discovery service for find another services
- use filter for modify request (header, url, body) and ...
- use ServerWebExchangeUtils.addOriginalRequestUrl() for store original url
- once we've applied the rewrite logic, we must save the modified URL in the GATEWAY_REQUEST_URL_ATTR exchange's attribute in filters
- use Path rewrite for get request and modify route for internal services
- use circuit breaker pattern
- Request Rate Limiting
- use Project Reactor and Spring Boot 2.0

## Run project
1 - use dockerfile and docker-compose command for run app

- creat package:
~~~
mvn clean package
~~~
- build image
~~~
docker compose build
~~~
- run image
~~~
docker compose up
~~~


manual command for build image:
~~~
docker build ./gateway-service/ -t gateway-service:latest
docker build ./discovery-service/ -t discovery-service:latest
~~~
manual command for Run container:
~~~
docker network create -d bridge my-network
docker run -d --name redis -p 6379:6379 --network my-network redis
docker run -d --name discovery-service -p 8761:8761 --network my-network discovery-service
docker run -d --name gateway-service -p 8091:8091 --network gateway-service
~~~


## Glossary
~~~
- Route: The basic building block of the gateway. It is defined by an ID, a destination URI, a collection of predicates, and a collection of filters. A route is matched if the aggregate predicate is true.
- Predicate: This is a Java 8 Function Predicate. The input type is a Spring Framework ServerWebExchange. This lets you match on anything from the HTTP request, such as headers or parameters.
- Filter: These are instances of GatewayFilter that have been constructed with a specific factory. Here, you can modify requests and responses before or after sending the downstream request.
~~~