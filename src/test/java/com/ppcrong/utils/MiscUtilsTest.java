package com.ppcrong.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * MiscUtilsTest
 */
@RunWith(RobolectricTestRunner.class)
public class MiscUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getIntToBitArrayString() throws Exception {

        int expected;
        String result;

        expected = 0b11111111;
        result = MiscUtils.getIntToBitArrayString(expected);
        assertEquals(true, "11111111".equalsIgnoreCase(result));

        expected = 0;
        result = MiscUtils.getIntToBitArrayString(expected);
        assertEquals(true, "00000000".equalsIgnoreCase(result));

        expected = 0b10101010;
        result = MiscUtils.getIntToBitArrayString(expected);
        assertEquals(true, "10101010".equalsIgnoreCase(result));

        expected = 0b01010101;
        result = MiscUtils.getIntToBitArrayString(expected);
        assertEquals(true, "01010101".equalsIgnoreCase(result));
    }

    @Test
    public void getByteToHexString() throws Exception {

        byte[] expected;
        String result;

        expected = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};

        result = MiscUtils.getByteToHexString(expected, ":", true);
        assertEquals(true, "01:02:03:04".equalsIgnoreCase(result));

        result = MiscUtils.getByteToHexString(expected, ":", false);
        assertEquals(true, "01020304".equalsIgnoreCase(result));

        expected = new byte[] {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
                (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        result = MiscUtils.getByteToHexString(expected, ":", true);
        assertEquals(true, "01:02:03:04:AB:CD:EF".equalsIgnoreCase(result));

        result = MiscUtils.getByteToHexString(expected, ":", false);
        assertEquals(true, "01020304ABCDEF".equalsIgnoreCase(result));
    }

    @Test
    public void getByteToIntString() throws Exception {

        byte[] expected;
        String result;

        // byte: 26:43:60:77:94:111:-86:-1
        // ubyte: 26:43:60:77:94:111:170:255
        expected = new byte[] {(byte) 0x1A, (byte) 0x2B, (byte) 0x3C, (byte) 0x4D,
                (byte) 0x5E, (byte) 0x6F, (byte) 0xAA, (byte) 0xFF};

        result = MiscUtils.getByteToIntString(expected, ":", true);
        assertEquals(true, "26:43:60:77:94:111:170:255".equalsIgnoreCase(result));

        result = MiscUtils.getByteToIntString(expected, ":", false);
        assertEquals(true, "2643607794111170255".equalsIgnoreCase(result));
    }

    @Test
    public void getIntToByte() throws Exception {

        int expected;
        byte result;

        expected = 0x1234;
        result = MiscUtils.getIntToByte(expected);
        assertEquals((byte) 0x34, result);

        expected = 0x12;
        result = MiscUtils.getIntToByte(expected);
        assertEquals((byte) 0x12, result);
    }

    @Test
    public void getBytesToInt16() throws Exception {

        byte[] expected;
        int result;

        expected = new byte[] {(byte) 0x78, (byte) 0x56};
        result = MiscUtils.getBytesToInt(expected[0], expected[1]);
        assertEquals(0x5678, result); // 0x5678 = 305419896

        expected = new byte[] {(byte) 0xEF, (byte) 0xCD};
        result = MiscUtils.getBytesToInt(expected[0], expected[1]);
        assertEquals(0xCDEF, result); // 0xCDEF = 296472047
    }

    @Test
    public void getBytesToInt32() throws Exception {

        byte[] expected;
        int result;

        expected = new byte[] {(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        result = MiscUtils.getBytesToInt(expected[0], expected[1], expected[2], expected[3]);
        assertEquals(0x12345678, result); // 0x12345678 = 305419896

        expected = new byte[] {(byte) 0xEF, (byte) 0xCD, (byte) 0xAB, (byte) 0x11};
        result = MiscUtils.getBytesToInt(expected[0], expected[1], expected[2], expected[3]);
        assertEquals(0x11ABCDEF, result); // 0x11ABCDEF = 296472047
    }

    @Test
    public void getSignedByteToInt() throws Exception {

        byte expected;
        int result;

        expected = (byte) 0xAA; // byte: -86, ubyte: 170
        result = MiscUtils.getSignedByteToInt(expected);
        assertEquals(0xAA, result);

        expected = (byte) 0x10;
        result = MiscUtils.getSignedByteToInt(expected);
        assertEquals(0x10, result);
    }

    @Test
    public void getPinToBytes() throws Exception {

        byte[] pinBytes;
        String pinString;

        pinString = "";
        pinBytes = MiscUtils.getPinToBytes(pinString);
        assertNull(pinBytes);

        pinString = "1234";
        pinBytes = MiscUtils.getPinToBytes(pinString);
        assertEquals(pinString, new String(pinBytes, "UTF-8"));
    }

    @Test
    public void getIntTo4Bytes() throws Exception {

        int expected;
        byte[] result;

        expected = 8000;

        result = MiscUtils.getIntTo4Bytes(expected, false);
        assertArrayEquals(new byte[] {(byte) 0x40, (byte) 0x1F, (byte) 0x00, (byte) 0x00}, result);

        result = MiscUtils.getIntTo4Bytes(expected, true);
        assertArrayEquals(new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x1F, (byte) 0x40}, result);

        expected = 0x11ABCDEF;

        result = MiscUtils.getIntTo4Bytes(expected, false);
        assertArrayEquals(new byte[] {(byte) 0xEF, (byte) 0xCD, (byte) 0xAB, (byte) 0x11}, result);

        result = MiscUtils.getIntTo4Bytes(expected, true);
        assertArrayEquals(new byte[] {(byte) 0x11, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF}, result);
    }

    @Test
    public void getAge() throws Exception {

        int age = MiscUtils.getAge(1990, 1, 1);
        assertEquals(true, age > 10);
    }

    @Test
    public void isToday() throws Exception {

        long today = Calendar.getInstance().getTimeInMillis();
        assertEquals(true, MiscUtils.isToday(today));

        long yesterday = today - 24 * 60 * 60 * 1000;
        assertEquals(false, MiscUtils.isToday(yesterday));
    }

    @Test
    public void getFormattedTime() throws Exception {

        Calendar time = Calendar.getInstance();

        // Month is Jan(0)~Dec(11)
        time.set(2000, 2, 6, 10, 20, 30);
        assertEquals("2000.03.06_10:20:30",
                MiscUtils.getFormattedTime(time.getTimeInMillis()));

        assertEquals("-1",
                MiscUtils.getFormattedTime(-100));
    }

    @Test
    public void isValidEmail() throws Exception {

        assertEquals(true, MiscUtils.isValidEmail("test@test.com"));
        assertEquals(true, MiscUtils.isValidEmail("test@test.edu.tw"));
        assertEquals(false, MiscUtils.isValidEmail("test@@@test.com"));
        assertEquals(false, MiscUtils.isValidEmail("test!@#$%^&*()test.com"));
        assertEquals(false, MiscUtils.isValidEmail("test"));
        assertEquals(false, MiscUtils.isValidEmail("test@test"));
    }

    @Test
    public void getDemoDeviceName() throws Exception {

        assertEquals(9, MiscUtils.getDemoDeviceName("VNS").length());
        assertEquals(10, MiscUtils.getDemoDeviceName("TEST").length());
    }

    @Test
    public void getDemoMac() throws Exception {

        assertEquals(17, MiscUtils.getDemoMac().length());
    }

}