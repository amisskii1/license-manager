package com.misskii.licensemanager.utils;


import org.springframework.stereotype.Component;
import java.security.*;

@Component
public class LicenseKeyValidator extends LicenseGeneral{
    public boolean verifySignature(String email, byte[] signature) throws Exception {
        PublicKey publicKey = getECDSAKeyPair(256).getPublic();
        byte[] data = hashEmail(email);
        Signature ecdsaSignature = Signature.getInstance("SHA256withECDSA");
        ecdsaSignature.initVerify(publicKey);
        ecdsaSignature.update(data);
        return ecdsaSignature.verify(signature);
    }
}
