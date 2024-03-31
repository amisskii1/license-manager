package com.misskii.licensemanager.services;

import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.repositories.LicenseRepository;
import com.misskii.licensemanager.utils.LicenseNotCreatedException;
import com.misskii.licensemanager.utils.LicenseNotFoundException;
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
        return licenseRepository.findById(userEmail).orElseThrow(LicenseNotFoundException::new);
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
    public void delete(String userEmail){
        licenseRepository.deleteById(userEmail);
    }

    public boolean doesUserExist(String userEmail){
        return findOne(userEmail) != null;
    }

    @Transactional
    public void createTrialLicense(License license) {
        if (!doesUserExist(license.getUserEmail())){
            enrichTrialLicense(license);
            save(license);
        }else {
            throw new LicenseNotCreatedException();
        }
    }

    public void enrichTrialLicense(License license){
        license.setLicenseStatus("Active");
        license.setTrialStatus("Active");
        license.setLicenseValue(license.generateLicense(license.getUserEmail()));
        license.setDateOfCreation(LocalDateTime.now());
        license.setCreatedBy("Trial");
        license.setExpiredDate(LocalDateTime.now().plusDays(7));
    }

}
