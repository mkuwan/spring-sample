package dev.mkuwan.spring.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * メッセージを受信する方法を定義する POJO
 */
public class RedisReceiver {
    private static final Logger logger = LoggerFactory.getLogger(RedisReceiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message){

        counter.incrementAndGet();

        logger.info(message + "を受信しました [ " + getCount() + " 回目]");
    }

    public int getCount(){
        return counter.get();
    }
}
