package com.ar.gl.customer.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ar.gl.customer.shop.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
