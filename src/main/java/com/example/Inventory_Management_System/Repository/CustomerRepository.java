package com.example.Inventory_Management_System.Repository;

import com.example.Inventory_Management_System.Entity.Customer;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer,Integer>{

}