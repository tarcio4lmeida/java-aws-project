package br.com.siecola.aws_project01.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 25, nullable = false)
    private String model;

    @Column(length = 8, nullable = false)
    private String code;
}