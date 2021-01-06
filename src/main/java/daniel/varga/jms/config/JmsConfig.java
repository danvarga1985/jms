package daniel.varga.jms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    // Set up queue names.
    public static final String MY_QUEUE = "my-hello-world";
    public static final String MY_SEND_RCV_QUEUE = "reply-back-to-me";

    // Serialize message content to json using TextMessage. For the most compatibility: "Text" with 'JSON' payload.
    @Bean
    public MessageConverter messageConverter() {
        // Conversion goes both ways: Object -> JSON, JSON -> Object.
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        // Header property.
        converter.setTypeIdPropertyName("_type");

        return converter;
    }
}
