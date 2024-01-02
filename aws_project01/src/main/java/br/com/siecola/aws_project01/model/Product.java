package br.com.siecola.aws_project01.model;

import jakarta.persistence.*;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"})
        }
)
@Entity
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

    public Product() {
    }

    public Product(String name, String model, String code) {
        this.name = name;
        this.model = model;
        this.code = code;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

