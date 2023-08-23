package dev.mkuwan.spring.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomEventPublisher {
    private final ApplicationEventPublisher publisher;

    public CustomEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(final String message){
        System.out.println("Publishing custom event");
        CustomEvent event = new CustomEvent(this, message);
        publisher.publishEvent(event);
    }
}
