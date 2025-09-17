package org.example.jk_test1.controller;

import org.example.jk_test1.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("/")
    public String index(Model model) {
        String returnMessage = "Hello World!";
        returnMessage += "1";
        returnMessage += "2";
        returnMessage += "3";
        returnMessage += "4";
        returnMessage += "5";
        returnMessage += "6";
        returnMessage += "7";
        returnMessage += "8";

        model.addAttribute("message", returnMessage);


        User test = User.builder().id(1).name("test").build();
        model.addAttribute("user", test);

        return "index";
    }

    @ResponseBody
    @GetMapping("/test")
    public User test() {
        return User.builder().id(1).name("test").build();
    }
}
