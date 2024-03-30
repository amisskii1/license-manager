package com.misskii.licensemanager.controllers;

import com.misskii.licensemanager.dao.LicenseDAO;
import com.misskii.licensemanager.dto.LicenseDTO;
import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.services.LicenseService;
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

    @Autowired
    public RestLicensesController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping("/trial")
    public ResponseEntity<HttpStatus> createTrialLicense(@RequestBody LicenseDTO licenseDTO){
        // TODO display validation error
        licenseService.createTrialLicense(convertToLicense(licenseDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private License convertToLicense(LicenseDTO licenseDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(licenseDTO, License.class);
    }
}
