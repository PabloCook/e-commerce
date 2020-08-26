package com.ar.gl.customer.shop.Customershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ar.gl.customer.shop.Customershop.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
