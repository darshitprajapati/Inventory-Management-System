package com.example.Inventory_Management_System.Cantroller;

import com.example.Inventory_Management_System.Dto.SaveDto;
import com.example.Inventory_Management_System.Entity.Order;
import com.example.Inventory_Management_System.Repository.OrderRepository;
import com.example.Inventory_Management_System.Service.Orderservice;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderCantroller {

    @Autowired
    Orderservice orderservice;

   @Autowired
    private OrderRepository orderRepo;

    @PostMapping("/setData")
    public String setOrdDetl(@RequestBody SaveDto saveDto) throws RazorpayException {
        System.out.println(saveDto.getCustId());
        System.out.println(saveDto.getProdId());
        System.out.println(saveDto.getQuntity());

        return orderservice.setOrderDetails(saveDto);
    }

    @GetMapping("/emailReport")
    public String emailReport(@RequestParam String to) {
        try {
            List<Order> orders = orderRepo.findAll();
            return "Report sent to " + to;
        } catch (Exception e) {
            return "Failed: " + e.getMessage();
        }
    }
}
