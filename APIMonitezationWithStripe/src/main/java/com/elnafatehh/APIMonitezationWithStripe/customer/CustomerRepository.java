package com.elnafatehh.APIMonitezationWithStripe.customer;

import com.elnafatehh.APIMonitezationWithStripe.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByStripeCustomerId(String stripeCustomerId);
}
