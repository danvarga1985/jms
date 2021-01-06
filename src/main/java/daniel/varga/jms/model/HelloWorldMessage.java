package daniel.varga.jms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    // Mainly needed, if message type is 'Java Object' instead of 'text'.
    static final long serialVersionUID = -5282019346851598898L;

    private UUID id;
    private String message;
}
