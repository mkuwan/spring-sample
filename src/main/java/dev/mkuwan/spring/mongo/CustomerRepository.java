package dev.mkuwan.spring.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
//@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByCustomerId(String customerId);
    List<Customer> findByGenderAndPostCode(String gender, String postCode);
    List<Customer> findByOrdersOrderDateBetween(LocalDate from, LocalDate to);

    @Query("{ 'orders.totalPrice' : { $gt : ?0 } }")
    List<Customer> findByTotalPriceGt(int minPrice);

    Customer deleteByCustomerId(String customerId);
}
