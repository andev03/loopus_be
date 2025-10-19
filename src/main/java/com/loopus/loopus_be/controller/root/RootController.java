package com.loopus.loopus_be.controller.root;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String notFound() {
        return "404NotFound";
    }
}
