package dev.mkuwan.spring.pattern.creational.adapter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdapterRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Target original = new Target();
        original.Display();

        Adaptor adaptorJapan = new Adaptor("日本");
        adaptorJapan.Display();

        Adaptor adaptorUS = new Adaptor("アメリカ");
        adaptorUS.Display();
    }
}
