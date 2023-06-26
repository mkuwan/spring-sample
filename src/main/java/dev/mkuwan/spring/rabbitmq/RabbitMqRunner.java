package dev.mkuwan.spring.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqRunner implements ApplicationRunner {
    private final RabbitTemplate rabbitTemplate;
//    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    public RabbitMqRunner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
//        this.rabbitMessagingTemplate = new RabbitMessagingTemplate(this.rabbitTemplate);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try{
            System.out.println("Sending Simple Text Message...");
            // AMQPによるメッセージング
            // https://labs.gree.jp/blog/2010/06/262/

            // Sender
            // MessageをRoutingKeyに従ってExchangeに渡されます
            // ExchangeはBindingに基づいてQueueに引き渡します
            // 第3引数で送信するObjectを色々作れる POJOやjsonでの送り方は別メソッドで作成してみた

            // Topic Exchange
            // routing key の一部一致で送れる  設定値: ROUTING_KEY = "dev.mkuwan.spring.#";
            rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,
                    "dev.mkuwan.spring.test",
                    "Hello from RabbitMQ By Topic Exchange");
            rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,
                    "dev.mkuwan.spring.any",
                    "Hello from RabbitMQ By Topic Exchange2");


            // 一致しないと送れない
            rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,
                    "dev.mkuwan.any.any",
                    "Hello from RabbitMQ By Topic Exchange3");

            // Direct Exchange
            // directBindingで指定しているkeyだと送れる 設定値: KEY = "key"
            rabbitTemplate.convertAndSend(RabbitMqConfig.DIRECT_EXCHANGE,
                    RabbitMqConfig.KEY,
                    "Hello from RabbitMQ By Direct Exchange");
            // 異なるkeyだと送れない
            rabbitTemplate.convertAndSend(RabbitMqConfig.DIRECT_EXCHANGE,
                    "irregular_key",
                    "Hello from RabbitMQ By Direct Exchange2");


            // Fanout Exchange
            // keyに関係なく送れる
            rabbitTemplate.convertAndSend(RabbitMqConfig.FANOUT_EXCHANGE,
                    "",
                    "Hello from RabbitMQ By Fanout Exchange");

        } catch (Exception e){
            System.out.println("Error Occurred " + e.getMessage());
        }
    }
}
