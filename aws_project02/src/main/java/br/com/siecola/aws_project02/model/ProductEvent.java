package br.com.siecola.aws_project02.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    private long id;
    private String code;
    private String username;
}