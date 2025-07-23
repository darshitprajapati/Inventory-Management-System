package com.example.Inventory_Management_System.Repository;

import com.example.Inventory_Management_System.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}