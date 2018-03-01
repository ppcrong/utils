package com.ppcrong.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;

import com.socks.library.KLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.POWER_SERVICE;

/**
 * The utility for misc
 */
public class MiscUtils {

    // region [Intent]

    /**
     * Start an intent with try-catch to avoid activity not found
     *
     * @param context
     * @param intent
     */
    public static void startSafeIntent(Context context, Intent intent) {

        try {

            context.startActivity(intent);
        } catch (Exception e) {

            KLog.i("[startSafeIntent1] start intent error...");
            KLog.i(Log.getStackTraceString(e));
        }
    }

    /**
     * Start an intent with try-catch to avoid activity not found
     *
     * @param context
     * @param action
     */
    public static void startSafeIntent(Context context, String action) {

        Intent intent = new Intent(action);
        startSafeIntent(context, intent);
    }
    // endregion [Intent]

    // region [Conversion]

    /**
     * Convert integer to bit array string
     *
     * @param num integer number
     * @return bit array string
     */
    public static String getIntToBitArrayString(int num) {

        return String.format("%8s", Integer.toBinaryString(num & 0xFF)).replace(' ', '0');
    }

    /**
     * Convert byte array to hex string
     * <br/>
     * ex: 0x1C, 0x2B, 0x3D, 0x4A => 1C:2B:3D:4A
     *
     * @param bytes           The byte array to convert
     * @param separator       The separator between 2 bytes
     * @param appendSeparator true to append separator, false not append
     * @return The string value
     */
    public static String getByteToHexString(byte[] bytes, String separator, boolean appendSeparator) {

        KLog.i("length = " + bytes.length);

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {

            if (sb.length() > 0 && appendSeparator) sb.append(separator);
            sb.append(String.format("%02X", b));
        }

        String str = sb.toString();
        KLog.i(str);
        return str;
    }

    /**
     * Convert byte array to integer string
     * <br/>
     * ex: 1C2B3D4A => 28:43:61:74
     *
     * @param bytes           The byte array to convert
     * @param separator       The separator between 2 bytes
     * @param appendSeparator true to append separator, false not append
     * @return The string value
     */
    public static String getByteToIntString(byte[] bytes, String separator, boolean appendSeparator) {

        KLog.i("length = " + bytes.length);

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {

            if (sb.length() > 0 && appendSeparator) sb.append(separator);
            sb.append(b);
        }

        String str = sb.toString();
        KLog.i(str);
        return str;
    }

    /**
     * Convert byte to unsigned byte
     * <br/>
     * EX: If value is signed byte, for example, 0x8E, int value will get  -114, but we want the value is unsigned byte value 142
     * <br/>
     * byte b = (byte) 0x8E;
     * <br/>
     * int i = b;
     * <br/>
     * The i value will be -114.
     *
     * @param b The byte
     * @return Unsigned short value
     */
    public static short getByteToUByte(byte b) {
        return (short) (b & 0xFF);
    }

    /**
     * Convert integer to byte
     *
     * @param i The integer
     * @return Byte
     */
    public static byte getIntToByte(int i) {
        return ((byte) (i & 0xFF));
    }

    /**
     * Convert signed bytes to a 32-bit unsigned int.
     */
    public static int getUnsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {

        return (getUnsignedByteToInt(b0) + (getUnsignedByteToInt(b1) << 8))
                + (getUnsignedByteToInt(b2) << 16) + (getUnsignedByteToInt(b3) << 24);
    }

    /**
     * Convert a signed byte to an unsigned int.
     */
    public static int getUnsignedByteToInt(byte b) {
        return b & 0xFF;
    }

    /**
     * Convert 2 bytes to signed short.
     *
     * @param b0
     * @param b1
     * @return
     */
    public static short getBytesToShort(byte b0, byte b1) {

        // bytes to short conversion
        return (short) ((short) (b0) | (short) (b1 << 8)); // Little-Endian
    }

