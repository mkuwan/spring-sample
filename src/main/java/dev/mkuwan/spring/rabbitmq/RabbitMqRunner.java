package dev.mkuwan.spring.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * RabbiMq コマンド
 * 　　rabbitmqctl cluster_status
 * 　　rabbitmqctl list_vhosts
 * 　　rabbitmqctl list_users
 * 　　rabbitmqctl list_user_permissions <user>
 * 　　rabbitmqctl list_parameters
 * 　　rabbitmqctl list_global_parameters
 * 　　rabbitmqctl list_policies
 * 　　rabbitmqctl list_permissions
 * 　　rabbitmqctl list_queues
 * 　　rabbitmqctl list_exchanges
 * 　　rabbitmqctl list_bindings
 * 　　rabbitmqctl list_connections
 * 　　rabbitmqctl list_channels
 * 　　rabbitmqctl list_consumers
 * 　　rabbitmqctl list_hashes
 * 　　rabbitmqctl list_ciphers
 */

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
            StringSend();
            POJOtoJsonSend();
            POJOSend();

        } catch (Exception e){
            System.out.println("Error Occurred " + e.getMessage());
        }
    }

    /**
     * String
     */
    public void StringSend(){
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
    }

    /**
     * Java Class(POJO)をJson(String)に変換して送信
     * @throws JsonProcessingException　mapper.writeValueAsString実装した場合の例外
     */
    public void POJOtoJsonSend() throws JsonProcessingException {
        MessagePojo messagePojo = MessagePojo
                .builder()
                .id("POJO to json id")
                .message("POJO to Json Message")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = mapper.writeValueAsString(messagePojo);

        System.out.println("POJO to Json Send...");
        rabbitTemplate.convertAndSend(RabbitMqConfig.DIRECT_EXCHANGE,
                RabbitMqConfig.KEY,
                jsonMessage);
    }

    /**
     * POJO(バイナリデータ)で送信する場合
     * public void receivePOJOMessage(MessagePojo messagePojo)で受信する
     */
    public void POJOSend(){
        MessagePojo messagePojo = MessagePojo
                .builder()
                .id("POJO id")
                .message("POJO Message")
                .build();

        System.out.println("POJO Send...");
        rabbitTemplate.convertAndSend(RabbitMqConfig.DIRECT_EXCHANGE,
                RabbitMqConfig.KEY,
                messagePojo);
    }
}
