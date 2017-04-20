package io.github.trylovecatch.viewpagerdemo;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lipeng21 on 2017/4/17.
 */

public class ColorAnimationView extends View implements
        ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    public static final int DURATION = 3000;

    private ValueAnimator colorAnim = null;

    private GradientDrawable mDrawable;
    private Color mCurrentColor;
//    private int mCurrentColor = -1;

    public ColorAnimationView(Context context) {
        this(context, null, 0);
    }

    public ColorAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorAnimationView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentColor == null) {
            return;
        }
        mDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {mCurrentColor.startColor, mCurrentColor.endColor});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(mDrawable);
        } else {
            setBackgroundDrawable(mDrawable);
        }

//        if(mCurrentColor == -1){
//            return;
//        }
//
//        setBackgroundColor(mCurrentColor);
    }

    public void initData(Color[] pColors) {
        if (pColors == null) {
            throw new RuntimeException("colors is null");
        }
        createAnimation(pColors);

    }

    public void initData(Integer[] pColors) {
        if (pColors == null) {
            throw new RuntimeException("colors is null");
        }
        createAnimation(pColors);

    }

    public void seek(long seekTime) {
        colorAnim.setCurrentPlayTime(seekTime);
    }

    private void createAnimation(Object[] pColors) {
        if (colorAnim == null) {
//            colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), pColors);
            colorAnim = ValueAnimator.ofObject(new MyEvaluator(), pColors);
            colorAnim.setDuration(DURATION);
            colorAnim.addUpdateListener(this);
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mCurrentColor = (Color) animation.getAnimatedValue();
//        mCurrentColor = (int) animation.getAnimatedValue();
        invalidate();
    }

    private class MyEvaluator implements TypeEvaluator<Color> {

        public Color evaluate(float fraction, Color startValue, Color endValue) {
            Color tColor = new Color();
            tColor.startColor = createColor(fraction, startValue.startColor, endValue.startColor);
            tColor.endColor = createColor(fraction, startValue.startColor, endValue.startColor);
            return tColor;
        }

        private int createColor(float fraction, int pStartColor, int pEndColor) {
            int startA = (pStartColor >> 24) & 0xff;
            int startR = (pStartColor >> 16) & 0xff;
            int startG = (pStartColor >> 8) & 0xff;
            int startB = pStartColor & 0xff;

            int endA = (pEndColor >> 24) & 0xff;
            int endR = (pEndColor >> 16) & 0xff;
            int endG = (pEndColor >> 8) & 0xff;
            int endB = pEndColor & 0xff;

            return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                    | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                    | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                    | (int) ((startB + (int) (fraction * (endB - startB))));
        }
    }

    public static class Color {
        public int startColor;
        public int endColor;
    }
}
