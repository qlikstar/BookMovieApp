package com.twilio.sms.Backend.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SMSService {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACae222ca26ef02ae602646fb5e36746f6";
    public static final String AUTH_TOKEN = "82ef7e50a7797bd7db64bffa1058264b";
    public static final PhoneNumber SENDER_PHONE_NUMBER = new PhoneNumber("+14159158459");

    @Autowired
    RandomNumberGenerator randomNumberGenerator;

    private static final Map<PhoneNumber, String> OTPHashMap = new HashMap<>();


    public Boolean sendMovieBookedSMS(String phone, String name, String movie) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        PhoneNumber receiverPhoneNumber = new PhoneNumber(phone);

        try {
            Message message = Message
                    .creator(receiverPhoneNumber ,SENDER_PHONE_NUMBER,
                            "Ahoy " + name +"!, Congratulations, you have booked your movie tickets for :" + movie)
                    .create();
            System.out.println(message.getSid());
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean sendOTPTokenToPhone(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String randomNumberAsString = randomNumberGenerator.getRandomNumberAsString();
        OTPHashMap.put(new PhoneNumber(phoneNumber), randomNumberAsString);
        PhoneNumber receiverPhoneNumber = new PhoneNumber(phoneNumber);
        System.out.println("OTP Code : " + randomNumberAsString);

        try {
            Message message = Message
                    .creator(receiverPhoneNumber ,SENDER_PHONE_NUMBER,
                            "Ahoy! Here is your OTP Token for Twilio theater: " + randomNumberAsString)
                    .create();
            System.out.println(message.getSid());
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean verifyOTPToken(String phoneNumber, String Otp) {
        System.out.println(OTPHashMap.get(new PhoneNumber(phoneNumber)));
        System.out.println(Otp);
        if (Otp.equals(OTPHashMap.get(new PhoneNumber(phoneNumber))))
            return true;
        return false;
    }

    public void removeUser(String phoneNumber) {
        OTPHashMap.remove(new PhoneNumber(phoneNumber));
    }


}
