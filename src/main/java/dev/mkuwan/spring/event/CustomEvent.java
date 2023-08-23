package dev.mkuwan.spring.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CustomEvent extends ApplicationEvent {
    private String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
