package com.ppcrong.utils;

import com.socks.library.KLog;

/**
 * The utility to calculate data rate
 */
public class DataRateUtil {
    private long mPrevTime = 0l;
    private int mDataCount = 0;

    /**
     * Calculate data rate and print log
     * @param logTag The tag to show log
     * @param bytes The bytes data
     * @param logInterval The log interval to calculate
     */
    public void calculateDataRate(String logTag, byte[] bytes, long logInterval) {

        int num = bytes.length;
        mDataCount += num;

        if (mPrevTime == 0l) {

            KLog.i("[" + logTag + "]" + "=====First counting=====");
            mPrevTime = System.currentTimeMillis();
        } else {

            // Calculate KB/s
            long deltaTime = System.currentTimeMillis() - mPrevTime;
            if (deltaTime > logInterval) {
                float rate = (float) mDataCount / deltaTime;
                KLog.i("[" + logTag + "]" + "==========DataRate: " + rate + " KB/s==========");
                mDataCount = 0;
                mPrevTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Reset previous time for next data rate calculation
     */
    public void resetDataRate() {

        mPrevTime = 0l;
    }
}
