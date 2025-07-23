package com.example.Inventory_Management_System.Cantroller;

import com.example.Inventory_Management_System.Service.ProductStockReport;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ProductReportController {


    @Autowired
    private ProductStockReport productStockReport;

    @GetMapping("/send")
    public String sendStockReport(@RequestParam("email") String email) {
        try {
            productStockReport.sendProductReportEmail(email);
            return "Stock report sent successfully to: " + email;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send stock report to: " + email;
        }
    }
}

