package br.com.siecola.aws_project01.model.sns;

import br.com.siecola.aws_project01.enums.EventType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Envelope {
    private EventType eventType;
    private String data;
}
