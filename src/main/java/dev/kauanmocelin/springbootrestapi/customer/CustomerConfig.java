package dev.kauanmocelin.springbootrestapi.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            Customer maria = new Customer(
                    "Maria",
                    "maria@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 1)
            );
            Customer alex = new Customer(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(1998, Month.DECEMBER, 10)
            );

            //customerRepository.saveAll(List.of(maria, alex));
        };
    }
}
