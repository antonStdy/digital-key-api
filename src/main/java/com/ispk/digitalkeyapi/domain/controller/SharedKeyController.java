package com.ispk.digitalkeyapi.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sharedKeys")
public class SharedKeyController {

    // TODO finish html
    @GetMapping("/share/{sharingId}")
    public ModelAndView share(@PathVariable String sharingId) {
        var modelAndView = new ModelAndView();
        modelAndView.setViewName("sharePage");
        return modelAndView;
    }
}
