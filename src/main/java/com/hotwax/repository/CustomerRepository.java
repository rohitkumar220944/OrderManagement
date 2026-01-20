package com.hotwax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotwax.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}