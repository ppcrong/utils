package com.ppcrong.utils;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * https://stackoverflow.com/questions/41223937/how-can-i-encrypte-my-password-android-studio
 */

public class AesCrypt {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String value, String keyString) throws Exception {

        Key key = generateKey(keyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.NO_WRAP);
        return encryptedValue64;
    }

    public static String decrypt(String value, String keyString) throws Exception {

        Key key = generateKey(keyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue, "utf-8");
        return decryptedValue;
    }

    private static Key generateKey(String keyString) throws Exception {

        Key key = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
        return key;
    }
}
