package dev.mkuwan.spring.pattern.behavioral.observer.news;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class NewsCommandRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        NewsAgency observable = new NewsAgency();
        NewsChannel observerNHK = new NewsChannel("NHK");
        NewsChannel observerTBS = new NewsChannel("TBS");
        NewsChannel observerCATV = new NewsChannel("CATV");

        observable.addObserver(observerNHK);
        observable.notifyNews("最新情報");

        observable.addObserver(observerTBS);
        observable.addObserver(observerCATV);
        observable.notifyNews("三局同時配信の最新ニュースですよ");

        observable.removeObserver(observerNHK);
        observable.notifyNews("NHKには知らせていない最新ニュースです");

    }
}
