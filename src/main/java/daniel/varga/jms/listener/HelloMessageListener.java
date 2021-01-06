package daniel.varga.jms.listener;

import daniel.varga.jms.config.JmsConfig;
import daniel.varga.jms.model.HelloWorldMessage;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@AllArgsConstructor
@Component
public class HelloMessageListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) {
//        System.out.println("I got a message");
//
//        System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) throws JMSException {
        HelloWorldMessage payloadMsg = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }

}
