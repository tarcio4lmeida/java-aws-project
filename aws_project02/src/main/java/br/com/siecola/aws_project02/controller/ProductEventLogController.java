package br.com.siecola.aws_project02.controller;

import br.com.siecola.aws_project02.model.ProductEventLogResponse;
import br.com.siecola.aws_project02.repository.ProductEventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductEventLogController {

    private final ProductEventLogRepository repository;

    @GetMapping("/events")
    public List<ProductEventLogResponse> getAllEvents() {
        return repository.findAll()
                .stream()
                .map(ProductEventLogResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/{code}")
    public List<ProductEventLogResponse> findByCode(@PathVariable String code) {
        return repository.findAllByPk(code)
                .stream()
                .map(ProductEventLogResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/{code}/{event}")
    public List<ProductEventLogResponse> findByCodeAndEventType(@PathVariable String code, @PathVariable String event) {
        return repository.findAllByPkAndSkStartsWith(code, event)
                .stream()
                .map(ProductEventLogResponse::new)
                .collect(Collectors.toList());
    }
}