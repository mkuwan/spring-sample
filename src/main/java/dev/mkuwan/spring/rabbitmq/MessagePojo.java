package dev.mkuwan.spring.rabbitmq;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Builder
@Data
public class MessagePojo implements Serializable {
    private String id;
    private String message;
}
