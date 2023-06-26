package dev.mkuwan.spring.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    public void receiveMessage(Object message) throws InterruptedException {
        Thread.sleep(1500);
        if(message.getClass() == String.class){
            logger.info("RabbitMq メッセージ受信:" + message);
            return;
        }

        if(message.getClass() == MessagePojo.class){
            var pojo = (MessagePojo)message;
            logger.info("RabbitMq メッセージ受信 From POJO: " + "id: " + pojo.getId() + " message: " + pojo.getMessage());
        }

    }


}
