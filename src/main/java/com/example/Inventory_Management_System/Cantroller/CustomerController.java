package com.example.Inventory_Management_System.Cantroller;

import com.example.Inventory_Management_System.Entity.Customer;
import com.example.Inventory_Management_System.Entity.Product;
import com.example.Inventory_Management_System.Service.CustomerService;
import com.example.Inventory_Management_System.Service.ProductService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abc")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @PostMapping("/cData")
    public void saveData(@RequestBody Customer customer){
        customerService.saveData(customer);
    }

    @PostMapping("productData")
    public void savePData(@RequestBody Product productData){
        productService.savedata(productData);
    }
}