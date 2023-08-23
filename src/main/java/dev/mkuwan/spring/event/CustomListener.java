package dev.mkuwan.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomListener implements ApplicationListener<CustomEvent> {

    @Override
    @Async("taskExecutor")
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("Custom Event を受信しました: " + event.getMessage());
    }
}
