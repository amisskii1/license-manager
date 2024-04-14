package com.misskii.licensemanager.controllers;

import com.misskii.licensemanager.dto.LicenseDTO;
import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.services.LicenseService;
import com.misskii.licensemanager.utils.LicenseErrorResponse;
import com.misskii.licensemanager.utils.LicenseKeyValidator;
import com.misskii.licensemanager.utils.LicenseNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LicenseDTO> createTrialLicense(@RequestBody LicenseDTO licenseDTO){
            licenseDTO.setLicenseValue(licenseService.createTrialLicense(convertToLicense(licenseDTO)));
        return new ResponseEntity<>(licenseDTO, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<LicenseDTO> validateLicense(@RequestBody LicenseDTO licenseDTO){
        String licenseStatus;
        if (licenseKeyValidator.verifySignature(licenseService.findOne(convertToLicense(licenseDTO).getUserEmail()))
            && licenseKeyValidator.doesLicenseExpired(licenseService.findOne(convertToLicense(licenseDTO).getUserEmail()))) {
            licenseStatus = "valid";
        } else licenseStatus = "invalid";
        licenseDTO.setLicenseStatus(licenseStatus);
        licenseDTO.setExpiredDate(licenseService.findOne(licenseDTO.getUserEmail()).getExpiredDate());
        licenseService.updateLicenseStatus(licenseDTO.getUserEmail(), licenseStatus);
        return new ResponseEntity<>(licenseDTO, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<LicenseErrorResponse> handelException(LicenseNotCreatedException e){
        LicenseErrorResponse licenseErrorResponse = new LicenseErrorResponse("Trial license can not be activated", System.currentTimeMillis() );
        return new ResponseEntity<>(licenseErrorResponse, HttpStatus.FORBIDDEN);
    }

    private License convertToLicense(LicenseDTO licenseDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(licenseDTO, License.class);
    }
}
