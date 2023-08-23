package dev.mkuwan.spring.pattern.behavioral.observer.news;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency {
    private String news;
    private List<Channel> channelList = new ArrayList<>();

    public void addObserver(Channel channel){
        var existChannel = channelList.stream().filter(x -> x.equals(channel)).findFirst();

        if(!existChannel.isPresent())
            this.channelList.add(channel);
    }

    public void removeObserver(Channel channel){
        var existChannel = channelList.stream().filter(x -> x.equals(channel)).findFirst();

        if(existChannel.isPresent())
            this.channelList.remove(channel);
    }
    
    public void notifyNews(String news){
        this.news = news;
        for (Channel channel :
                this.channelList) {
            channel.update(this.news);
        }
    }

}
