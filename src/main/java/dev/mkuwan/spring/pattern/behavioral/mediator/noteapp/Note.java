package dev.mkuwan.spring.pattern.behavioral.mediator.noteapp;

public class Note {
    private String name;
    private String text;

    public Note() {
        name = "New note";
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
