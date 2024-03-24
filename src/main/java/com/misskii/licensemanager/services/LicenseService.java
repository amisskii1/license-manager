package com.misskii.licensemanager.services;

import com.misskii.licensemanager.models.License;
import com.misskii.licensemanager.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void delete(String userEmail){
        licenseRepository.deleteById(userEmail);
    }
}
