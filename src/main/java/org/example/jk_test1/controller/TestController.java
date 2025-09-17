package org.example.jk_test1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String index() {
        String returnMessage = "Hello World!";
        returnMessage += "1";
        returnMessage += "2";
        returnMessage += "3";
        returnMessage += "4";
        returnMessage += "5";
        returnMessage += "6";
        returnMessage += "7";
        returnMessage += "8";


        return returnMessage;
    }
}
