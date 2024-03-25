package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;

@Component
public class LicenseKeyGenerator {

    public static KeyPair generateECDSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    private byte[] hashEmail(String userEmail) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(userEmail.getBytes());
    }

    private byte[] signData(byte[] data) throws Exception {
        PrivateKey privateKey = generateECDSAKeyPair(256).getPrivate();
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    private String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public String generateLicenseKey(String userEmail) throws Exception {
        byte[] hashedEmail = hashEmail(userEmail);
        byte[] signature = signData(hashedEmail);
        String licenseKey = encodeBase64(signature);
        return licenseKey;
    }
}
