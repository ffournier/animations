package com.animations.animations.lib.particle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.Interpolator;

import java.util.Random;

public class Particle {

    protected double cos;
    protected double sin;

    protected ValueAnimator animator;
    protected boolean dead;

    protected int maxDistance;
    protected int distance;
    protected float randomDistance;

    protected int alpha = 255;

    protected final BitmapDrawable drawable;
    protected final int height;
    protected final int width;

    protected final Paint paint = new Paint();
    protected final Rect dst = new Rect();

    protected Random mRandom = new Random();

    protected int mCenterX;
    protected int mCenterY;

    protected int mMinAngle;
    protected int mMaxAngle;

    protected double mMinScale;
    protected double mMaxScale;
    protected double mScale;
    protected int mDuration;

    protected int mVelocityX;
    protected int mVelocityY;
    protected int mVelocityXMax;
    protected int mVelocityYMax;
    private float particleStandardSize;

    private Context mContext;

    public Particle(Context context, @NonNull Drawable drawable, int centerX, int centerY, float minScale, float maxScale,
                    int minAngle, int maxAngle,
                    float particleStandardSize, int distance, int duration, int velocityX, int velocityY) {
        this(context, drawable, centerX, centerY, minScale, maxScale, minAngle, maxAngle,
                particleStandardSize, distance, duration);

        mVelocityXMax = velocityX;
        mVelocityYMax = velocityY;
    }


    public Particle(Context context, @NonNull Drawable drawable,  int centerX, int centerY, float minScale, float maxScale,
                    int minAngle, int maxAngle,
                    float particleStandardSize, int distance, int duration) {

        mContext = context;

        this.drawable = (BitmapDrawable) drawable;
        width = drawable.getIntrinsicWidth();
        height = drawable.getIntrinsicHeight();

        this.particleStandardSize = particleStandardSize;

        maxDistance = distance;
        mMinScale = minScale;
        mMaxScale = maxScale;

        mMinAngle = minAngle;
        mMaxAngle = maxAngle;

        mCenterX = centerX;
        mCenterY = centerY;

        mDuration = duration;
        mVelocityXMax = 0;
        mVelocityYMax = 0;
        mVelocityX = 0;
        mVelocityY = 0;

    }


    public void start(Interpolator inter, ValueAnimator.AnimatorUpdateListener listener) {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration((long) mDuration);
        animator.setInterpolator(inter);
        animator.addUpdateListener(listener);
        animator.start();
    }



    public boolean isDead() {
        return dead;
    }

    public void draw(Canvas canvas) {
        if (drawable != null && !dead) {
            int w = (int) (width * mScale);
            int h = (int) (height * mScale);
            int x = mCenterX + (int)(distance * cos - w / 2) + mVelocityX;
            int y = mCenterY + (int)(distance * sin - h / 2) + mVelocityY;
            dst.set(x, y, x + w, y + h);

            paint.setAlpha(alpha);
            canvas.drawBitmap(drawable.getBitmap(), new Rect(0,0,width,height), dst, paint);
        }
    }




    public Context getContext() {
        return mContext;
    }

    public float getParticleStandardSize() {
        return particleStandardSize;
    }

    public int getHeight() {
        return height;
    }

    public Random getRandom() {
        return mRandom;
    }

    public void setRandomDistance(float randomDistance) {
        this.randomDistance = randomDistance;
    }

    public float getRandomDistance() {
        return randomDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setMinScale(double minScale) {
        this.mMinScale = minScale;
    }

    public void setMaxScale(double maxScale) {
        this.mMaxScale = maxScale;
    }

    public int getDistance() {
        return distance;
    }

    public double getMinScale() {
        return mMinScale;
    }

    public double getMaxScale() {
        return mMaxScale;
    }

    public int getMaxAngle() {
        return mMaxAngle;
    }

    public int getMinAngle() {
        return mMinAngle;
    }

    public void setCos(double cos) {
        this.cos = cos;
    }

    public void setSin(double sin) {
        this.sin = sin;
    }

    public int getVelocityXMax() {
        return mVelocityXMax;
    }

    public int getVelocityYMax() {
        return mVelocityYMax;
    }

    public void setVelocityXMax(int velocityXMax) {
        this.mVelocityXMax = velocityXMax;
    }

    public void setVelocityYMax(int velocityYMax) {
        this.mVelocityYMax = velocityYMax;
    }

    public void setScale(double scale) {
        this.mScale = scale;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setVelocityX(int velocityX) {
        this.mVelocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.mVelocityY = velocityY;
    }
}