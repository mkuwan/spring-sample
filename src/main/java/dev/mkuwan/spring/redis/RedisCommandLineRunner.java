package dev.mkuwan.spring.redis;

import dev.mkuwan.spring.SpringSampleApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisCommandLineRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(RedisCommandLineRunner.class);

    private final ApplicationContext context;

    public RedisCommandLineRunner(ApplicationContext context){
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);

        RedisReceiver receiver = context.getBean(RedisReceiver.class);

        while (receiver.getCount() < 5){
            logger.info("Sending message");
            template.convertAndSend("chat", "Hello from RedisApp!");
            Thread.sleep(500L);
        }
    }


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                            MessageListenerAdapter adapter){
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, new PatternTopic("chat"));

        return container;
    }



    @Bean
    MessageListenerAdapter listenerAdapter(RedisReceiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    RedisReceiver receiver(){
        return new RedisReceiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }


}
