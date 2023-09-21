package com.carnival.controller;

import com.carnival.service.TransformDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transformData")
public class TransformDataController {

    @Autowired
    private TransformDataService transformDataService;

    @GetMapping("/transformDatatoChart")
    public void transformDatatoChart(){

    }



}
