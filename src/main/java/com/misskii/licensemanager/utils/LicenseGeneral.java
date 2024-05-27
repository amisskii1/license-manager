package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class LicenseGeneral {
    KeyPair getECDSAKeyPair() throws NoSuchAlgorithmException {
        String filename = "ecdsa_keys";
        File keyFile = new File(filename);

        if (keyFile.length() == 0) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(keyPair);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return keyPair;
        } else {
            try {
                return loadECDSAKeyPairFromFile(filename);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    byte[] hashEmail(String userEmail, LocalDateTime dateOfCreation) throws NoSuchAlgorithmException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String emailWithTimestamp = userEmail + dateOfCreation.format(formatter);
        System.out.println(dateOfCreation.format(formatter));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(emailWithTimestamp.getBytes());
    }

    String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    byte[] decodeBase64(String license){
        return Base64.getDecoder().decode(license);
    }

    KeyPair loadECDSAKeyPairFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (KeyPair) ois.readObject();
        }
    }
}
