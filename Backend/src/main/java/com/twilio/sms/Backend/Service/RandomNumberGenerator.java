package com.twilio.sms.Backend.Service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberGenerator {

    public String getRandomNumberAsString(){
        Random r = new Random();
        return String.format("%06d", r.nextInt(999999));
    }

}
