package com.misskii.licensemanager.utils;

import org.springframework.stereotype.Component;
import java.security.*;

@Component
public class LicenseKeyValidator extends LicenseGeneral{
    public boolean verifySignature(String email, String license) {
        PublicKey publicKey = null;
        Signature ecdsaSignature;
        try {
            publicKey = getECDSAKeyPair().getPublic();
            byte[] data = hashEmail(email);
            ecdsaSignature = Signature.getInstance("SHA256withECDSA");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data);
            return ecdsaSignature.verify(decodeBase64(license));
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
