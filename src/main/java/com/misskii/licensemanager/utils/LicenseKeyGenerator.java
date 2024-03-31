package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class LicenseKeyGenerator extends LicenseGeneral {
    private byte[] signData(byte[] data) throws Exception {
        PrivateKey privateKey = getECDSAKeyPair().getPrivate();
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public String generateLicenseKey(String userEmail){
        byte[] hashedEmail;
        try {
            hashedEmail = hashEmail(userEmail);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] signature;
        try {
            signature = signData(hashedEmail);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encodeBase64(signature);
    }
}
