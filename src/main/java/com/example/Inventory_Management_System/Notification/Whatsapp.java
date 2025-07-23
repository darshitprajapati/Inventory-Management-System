package com.example.Inventory_Management_System.Notification;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.math.BigDecimal;

public class Whatsapp {
    @Value("$twilio.account.sid")
    private String ACCOUNT_SID;
    @Value("$twilio.auth.token")
    private String AUTH_TOKEN;
    public void paymentDone(String name,String productName, double price) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+919726823959"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        "Hello customer +"+name+" Your payment order of "+ productName+ "is done successfully " + price + "a total price")
                .create();

        System.out.println(message.getSid());
    }
    public void paymentNotDone(String name,String productName, double price) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+919726823959"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        "Hello customer "+name+" Your payment order of "+ productName+ " is not successfully " + price + " a total price ")
                .create();

        System.out.println(message.getSid());
    }
}