package com.example.Inventory_Management_System.Service;

import com.example.Inventory_Management_System.Entity.Customer;
import com.example.Inventory_Management_System.Entity.Product;
import com.example.Inventory_Management_System.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public void savedata(Product productData) {


    productRepository.save(productData);
    }
}
