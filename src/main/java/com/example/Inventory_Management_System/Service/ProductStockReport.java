package com.example.Inventory_Management_System.Service;

import com.example.Inventory_Management_System.Entity.Product;
import com.example.Inventory_Management_System.Repository.ProductRepository;
import com.opencsv.CSVWriter; // ✅ Make sure to import this

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class ProductStockReport {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public void sendProductReportEmail(String toEmail) throws MessagingException {
        List<Product> products = productRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        CSVWriter writer = new CSVWriter(outputStreamWriter); // OpenCSV writer

        // Write header
        String[] header = {"ID", "Name", "Quantity", "Price"};
        writer.writeNext(header);

        // Write product data
        for (Product product : products) {
            String[] data = {
                    String.valueOf(product.getProductId()),
                    product.getProductName(),
                    String.valueOf(product.getQuntitySlot()),
                    String.valueOf(product.getPrice())
            };
            writer.writeNext(data);
        }

        try {
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputStreamSource attachment = new ByteArrayResource(outputStream.toByteArray());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Stock Report");
        helper.setText("Please find attached the stock report.");
        helper.addAttachment("stock_report.csv", attachment);

        javaMailSender.send(message);
    }
}
