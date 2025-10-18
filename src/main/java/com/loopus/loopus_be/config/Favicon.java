package com.loopus.loopus_be.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Favicon {
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void favicon() { }
}
