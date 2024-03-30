package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class LicenseKeyGenerator extends LicenseGeneral {
    private byte[] signData(byte[] data) throws Exception {
        PrivateKey privateKey = getECDSAKeyPair(256).getPrivate();
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public String generateLicenseKey(String userEmail) throws Exception {
        byte[] hashedEmail = hashEmail(userEmail);
        byte[] signature = signData(hashedEmail);;
        return encodeBase64(signature);
    }
}
