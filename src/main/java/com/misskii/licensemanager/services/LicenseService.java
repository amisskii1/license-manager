package com.misskii.licensemanager.services;

import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.repositories.LicenseRepository;
import com.misskii.licensemanager.utils.LicenseNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LicenseService {
    private final LicenseRepository licenseRepository;

    @Autowired
    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public List<License> findAll(){
        return licenseRepository.findAll();
    }

    public License findOne(String userEmail){
        return licenseRepository.findById(userEmail).orElse(null);
    }

    @Transactional
    public void save(License license){
        licenseRepository.save(license);
    }

    @Transactional
    public void update(String userEmail, License updatedLicense){
        updatedLicense.setUserEmail(userEmail);
        licenseRepository.save(updatedLicense);
    }

    @Transactional
    public void updateLicenseStatus(String email,String status){
        licenseRepository.findById(email).get().setLicenseStatus(status);
    }

    @Transactional
    public void delete(String userEmail){
        licenseRepository.deleteById(userEmail);
    }

    public boolean doesUserExist(String userEmail){
        return findOne(userEmail) != null;
    }

    @Transactional
    public String createTrialLicense(License license) {
        if (!doesUserExist(license.getUserEmail())){
            enrichTrialLicense(license);
            save(license);
        }else {
            throw new LicenseNotCreatedException();
        }
        return license.getLicenseValue();
    }

    public void enrichTrialLicense(License license){
        license.setLicenseStatus("unknown");
        license.setTrialStatus("true");
        license.setLicenseValue(license.generateLicense(license.getUserEmail()));
        license.setDateOfCreation(LocalDateTime.now());
        license.setCreatedBy("trials manager");
        license.setExpiredDate(LocalDateTime.now().plusDays(7));
    }

}
