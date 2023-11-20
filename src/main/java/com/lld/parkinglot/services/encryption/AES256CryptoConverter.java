package com.lld.parkinglot.services.encryption;

import com.lld.parkinglot.enums.ResponseCode;
import com.lld.parkinglot.exceptions.ApplicationException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AES256CryptoConverter {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private static String SECRET_KEY = "00112233445566778899AABBCCDDEEFF00112233445566778899AABBCCDDEEFF";

    public static String encrypt(String plainText) throws ApplicationException {
        try{
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey());

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch(Exception exception){
            throw new ApplicationException(ResponseCode.PL_500102,exception,"Error Encryption of personal Data.");
        }
    }

    public static String decrypt(String encryptedText) throws ApplicationException {
        try{
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, generateKey());

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        }catch(Exception exception){
            throw new ApplicationException(ResponseCode.PL_500102,exception,"Error Decryption of personal Data.");
        }
    }

    private static SecretKey generateKey() throws ApplicationException {
        return new SecretKeySpec(hexStringToByteArray(SECRET_KEY), AES_ALGORITHM);
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

//    private static byte[] correctKeyLength(byte[] key) throws ApplicationException {
//        try{
//            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//            byte[] hashedKey = sha256.digest(key);
//
//            // Truncate or pad to the desired length
//            byte[] correctedKey = new byte[256];
//            System.arraycopy(hashedKey, 0, correctedKey, 0, Math.min(hashedKey.length, correctedKey.length));
//
//            return correctedKey;
//        }catch (NoSuchAlgorithmException exception){
//            throw new ApplicationException(ResponseCode.PL_500102,exception,"Error Encryption/Decryption of personal Data.");
//        }
//    }
}