    /**
     * Check that a pin is valid and convert to byte array.
     * <p>
     * Bluetooth pin's are 1 to 16 bytes of UTF-8 characters.
     *
     * @param pin pin as java String
     * @return the pin code as a UTF-8 byte array, or null if it is an invalid
     * Bluetooth pin.
     */
    public static byte[] getPinToBytes(String pin) {

        if (pin == null) {

            return null;
        }

        byte[] pinBytes;
        try {

            pinBytes = pin.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {

            KLog.i(Log.getStackTraceString(uee));
//            Log.e(TAG, "UTF-8 not supported?!?");  // this should not happen
            return null;
        }

        if (pinBytes.length <= 0 || pinBytes.length > 16) {

            return null;
        }

        return pinBytes;
    }

    /**
     * Convert int to 4 bytes
     *
     * @param n         The integer
     * @param bigEndian is big endian
     * @return 4 bytes
     */
    public static byte[] getIntTo4Bytes(int n, boolean bigEndian) {

        ByteBuffer b = ByteBuffer.allocate(4);
        if (bigEndian)
            b.order(ByteOrder.BIG_ENDIAN);
        else
            b.order(ByteOrder.LITTLE_ENDIAN);
        b.putInt(n);

        byte[] result = b.array();
        KLog.i(getByteToHexString(result, ":", true));

        return result;
    }
    // endregion [Conversion]

    // region [Date]

    /**
     * Method to extract the user's age from the entered Date of Birth.
     *
     * @param year  Birth year
     * @param month Birth month
     * @param day   Birth day
     * @return The user's age in years based on the supplied DoB.
     */
    public static int getAge(int year, int month, int day) {

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    /**
     * Check if today
     *
     * @param timestamp The time to check
     * @return True is today, False is not
     */
    public static boolean isToday(long timestamp) {

        Calendar timeToCheck = Calendar.getInstance();
        timeToCheck.setTimeInMillis(timestamp);
        Calendar now = Calendar.getInstance();

        if ((now.get(Calendar.YEAR) == timeToCheck.get(Calendar.YEAR)) &&
                (now.get(Calendar.MONTH) == timeToCheck.get(Calendar.MONTH)) &&
                (now.get(Calendar.DATE) == timeToCheck.get(Calendar.DATE))) {
            return true;
        }

        return false;
    }

    public static String getFormattedTime(long time) {

        if (time >= 0) {
            String formattedTime;
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss", Locale.US);
            formattedTime = timeFormat.format(new Date(time));
            return formattedTime;
        } else {
            return "-1";
        }
    }
    // endregion [Date]

    // region [WakeLock]
    private static PowerManager sPowerManager;
    private static PowerManager.WakeLock sWakeLock;

    public static void initWakeLock(Context ctx) {

        if (sWakeLock == null) {
            sPowerManager = (PowerManager) ctx.getSystemService(POWER_SERVICE);
            sWakeLock = sPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MiscUtilsWakeLock");
        }
    }

    public static void acquireWakeLock() {

        if (sWakeLock != null) {
            KLog.i("acquire WakeLock");
            try {
                sWakeLock.acquire();
            } catch (Exception e) {
                KLog.i(Log.getStackTraceString(e));
            }
        } else {
            KLog.i("sWakeLock is null");
        }
    }

    public static void releaseWakeLock() {

        if (sWakeLock != null) {
            KLog.i("release WakeLock");
            try {
                if (sWakeLock.isHeld()) {
                    sWakeLock.release();
                } else {
                    KLog.i("WakeLock is released already, ignore...");
                }
            } catch (Exception e) {
                KLog.i(Log.getStackTraceString(e));
            }
        } else {
            KLog.i("sWakeLock is null");
        }
    }
    // endregion [WakeLock]

    // region [Connection]
    public static boolean isInternetConnected(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    // endregion [Connection]

    // region [Asset]
    public static String loadJsonAsset(Context ctx, String fileName) {

        String json = "";
        InputStream is = null;

        try {

            is = ctx.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {

            KLog.i(Log.getStackTraceString(e));
        } finally {

            try {

                if (is != null) is.close();
            } catch (IOException e) {

                KLog.i(Log.getStackTraceString(e));
            }
        }

        return json;
    }
    // endregion [Asset]
}
