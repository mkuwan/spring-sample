package dev.mkuwan.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.io.UnsupportedEncodingException;

public abstract class RabbitMqMessageTestUtils {
    /**
     * Create a text message with the specified {@link MessageProperties}. The
     * content type is set no matter
     */
    public static Message createTextMessage(String body, MessageProperties properties) {
        properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        return new org.springframework.amqp.core.Message(toBytes(body), properties);
    }

    /**
     * Create a text message with the relevant content type.
     */
    public static Message createTextMessage(String body) {
        return createTextMessage(body, new MessageProperties());
    }

    /**
     * Extract the text from the specified message.
     */
    public static String extractText(Message message) {
        try {
            return new String(message.getBody(), SimpleMessageConverter.DEFAULT_CHARSET);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Should not happen", e);
        }
    }

    private static byte[] toBytes(String content) {
        try {
            return content.getBytes(SimpleMessageConverter.DEFAULT_CHARSET);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
