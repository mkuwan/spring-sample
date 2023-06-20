package dev.mkuwan.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mkuwan.spring.mongo.Customer;
import dev.mkuwan.spring.mongo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerRepositoryTest {
    private static final String COL_NAME = "customer";

    private static final String DATA_PATH = "mongo/init/01_import_init_data.json";

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() throws Exception {
        // Delete all documents from collection.
        mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Customer.class, COL_NAME)
                .remove(new Query()).execute();

        // Load test data to MongoDB.
        List<Customer> customers =
                Arrays.asList(mapper.readValue(new File(DATA_PATH), Customer[].class));
        mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Customer.class, COL_NAME)
                .insert(customers).execute();
    }

    @DisplayName("Check that the customer is retrieved by its identity.")
    @Test
    public void testFindByCustomerId() {
        String customerId = "USR00001";

        Customer actual = repository.findByCustomerId(customerId);
        assertEquals(customerId, actual.getCustomerId());
        assertEquals(35, actual.getAge());
        assertEquals("999-9999", actual.getPostCode());
        assertEquals("ORD00001", actual.getOrders().get(0).getOrderId());
        assertEquals("apple",
                actual.getOrders().get(0).getOrderedProducts().get(0).getProductName());
    }

    @DisplayName("Check that the only customers with the provided gender and postCode are retrieved.")
    @Test
    public void testFindByGenderAndPostCode() {
        String gender = "M";
        String postCode = "999-9999";

        List<Customer> actuals = repository.findByGenderAndPostCode(gender, postCode);
        assertEquals(2, actuals.size());
        assertEquals("USR00001", actuals.get(0).getCustomerId());
        assertEquals("USR00004", actuals.get(1).getCustomerId());
    }

    @DisplayName("Check that the only customers who have ordered recently are retrieved.")
    @Test
    public void testFindByOrdersOrderDateBetween() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = LocalDate.of(2021, 1, 1);

        List<Customer> actuals = repository.findByOrdersOrderDateBetween(from, to);
        assertEquals(1, actuals.size());
        assertEquals("USR00001", actuals.get(0).getCustomerId());
    }

    @DisplayName("Check that the only customers who have ordered expensive products are retrieved.")
    @Test
    public void testFindByTotalPriceGt() {
        int minPrice = 4000;

        List<Customer> actuals = repository.findByTotalPriceGt(minPrice);
        assertEquals(1, actuals.size());
        assertEquals("USR00002", actuals.get(0).getCustomerId());
    }

    @DisplayName("Check that the customer is deleted by its identity.")
    @Test
    public void testDeleteByCustomerId() {
        String customerId = "USR00004";

        Customer actual = repository.deleteByCustomerId(customerId);
        assertEquals(customerId, actual.getCustomerId());
        assertEquals(12, actual.getAge());
        assertEquals("999-9999", actual.getPostCode());

    }
}
