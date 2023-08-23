package dev.mkuwan.spring.pattern.behavioral.observer.gurusample;

import java.io.File;

public interface EventListener {
    void update(String eventType, File file);
}
