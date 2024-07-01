package com.mananluvtocode.Recipie.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {
    // for writing the multiple requests for showing the same page.
    @RequestMapping({"", "/", "index"})
    public String getIndexPage() {
        log.debug("Getting the index page for this controller and then showing in the frontend.");
        return "index";
    }
}
