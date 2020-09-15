package daniel.varga.jms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {
    private UUID id;
    private String message;
}
