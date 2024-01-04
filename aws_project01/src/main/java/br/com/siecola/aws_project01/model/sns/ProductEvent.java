package br.com.siecola.aws_project01.model.sns;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEvent {
    private long id;
    private  String code;
    private String userName;
}
