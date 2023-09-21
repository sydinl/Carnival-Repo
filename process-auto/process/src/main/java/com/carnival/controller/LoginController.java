package com.carnival.controller;

import com.carnival.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carnival")
public class LoginController {

    @GetMapping("/getInfo")
    public AjaxResult getUserInfo(){
        return AjaxResult.success("test succeess!");
    }
}
