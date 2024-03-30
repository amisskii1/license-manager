package com.misskii.licensemanager.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Component
public class LicenseKeyValidator extends LicenseGeneral{
    public boolean verifySignature(String email, byte[] signature) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        PublicKey publicKey = getECDSAKeyPair(256).getPublic();
        byte[] data = hashEmail(email);
        Signature ecdsaSignature = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSignature.initVerify(publicKey);
        ecdsaSignature.update(data);
        return ecdsaSignature.verify(signature);
    }
}
