package dev.mkuwan.spring.mongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;

@EnableMongoRepositories(basePackageClasses = CustomerRepository.class)
@Configuration
public class MongoConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return inits -> {
            var exist1 = customerRepository.findByCustomerId("test001");
            if(exist1 == null)
                customerRepository.save(customer1());

            var exist2 = customerRepository.findByCustomerId("test002");
            if(exist2 == null)
                customerRepository.save(customer2());
        };
    }

    private Customer customer1(){
        var customer = new Customer();
//        customer.setId(UUID.randomUUID().toString());
        customer.setCustomerId("test001");
        customer.setCustomerName("test001");
        customer.setAge(21);
        customer.setPostCode("111-1111");
        customer.setAddress("test address 001");
        customer.setGender("M");

        return customer;
    }

    private Customer customer2(){
        var customer = new Customer();
//        customer.setId(UUID.randomUUID().toString());
        customer.setCustomerId("test002");
        customer.setCustomerName("test002");
        customer.setAge(22);
        customer.setPostCode("222-2222");
        customer.setAddress("test address 002");
        customer.setGender("F");

        return customer;
    }


}
