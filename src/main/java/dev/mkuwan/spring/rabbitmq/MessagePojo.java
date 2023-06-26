package dev.mkuwan.spring.rabbitmq;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class MessagePojo {
    private String id;
    private String message;
}
