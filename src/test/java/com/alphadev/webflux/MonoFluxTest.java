package com.alphadev.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<?> monoString = Mono.just("alphadev")
                .then(Mono.error(new RuntimeException("runtime expection")))
                .log();
        monoString.subscribe(System.out::println,(e)->System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux() {
        Flux<String> fluxString = Flux.just("spring", "spring boot", "hibernate", "microservices")
                .concatWithValues("AWS")
                .concatWith(Mono.error(new RuntimeException("Exception occured")))
                .log();
        fluxString.subscribe(System.out::println,(e)->System.out.println(e.getMessage()));
    }
}
