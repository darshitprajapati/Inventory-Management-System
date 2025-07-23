package com.example.Inventory_Management_System.Notification;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;
public class Sms {
    // Find your Account Sid and Token at twilio.com/console
    public  void paymentSmsSucces(String name,String productName, double price) {
        twilio.account-sid=${TWILIO_ACCOUNT_SID}
        twilio.auth-token=${TWILIO_AUTH_TOKEN}

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("sms");

        Message message = Message.creator(
                         new com.twilio.type.PhoneNumber("+919726823959"),
                        new com.twilio.type.PhoneNumber("+19713768834"),
                        "Hello customer +"+name+" Your payment order of "+ productName+ "is done successfully " + price + "a total price")
                .create();
        System.out.println(message.getSid());
    }
}
