package com.ppcrong.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Utility for timeout
 */
public class TimeoutUtil {

    // region [Interface]
    public interface onTimeoutListener {
        void timeout();
    }
    // endregion [Interface]

    // region [Constant]
    public static final long TIMEOUT_6S = 6_000l;
    public static final long TIMEOUT_30S = 30_000l;
    public static final long TIMEOUT_1M = 60_000l;
    // endregion [Constant]

    // region [Variable]
    private long mTimeout = TIMEOUT_6S;
    private onTimeoutListener mTimeoutListener;
    private Handler mSyncTimeoutHandler;
    private Runnable mSyncTimeoutRunnable = new Runnable() {
        @Override
        public void run() {

            if (mTimeoutListener != null) {

                mTimeoutListener.timeout();
            }
        }
    };
    // endregion [Variable]

    public TimeoutUtil(long timeout, onTimeoutListener listener, boolean forceMainLooper) {
        this.mTimeout = timeout;
        this.mTimeoutListener = listener;
        if (forceMainLooper) {

            this.mSyncTimeoutHandler = new Handler(Looper.getMainLooper());
        } else {

            this.mSyncTimeoutHandler = new Handler();
        }
    }

    public void setTimeoutListener(onTimeoutListener listener) {
        this.mTimeoutListener = listener;
    }

    public void setTimeout(long timeout) {
        this.mTimeout = timeout;
    }

    public void startWait() {
        mSyncTimeoutHandler.postDelayed(mSyncTimeoutRunnable, mTimeout);
    }

    public void stopWait() {
        mSyncTimeoutHandler.removeCallbacks(mSyncTimeoutRunnable);
    }

    public void restartWait() {
        stopWait();
        startWait();
    }

    // region [Builder]
    public static class Builder {
        private long timeout = TIMEOUT_6S;
        private onTimeoutListener listener;

        private boolean forceMainLooper;

        public Builder setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setListener(onTimeoutListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setForceMainLooper(boolean forceMainLooper) {
            this.forceMainLooper = forceMainLooper;
            return this;
        }

        public TimeoutUtil build() {
            return new TimeoutUtil(timeout, listener, forceMainLooper);
        }
    }
    // endregion [Builder]
}
