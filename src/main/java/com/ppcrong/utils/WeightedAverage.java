package com.ppcrong.utils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WeightedAverage
 */
public class WeightedAverage {

    private float lastNumber = 0f;
    private static final float W1 = 0.8f;
    private static final float W2 = 0.2f;
    private boolean isFirstNumber = true;

    // region [Last Number has dependency with all history numbers]
    public void add(float number) {

        if (isFirstNumber) {

            lastNumber = number;
            isFirstNumber = false;
        } else {

            lastNumber = lastNumber * W1 + number * W2;
        }
    }

    public float getAverage() {
        return lastNumber;
    }
    // endregion [Last Number has dependency with all history numbers]

    // region [Last Number has dependency with previous limited numbers]
    private float[] weights = new float[]{0.3f, 0.7f};
    private LimitQueue<Float> lqueue = new LimitQueue<>(weights.length);
    private float currentNumber;

    public void offer(float number) {

        currentNumber = number;
        lqueue.offer(number);
    }

    public float getWeightedAverage() {

        if (lqueue.size() < weights.length) {

            return currentNumber;
        }

        float avg;
        CopyOnWriteArrayList<Float> list = new CopyOnWriteArrayList<>(lqueue);
        float sum = 0f;

        for (int i = 0; i < list.size(); i++) {

            sum += list.get(i) * weights[i];
        }

        avg = sum / list.size();

        return avg;
    }
    // endregion [Last Number has dependency with previous limited numbers]
}