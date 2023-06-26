package dev.mkuwan.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mkuwan.spring.rabbitmq.MessagePojo;
import dev.mkuwan.spring.rabbitmq.RabbitMqConfig;
import dev.mkuwan.spring.rabbitmq.RabbitMqReceiver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

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
@SpringRabbitTest
@SpringBootTest
public class RabbitMqTest {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqTest.class);

    @Captor
    private ArgumentCaptor<Message> amqpMessage;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitMqReceiver rabbitMqReceiver;

    private RabbitMessagingTemplate messagingTemplate;

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp(){
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        messagingTemplate = new RabbitMessagingTemplate(rabbitTemplate);
        rabbitMqReceiver = new RabbitMqReceiver();
    }

    @AfterEach
    void tearDown(){
        try {
            this.autoCloseable.close();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    @Test
    void validateRabbitTemplate() {
        Assertions.assertEquals(this.rabbitTemplate, messagingTemplate.getRabbitTemplate());
        this.rabbitTemplate.afterPropertiesSet();
    }

    /**
     * RabbitMq自体の動作確認
     */
    @Test
    void correlation(){
        // arrange
        this.messagingTemplate.setDefaultDestination("defaultDestination");

        // act: payloadのみ
        this.messagingTemplate.send(new GenericMessage<>("payload",
                Collections.singletonMap(AmqpHeaders.PUBLISH_CONFIRM_CORRELATION,
                        new CorrelationData())));
        // assertion
        verify(this.rabbitTemplate).send(eq("defaultDestination"), any(), any(CorrelationData.class));

        // act: destination + payload
        this.messagingTemplate.send("destinationA",
                new GenericMessage<>("payload",
                Collections.singletonMap(AmqpHeaders.PUBLISH_CONFIRM_CORRELATION,
                        new CorrelationData())));
        // assertion
        verify(this.rabbitTemplate).send(eq("destinationA"), any(), any(CorrelationData.class));

        // act: exchange + routingKey
        this.messagingTemplate.send("exchange", "routing",
                new GenericMessage<>("payload",
                        Collections.singletonMap(AmqpHeaders.PUBLISH_CONFIRM_CORRELATION,
                                new CorrelationData())));
        // assertion
        verify(this.rabbitTemplate).send(eq("exchange"), eq("routing"), any(), any(CorrelationData.class));
    }

    @Test
    void simpleMessageSend() {

        try{
            System.out.println("Sending Simple Text Message...");
            // AMQPによるメッセージング
            // https://labs.gree.jp/blog/2010/06/262/

            // Sender
            // MessageをRoutingKeyに従ってExchangeに渡されます
            // ExchangeはBindingに基づいてQueueに引き渡します
            // 第3引数で送信するObjectを色々作れる POJOやjsonでの送り方は別メソッドで作成してみた

            // Topic Exchange
            // routing key の一部一致で送れる  設定値: ROUTING_KEY = "dev.mkuwan.groupware.#";
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

//    @Test
    public void send() {
        Message<String> message = createTextMessage();

        messagingTemplate.send("myQueue", message);
        verify(rabbitTemplate).send(eq("myQueue"), (org.springframework.amqp.core.Message) this.amqpMessage.capture());
        assertTextMessage(this.amqpMessage.getValue());

    }




    private Message<String> createTextMessage(String payload) {
        return MessageBuilder
                .withPayload(payload).setHeader("foo", "bar").build();
    }

    private Message<String> createTextMessage() {
        return createTextMessage("Hello");
    }


    private org.springframework.amqp.core.Message createAmqpTextMessage(String payload) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("foo", "bar");
        return RabbitMqMessageTestUtils.createTextMessage(payload, properties);
    }

    private org.springframework.amqp.core.Message createAmqpTextMessage() {
        return createAmqpTextMessage("Hello");
    }

    private void assertTextMessage(org.springframework.amqp.core.Message amqpMsg) {
        assertThat(RabbitMqMessageTestUtils.extractText(amqpMsg)).as("Wrong body message").isEqualTo("Hello");
        assertThat(amqpMsg.getMessageProperties().getHeaders().get("foo")).as("Invalid foo property").isEqualTo("bar");
    }

    private void assertTextMessage(Message<?> message) {
        assertThat(message).as("message should not be null").isNotNull();
        assertThat(message.getPayload()).as("Wrong payload").isEqualTo("Hello");
        assertThat(message.getHeaders().get("foo")).as("Invalid foo property").isEqualTo("bar");
    }
}
