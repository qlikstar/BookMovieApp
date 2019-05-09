package com.twilio.sms.Backend.Controller;

import com.twilio.sms.Backend.Service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class MainController {

    @Autowired
    SMSService smsService;

    public final static String AREA_CODE_US = "+1";

    @RequestMapping("/hello")
    public String getGreeting(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello " + name;
    }

    @RequestMapping("/sms/book")
    @ResponseBody
    public String sendMovieBookedSMS(@RequestParam(value="phone") String phone,
                                     @RequestParam(value="name") String name,
                                     @RequestParam(value="movie") String movie) {
        return "SMS sent : " + smsService.sendMovieBookedSMS(AREA_CODE_US + phone, name, movie);
    }

    @RequestMapping("/sms/otp")
    public Boolean sendOTPTokenToDevice(@RequestParam(value="phone") String phone) {
        return smsService.sendOTPTokenToPhone(AREA_CODE_US + phone);
    }

    @RequestMapping("/verify")
    public Boolean verifyOTPToken(@RequestParam(value="phone") String phone,
                                  @RequestParam (value="otp") String otp) {
        return smsService.verifyOTPToken(AREA_CODE_US + phone, otp);
    }

    @RequestMapping("/logout")
    public Boolean logoutUser(@RequestParam(value="phone") String phone) {
        smsService.removeUser(AREA_CODE_US + phone);
        return true;
    }


}
