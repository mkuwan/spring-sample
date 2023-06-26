package dev.mkuwan.spring.pattern.creational.adapter;

public class Adaptor extends Target{

    private final String location;
    private Adaptee adaptee;

    public Adaptor(String location){
        this.location = location;
        adaptee = new Adaptee();
    }

    @Override
    public void Display() {
        voltage = adaptee.getVoltage(location);
        terminal = adaptee.getTerminal(location);
        System.out.println(location + ":= 電圧:" + voltage + ", 端子:" + terminal);
    }

    public String getVoltage(){
        return adaptee.getVoltage(location);
    }
}
