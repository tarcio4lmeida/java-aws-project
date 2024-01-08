package br.com.siecola.aws_project01.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"invoiceNumber"})
        }
)
@Data
@Entity
public class Invoice implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true, name = "invoice_number")
    private String invoiceNumber;

    @Column(length = 32, nullable = false, name = "customer_name")
    private String customerName;

    @Column(name = "total_value")
    private Float totalValue;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;
}