package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.customer.Customer;

import java.time.LocalDate;

public class CustomerCreator {

    public static Customer createCustomerToBeSaved() {
        return Customer.builder()
                .name("Fulano da Silva")
                .email("fulano@gmail.com")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();
    }

    public static Customer createValidCustomer() {
        return Customer.builder()
                .id(1L)
                .name("Fulano da Silva")
                .email("fulano@gmail.com")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();
    }

    public static Customer createValidUpdatedCustomer() {
        return Customer.builder()
                .id(1L)
                .name("Ciclano de Almeida")
                .email("ciclano@gmail.com")
                .dateOfBirth(LocalDate.of(1995, 12, 1))
                .build();
    }
}
