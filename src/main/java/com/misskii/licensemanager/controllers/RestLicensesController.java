package com.misskii.licensemanager.controllers;

import com.misskii.licensemanager.dto.LicenseDTO;
import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.services.LicenseService;
import com.misskii.licensemanager.utils.LicenseKeyValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestLicensesController {
    private final LicenseService licenseService;
    private final LicenseKeyValidator licenseKeyValidator;


    @Autowired
    public RestLicensesController(LicenseService licenseService, LicenseKeyValidator licenseKeyValidator) {
        this.licenseService = licenseService;
        this.licenseKeyValidator = licenseKeyValidator;
    }

    @PostMapping("/trial")
    public ResponseEntity<HttpStatus> createTrialLicense(@RequestBody LicenseDTO licenseDTO){
        // TODO display validation error
        licenseService.createTrialLicense(convertToLicense(licenseDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/validate")
    public boolean validateLicense(@RequestBody LicenseDTO licenseDTO){
        return licenseKeyValidator.verifySignature(licenseDTO.getUserEmail(), licenseDTO.getLicenseValue());
    }

    private License convertToLicense(LicenseDTO licenseDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(licenseDTO, License.class);
    }
}
