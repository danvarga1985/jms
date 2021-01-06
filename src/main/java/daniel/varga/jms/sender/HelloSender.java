package daniel.varga.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import daniel.varga.jms.config.JmsConfig;
import daniel.varga.jms.model.HelloWorldMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    // Preconfigured template.
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = (2000))
    public void sendMessage() {
        System.out.println("SENDER: I'm sending a message!");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Message sent!");
    }

    // Code is not-async, from 'sendAndReceive', the thread is blocked until the message returns back.
    @Scheduled(fixedRate = (2000))
    public void sendAndReceiveMessage() throws JMSException {
        System.out.println("I'm sending a message!");

        HelloWorldMessage payloadMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;

                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(payloadMessage));
                    helloMessage.setStringProperty("_type", "daniel.varga.jms.model.HelloWorldMessage");

                    System.out.println("Sending Hello");

                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("bang");
                }
            }
        });

        System.out.println("Received Message: " + receivedMsg.getBody(String.class));
    }
}
