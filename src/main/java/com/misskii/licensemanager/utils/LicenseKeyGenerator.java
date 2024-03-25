package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

@Component
public class LicenseKeyGenerator {

    public static KeyPair generateECDSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        String filename = "ecdsa_keys";
        File keyFile = new File(filename);

        if (keyFile.length() == 0) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(keyPair);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return keyPair;
        } else {
            // If file is not empty, load the key pair from the file
            try {
                return loadECDSAKeyPairFromFile(filename);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
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
        byte[] signature = signData(hashedEmail);;
        return encodeBase64(signature);
    }

    public static KeyPair loadECDSAKeyPairFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (KeyPair) ois.readObject();
        }
    }
}
