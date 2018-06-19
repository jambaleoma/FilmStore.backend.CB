package com.javasampleapproach.couchbase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value="/Home")
    public String homepage(){
        return "index";
    }
}
