package daniel.varga.jms.sender;

import daniel.varga.jms.config.JmsConfig;
import daniel.varga.jms.model.HelloWorldMessage;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = (2000))
    public void sendMessage() {
        System.out.println("I'm sending a message!");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Message sent!");
    }
}
