package dev.mkuwan.spring.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class QuoteController {
    private final static Random RANDOMIZER = new Random();
    @GetMapping("/api/random")
    public Quote getRandomOne() {
        return new Quote("success", new Value(RANDOMIZER.nextLong(), "Hi!"));
    }
}
