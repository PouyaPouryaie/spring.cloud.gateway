package ir.cloud.spring.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {

// if you want hardcode select profile for run
//        new SpringApplicationBuilder()
//                .profiles("debug")
//                        .sources(DiscoveryServiceApplication.class)
//                                .run(args);
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
