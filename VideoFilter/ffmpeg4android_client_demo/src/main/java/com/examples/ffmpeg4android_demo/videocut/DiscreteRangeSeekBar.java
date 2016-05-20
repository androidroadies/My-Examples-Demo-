package com.examples.ffmpeg4android_demo.videocut;

import android.content.Context;
import android.view.MotionEvent;


/**
 * Created by Sanjay on 11-08-2015.
 */
public class DiscreteRangeSeekBar<T extends Number> extends RangeSeekBar<T> {

    private final double normalizedMaxDiffLimit;
    private double normalizedStepSize;
    private double normalizedMinDiffLimit;

    @SuppressWarnings("unchecked")
    public DiscreteRangeSeekBar(T absoluteMinValue, T absoluteMaxValue, T stepSize, T diffMinLimit, T diffMaxLimit, Context context)
            throws IllegalArgumentException {
        super(absoluteMinValue, absoluteMaxValue, context);

        this.normalizedStepSize = valueToNormalized((T) numberType.toNumber(absoluteMinValue.doubleValue()
                + stepSize.doubleValue()));

        normalizedMinDiffLimit = valueToNormalized((T) numberType.toNumber(absoluteMinValue.doubleValue()
                + diffMinLimit.doubleValue()));

        normalizedMaxDiffLimit = valueToNormalized((T) numberType.toNumber(absoluteMinValue.doubleValue()
                + diffMaxLimit.doubleValue()));
//        normalizedMinDiffLimit = valueToNormalized((T) numberType.toNumber(absoluteMinValue.doubleValue()
//                + stepSize.doubleValue()));
    }

    @Override
    public void trackTouchEvent(MotionEvent event) {
        final int pointerIndex = event.findPointerIndex(mActivePointerId);
        final float x = event.getX(pointerIndex);

        if (Thumb.MIN.equals(pressedThumb)) {
            setNormalizedMinValue(findClosestNormalizedStep(screenToNormalized(x)));
        } else if (Thumb.MAX.equals(pressedThumb)) {
            setNormalizedMaxValue(findClosestNormalizedStep(screenToNormalized(x)));
        }
    }

    /**
     * Return normalized location of the nearest step or max (if closer)
     */
    private double findClosestNormalizedStep(double value) {
        int numbStepsBelow = (int) Math.floor(value / normalizedStepSize);

        double stepBelow = normalizedStepSize * numbStepsBelow;
        double stepAbove = Math.min(normalizedStepSize * (numbStepsBelow + 1), 1.0d);

        double distanceBelow = value - stepBelow;
        double distanceAbove = stepAbove - value;

        if (distanceBelow < distanceAbove)
            return stepBelow;
        return stepAbove;
    }


    @Override
    public void setNormalizedMinValue(double value) {

        Double newVal = Math.max(0d, Math.min(1d, Math.min(value, normalizedMaxValue)));
        if (normalizedMaxValue - newVal > normalizedMinDiffLimit && normalizedMaxValue - newVal < normalizedMaxDiffLimit) {
            normalizedMinValue = newVal;
            invalidate();
        }
    }

    @Override
    public void setNormalizedMaxValue(double value) {
        Double newVal = Math.max(0d, Math.min(1d, Math.max(value, normalizedMinValue)));
        if (newVal - normalizedMinValue >= normalizedMinDiffLimit && newVal - normalizedMinValue <= normalizedMaxDiffLimit) {
            normalizedMaxValue = newVal;
            invalidate();
        }
    }
}
