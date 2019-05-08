package com.ppcrong.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.math.BigInteger;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static org.junit.Assert.assertEquals;

/**
 * AesCryptTest
 */
@RunWith(RobolectricTestRunner.class)
public class AesCryptTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encrypt_decrypt() throws Exception {

        // Avoid java.security.InvalidKeyException: Illegal key size or default parameters
        Security.setProperty("crypto.policy", "unlimited");

        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(128); /* 128-bit AES */
        SecretKey secret = gen.generateKey();
        byte[] binary = secret.getEncoded();
        String keyString = String.format("%032X", new BigInteger(+1, binary));

        String pwd = "1234567890";
        String encrypted = AesCrypt.encrypt(pwd, keyString);
        assertEquals(pwd, AesCrypt.decrypt(encrypted, keyString));
    }
}