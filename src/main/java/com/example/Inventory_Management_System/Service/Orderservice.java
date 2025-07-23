package com.example.Inventory_Management_System.Service;

import com.example.Inventory_Management_System.Dto.SaveDto;
import com.example.Inventory_Management_System.Entity.Customer;
import com.example.Inventory_Management_System.Entity.Order;
import com.example.Inventory_Management_System.Entity.Product;
import com.example.Inventory_Management_System.Notification.Email;
import com.example.Inventory_Management_System.Notification.Sms;
import com.example.Inventory_Management_System.Notification.Whatsapp;
import com.example.Inventory_Management_System.Repository.CustomerRepository;
import com.example.Inventory_Management_System.Repository.OrderRepository;
import com.example.Inventory_Management_System.Repository.ProductRepository;
import com.razorpay.Invoice;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class Orderservice {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    Whatsapp whatsapp = new Whatsapp();
    Sms sms = new Sms();

    Email email = new Email();
    String to = "meetprajapati2304@gmail.com";
    String from = "darshittarsariya01@gmail.com";

    String text = "your order payment is  successfully";

    String subject = "online order payment";


    private Customer getCustomerDetail(SaveDto request) {
        Optional<Customer> c1 = customerRepository.findById(request.getCustId());
        Customer c2 = new Customer();
        if (c1.isPresent()) {
            c2 = c1.get();
        }
        return c2;
    }

    public Product getProductDetl(SaveDto request) {
        Optional<Product> p1 = productRepository.findById(request.getProdId());
        Product p2 = new Product();
        if (p1.isPresent()) {
            p2 = p1.get();
        }
        return p2;
    }

    public String setOrderDetails(SaveDto orderDetl) throws RazorpayException {
        Product p1 = getProductDetl(orderDetl);
        Customer c1 = getCustomerDetail(orderDetl);


        System.out.println(c1.getMoNo());
        System.out.println(c1.getId());
        System.out.println(c1.getCustomerName());

        if (p1.getQuntitySlot() < orderDetl.getQuntity()) {
            return "Order quantity is not available";
        }

        Order o1 = new Order();
        o1.setCustId(orderDetl.getCustId());

        System.out.println(o1.getCustId());
        o1.setProdId(orderDetl.getProdId());

        System.out.println(o1.getProdId());
        o1.setQuntity(orderDetl.getQuntity());

        System.out.println(o1.getQuntity());
        o1.setOrdDate(LocalDate.now());
        System.out.println(o1.getOrdDate());

        double productTotal = p1.getPrice() * orderDetl.getQuntity();
        double gstAmount = productTotal * p1.getGst() / 100;
        double totalAmount = productTotal + gstAmount;

        System.out.println("Total Amount in Service Method  : " + totalAmount);


        o1.setTotalAmount(totalAmount);

        System.out.println(o1.getTotalAmount());

        String invoicedID = razorpayPayment(c1, p1, o1);

        p1.setQuntitySlot(p1.getQuntitySlot() - orderDetl.getQuntity());

        productRepository.save(p1);

        orderRepository.save(o1);




        return "order is success";

    }
    public String razorpayPayment(Customer c1, Product p1, Order o1) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_l2HlwjANfJwviY", "tdmN5eOJ7QtHq1WvybVah58s");

        double productTotal = p1.getPrice() * o1.getQuntity();
        double gstAmount = productTotal * p1.getGst() / 100;
        double totalAmount = productTotal + gstAmount;

        // 💡 Razorpay expects amount in PAISE, so multiply by 100
        int finalAmount = (int) (totalAmount * 100);

        System.out.println("Total Amount Razorpay: " + totalAmount);
        System.out.println("Final Amount Razorpay: " + finalAmount);

        JSONObject invoiceRequest = new JSONObject();
        invoiceRequest.put("type", "invoice");
        invoiceRequest.put("description", "Invoice for your order");
        invoiceRequest.put("partial_payment", false);
        invoiceRequest.put("currency", "INR");
        invoiceRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());
        invoiceRequest.put("email_notify", 1);
        invoiceRequest.put("sms_notify", 1);
        invoiceRequest.put("expire_by", 2580479824L); // Optional

        // Customer info
        JSONObject customer = new JSONObject();
        customer.put("name", c1.getCustomerName());
        customer.put("contact", c1.getMoNo());
        invoiceRequest.put("customer", customer);

        //  Use o1.getQuntity() here (not p1.getQuntitySlot())
        List<JSONObject> lineItems = new ArrayList<>();
        JSONObject item = new JSONObject();
        item.put("name", p1.getProductName());
        item.put("description", "Product by " + c1.getCustomerName());
        item.put("amount", finalAmount); //  Amount in paise
        item.put("currency", "INR");
        item.put("quantity", o1.getQuntity()); //  Order quantity
        lineItems.add(item);

        invoiceRequest.put("line_items", lineItems);

        Invoice invoice = razorpay.invoices.create(invoiceRequest);

        System.out.println("Invoice Created: " + invoice.get("id"));
        System.out.println("Payment Link: " + invoice.get("short_url"));

        Invoice fetchedInvoice = razorpay.invoices.fetch(invoice.get("id"));
        String status = (String) fetchedInvoice.get("status");

        System.out.println("Invoice Status: " + status);

        if ("paid".equalsIgnoreCase(status)) {
//            whatsapp.paymentDone(c1.getCustomerName(), p1.getProductName(), totalAmount);
            email.sendMAil(to,from,subject,text);
//            sms.paymentSmsSucces(c1.getCustomerName(),p1.getProductName(),totalAmount);

            System.out.println("Payment Successful!");
        } else if ("issued".equalsIgnoreCase(status)) {
//            whatsapp.paymentDone(c1.getCustomerName(), p1.getProductName(), totalAmount);
            email.sendMAil(to,from,subject,text);
//            sms.paymentSmsSucces(c1.getCustomerName(),p1.getProductName(),totalAmount);
            System.out.println("Payment Pending.");
        } else if ("cancelled".equalsIgnoreCase(status)) {
//            whatsapp.paymentNotDone(c1.getCustomerName(),p1.getProductName(),totalAmount);
            email.sendMAil(to,from,subject,text);
//            sms.paymentSmsSucces(c1.getCustomerName(),p1.getProductName(),totalAmount);
            System.out.println("Payment Cancelled.");
        }

        return invoice.get("id");
    }
}