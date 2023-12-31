package br.com.siecola.aws_project01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    private final static Logger log = LoggerFactory.getLogger(TesteController.class);
    @GetMapping("/hello-world")
    public String HelloWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world2")
    public String HelloWorld2() {
        return "Testando novo endpoint";
    }

    @GetMapping("/hello-world3")
    public void HelloWorld3() {
        log.info("Testando o cloudwatch ");
    }
}
