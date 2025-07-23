package com.example.Inventory_Management_System.Repository;

import com.example.Inventory_Management_System.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {

}
