package dev.mkuwan.spring.kafka.consumer;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean
    public KafkaAdmin admin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1(){
        return TopicBuilder.name("topic1")
                .partitions(1)
                .replicas(1)
                .compact().build();
    }

//    @Bean
//    public NewTopic topic2(){
//        return TopicBuilder.name("topic2")
//                .partitions(10)
//                .replicas(3)
//                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .compact().build();
//    }
//
//    @Bean
//    public NewTopic topic3(){
//        return TopicBuilder.name("topic3")
//                .assignReplicas(0, Arrays.asList(0, 1))
//                .assignReplicas(1, Arrays.asList(1, 2))
//                .assignReplicas(2, Arrays.asList(2, 0))
//                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
//                .compact().build();
//    }
//
//    @Bean
//    public NewTopic topic4() {
//        return TopicBuilder.name("topic4")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic5() {
//        return TopicBuilder.name("topic5")
//                .replicas(1)
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic6() {
//        return TopicBuilder.name("topic6")
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public KafkaAdmin.NewTopics topics789(){
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("topic7").build(),
//                TopicBuilder.name("topic8")
//                        .replicas(1).build(),
//                TopicBuilder.name("topic9")
//                        .partitions(3).build()
//        );
//    }

    @KafkaListener(topics = {"general-topic"}, groupId = "task-group")
    public void listen(String in){
        System.out.println("Kafkaのtopicを受信しました:" + in);
    }
}
