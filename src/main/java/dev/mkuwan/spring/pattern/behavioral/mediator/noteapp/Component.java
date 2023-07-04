package dev.mkuwan.spring.pattern.behavioral.mediator.noteapp;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
