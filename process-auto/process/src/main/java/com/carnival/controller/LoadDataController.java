package com.carnival.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loadData")
public class LoadDataController {

    @GetMapping("/exportFileTemplate")
    public void exportFileTemplate(){

    }



}
