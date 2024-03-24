package com.misskii.licensemanager.controllers;

import com.misskii.licensemanager.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/licenses")
public class LicensesController {
    private final LicenseService licenseService;

    @Autowired
    public LicensesController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping()
    public String index(Model model ){
        model.addAttribute("licenses", licenseService.findAll());
        return "index";
    }

    @GetMapping("/{user_email}")
    public String show(@PathVariable("user_email") String userEmail, Model model){
        model.addAttribute("license", licenseService.findOne(userEmail));
        return "show";
    }

}
