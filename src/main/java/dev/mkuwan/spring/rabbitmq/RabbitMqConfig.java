package dev.mkuwan.spring.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String QUEUE_NAME = "dev.mkuwan.spring.queue";
    public static final String FANOUT_EXCHANGE = "mkuwa-fanout-exchange";
    public static final String TOPIC_EXCHANGE = "mkuwa-topic-exchange";
    public static final String DIRECT_EXCHANGE = "mkuwa-direct-exchange";
    public static final String ROUTING_KEY = "dev.mkuwan.spring.#";
    public static final String KEY = "mkuwa-key";

    // ---------------- queue ------------------
    /**
     * AMQPキューを作成する
     * Messageを蓄積しConsumerに引き渡す役割を持ちます
     * @return キュー
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }


    // ---------------- Exchange ------------------
    /**
     * Fanout ExchangeとBindされている全てのキューに送ります
     * チュートリアル3章　https://www.rabbitmq.com/tutorials/tutorial-three-java.html
     * @return Fanout Exchange
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * Direct Exchangeは、Messageに付与されているRouting KeyとBinding Keyを見て
     * Routing Key = Binding KeyとなるようなQueueにMessageを送ります
     * チュートリアル4章　https://www.rabbitmq.com/tutorials/tutorial-four-java.html
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange(DIRECT_EXCHANGE);
    }

    /**
     * Topic ExchangeはRouting Keyを元に、部分一致で配送先を選択させることができます
     * チュートリアル5章　https://www.rabbitmq.com/tutorials/tutorial-five-java.html
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange(){
        return  new TopicExchange(TOPIC_EXCHANGE);
    }


//    /**
//     * POJOをJSONにして送るもの
//     * @return
//     */
//    @Bean
//    public MessageConverter messageConverter(){
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.findAndRegisterModules();
//        return new Jackson2JsonMessageConverter(mapper);
//    }
//
//    /**
//     *
//     * @param factory
//     * @param converter
//     * @return
//     */
//    @Bean
//    public AmqpTemplate template(ConnectionFactory factory, MessageConverter converter){
//        RabbitTemplate template = new RabbitTemplate(factory);
//        template.setMessageConverter(converter);
//        return template;
//    }


    // ---------------- Binding ------------------
    /**
     * binding() メソッドはqueue()とexchange()の2つを結合し、
     * RabbitTemplate がエクスチェンジに公開するときに発生する動作を定義します
     * @param queue Queue
     * @param topicExchange TopicExchange
     * @return Binding
     */
    @Bean
    public Binding topicBinding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding directBinding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(KEY);
    }

    @Bean
    public Binding fanoutBinding(Queue queue, FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(queue).to(fanoutExchange);
    }


    // ---------------- Connection ------------------
//    /**
//     * connectionFactory 無くてもSpringBootで勝手に作成してくれる。これを全てコメント化した場合はデフォルト設定が使用されるみたい
//     * @return ConnectionFactory
//     */
//    @Bean
//    public ConnectionFactory connectionFactory(){
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        // 中身はこんな感じ CachingConnectionFactory [channelCacheSize=25, host=localhost, port=5672, active=true
//        //                  org.springframework.amqp.rabbit.connection.CachingConnectionFactory@373f7450]
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//
//        return connectionFactory;
//    }



    // ---------------- リスナー(受信側)に関する設定 ------------------
    /**
     * リスナーコンテナー
     * @param connectionFactory ConnectionFactory
     * @param listenerAdapter MessageListenerAdapter(Beanで設定されたリスナー)
     * @return SimpleMessageListenerContainer:
     *      指定されたリスナー用の MessageConsumers を作成するメッセージリスナーコンテナー
     */
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter); // container.setMessageListener(new MessageListenerAdapter(new RabbitMqReceiver()));

        return  container;
    }

    /**
     * メッセージのリスナー登録　
     * ReceiverクラスをMessageListenerインターフェースへ適用されるのがこのアダプターです
     * このアダプターはSimpleMessageListenerContainerの設定時に指定します
     * @param receiver (リスナークラス)
     * @return MessageListenerAdapter
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RabbitMqReceiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
