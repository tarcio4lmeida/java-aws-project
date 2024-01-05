package br.com.siecola.aws_project02.repository;

import br.com.siecola.aws_project02.model.ProductEventKey;
import br.com.siecola.aws_project02.model.ProductEventLog;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@EnableScan
public interface ProductEventLogRepository extends JpaRepository<ProductEventLog, ProductEventKey> {

    List<ProductEventLog> findAllByPk(String pk);
    List<ProductEventLog> findAllByPkAndSkStartsWith(String pk, String sk);
}