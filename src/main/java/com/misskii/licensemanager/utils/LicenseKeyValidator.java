package com.misskii.licensemanager.utils;

import com.misskii.licensemanager.dto.LicenseDTO;
import com.misskii.licensemanager.models.License;
import org.springframework.stereotype.Component;
import java.security.*;
import java.time.LocalDateTime;

@Component
public class LicenseKeyValidator extends LicenseGeneral{
    public boolean verifySignature(License license) {
        PublicKey publicKey = null;
        Signature ecdsaSignature;
        try {
            publicKey = getECDSAKeyPair().getPublic();
            byte[] data = hashEmail(license.getUserEmail(), license.getDateOfCreation());
            ecdsaSignature = Signature.getInstance("SHA256withECDSA");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data);
            return ecdsaSignature.verify(decodeBase64(license.getLicenseValue()));
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesLicenseExpired(License license){
        return LocalDateTime.now().isBefore(license.getExpiredDate());
    }
}
