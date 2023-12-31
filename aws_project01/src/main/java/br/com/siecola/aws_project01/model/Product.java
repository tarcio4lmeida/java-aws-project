package br.com.siecola.aws_project01.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"})
        }
)
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