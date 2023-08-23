package dev.mkuwan.spring.event;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomEventCommandRunner implements ApplicationRunner {
    private final CustomEventPublisher publisher;

    public CustomEventCommandRunner(CustomEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        publisher.publishEvent("こんにちは、メッセージをお届けします");

    }
}
