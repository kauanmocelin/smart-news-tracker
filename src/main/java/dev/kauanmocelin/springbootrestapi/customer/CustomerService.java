package dev.kauanmocelin.springbootrestapi.customer;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class CustomerService {
    public List<Customer> getCustomers() {
        return List.of(
                new Customer(1L, "Maria", "maria@gmail.com", LocalDate.of(2000, Month.JANUARY, 1))
        );
    }
}
