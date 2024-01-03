package br.com.siecola.aws_project01.controller;

import br.com.siecola.aws_project01.enums.EventType;
import br.com.siecola.aws_project01.model.Product;
import br.com.siecola.aws_project01.repository.ProductRepository;
import br.com.siecola.aws_project01.service.ProductSnsPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;
    private final ProductSnsPublisher productSnsPublisher;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        LOG.info("Listando produtos: {}", products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        LOG.info("Retornando produto de id: {}", id);
        return optProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product productCreated = productRepository.save(product);
        LOG.info("Objeto inserido : {}", product);
        productSnsPublisher.publishProductEvent(product, EventType.PRODUCT_CREATED, "user_created");
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product,
                                                 @PathVariable("id") long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct.map(prod -> {
            product.setId(prod.getId());
            Product productUpdated = productRepository.save(product);
            productSnsPublisher.publishProductEvent(product, EventType.PRODUCT_UPDATE, "user_updated");
            LOG.info("Update do objeto : {}", product);
            return new ResponseEntity<>(productUpdated, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Optional<Product> optProduct = productRepository.findById(id);
        return optProduct.map(prod -> {
            LOG.info("Deletando objeto : {}", prod);
            productRepository.delete(prod);
            productSnsPublisher.publishProductEvent(prod, EventType.PRODUCT_DELETED, "user_deleted");
            return new ResponseEntity<>(prod, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/bycode")
    public ResponseEntity<Product> findByCode(@RequestParam String code) {
        Optional<Product> optProduct = productRepository.findByCode(code);
        LOG.info("Find by code : {}", code);
        return optProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
