package com.mananluvtocode.Recipie.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    // for writing the multiple requests for showing the same page.
    @RequestMapping({"", "/", "index"})
    public String getIndexPage() {
        return "index";
    }
}
