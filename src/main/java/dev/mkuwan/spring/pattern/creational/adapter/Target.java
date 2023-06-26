package dev.mkuwan.spring.pattern.creational.adapter;

public class Target {
    protected String voltage = "100";
    protected String terminal = "A";

    public void Display(){
        System.out.println("日本の電圧:" + voltage + ", 端子:" + terminal);
    }
}
