package com.example.Inventory_Management_System.Service;

import com.example.Inventory_Management_System.Entity.Customer;
import com.example.Inventory_Management_System.Repository.CustomerRepository;
import com.example.Inventory_Management_System.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public void saveData(Customer customer){
        customerRepository.save(customer);
    }
}
