package dev.mkuwan.spring.pattern.behavioral.observer.news;

public class NewsChannel implements Channel{
    private String news;
    private String newsChannelName;

    public NewsChannel(String newsChannelName){
        this.newsChannelName = newsChannelName;
    }

    @Override
    public void update(String news) {
        this.news = news;
        System.out.println(newsChannelName + "からニュースです....「" + news + "」");
    }

    public String getNews() {
        return news;
    }

}
