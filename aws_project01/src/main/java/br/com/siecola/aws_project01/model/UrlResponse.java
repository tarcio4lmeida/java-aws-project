package br.com.siecola.aws_project01.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlResponse {
    private String url;
    private long expirationTime;
}
