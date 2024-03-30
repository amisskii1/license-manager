package com.misskii.licensemanager.controllers;

import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/licenses")
public class ManualLicensesController {
    private final LicenseService licenseService;

    @Autowired
    public ManualLicensesController(LicenseService licenseService) {
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

    @PostMapping()
    public String create(@ModelAttribute("license") License license){
        licenseService.save(license);
        return  "redirect:/licenses";
    }

    @GetMapping("/new/{user_email}")
    public String licenseCreationForm(@PathVariable("user_email") String userEmail, @ModelAttribute("license") License license){
        LocalDateTime localDateTime = LocalDateTime.now();
        license.setUserEmail(userEmail);
        license.setLicenseValue(license.getUserEmail());
        license.setCreatedBy("admin");
        license.setDateOfCreation(localDateTime);
        license.setTrialStatus("Cancelled");
        license.setLicenseStatus("Active");
        return "new";
    }

    @GetMapping("/new")
    public String showEmailForm(@ModelAttribute("license") License license) {
        return "email_form";
    }

    @PostMapping("/create")
    public String processEmailForm(@ModelAttribute("license") License license) {
        String userEmail = license.getUserEmail();
        return "redirect:/licenses/new/" + userEmail;
    }

    @GetMapping("/{user_email}/edit")
    public String licenseUpdateForm(@PathVariable("user_email") String userEmail, Model model){
        model.addAttribute("license", licenseService.findOne(userEmail));
        return "edit";
    }

    @PatchMapping("/{user_email}")
    public String licenseUpdate(@PathVariable("user_email") String userEmail, @ModelAttribute("license") License license){
        licenseService.update(userEmail, license);
        return "redirect:/licenses/" + license.getUserEmail();
    }

    @DeleteMapping("/{user_email}")
    public String licenseDeletion(@PathVariable("user_email") String userEmail){
        licenseService.delete(userEmail);
        return "redirect:/licenses";
    }

}
