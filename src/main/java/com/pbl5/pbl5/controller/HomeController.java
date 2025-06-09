package com.pbl5.pbl5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Trả về file templates/index.html
    }

    // @GetMapping("/listening-test")
    // public String listeningTest() {
    //     return "listeningTest"; // Trả về file templates/listeningTest.html
    // }
}
