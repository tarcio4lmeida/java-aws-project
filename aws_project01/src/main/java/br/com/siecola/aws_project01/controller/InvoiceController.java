package br.com.siecola.aws_project01.controller;

import br.com.siecola.aws_project01.model.Invoice;
import br.com.siecola.aws_project01.model.UrlResponse;
import br.com.siecola.aws_project01.repository.InvoiceRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    @Value("${aws.s3.bucket.invoice.name}")
    private String bucketName;
    private final AmazonS3 amazonS3;

    private final InvoiceRepository repository;

    @PostMapping
    public ResponseEntity<UrlResponse> createInvoiceUrl() {
        String processId = UUID.randomUUID().toString();
        Instant expirationTime = Instant.now().plus(Duration.ofMinutes(5));

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, processId)
                        .withMethod(com.amazonaws.HttpMethod.PUT)
                        .withExpiration(Date.from(expirationTime));

        final UrlResponse urlResponse = UrlResponse.builder()
                .url(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString())
                .expirationTime(expirationTime.getEpochSecond())
                .build();

        return ResponseEntity.ok(urlResponse);
    }

    @GetMapping
    public ResponseEntity<Iterable<Invoice>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(path = "/customername")
    public ResponseEntity<Iterable<Invoice>> findAllByCustomerName(@RequestParam String customerName) {
        return ResponseEntity.ok(repository.findAllByCustomerName(customerName));
    }
}