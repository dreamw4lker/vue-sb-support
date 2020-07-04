package com.betanet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class RedirectApiController {

    @GetMapping(value = "{?:(?:(?!api|static|\\.).)*}/**")
    public ModelAndView redirectApi() {
        return new ModelAndView("/index.html");
    }
}
