package com.loopus.loopus_be.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FallbackController {

    @RequestMapping(value = "/{path:[^\\.]*}", produces = "text/html")
    public String forwardToIndex(HttpServletRequest request) {
        String path = request.getRequestURI();

        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/webjars")) {
            return null;
        }

        return "forward:/";
    }
}
