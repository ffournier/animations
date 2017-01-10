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

/**
 * Define the attribute of a particle
 */
public class Particle {

    // declaration attributes
    private double cos;
    private double sin;

    private ValueAnimator animator;
    private boolean dead;

    private int maxDistance;
    private int distance;
    private float randomDistance;

    private int alpha = 255;

    private final BitmapDrawable drawable;
    private final int height;
    private final int width;

    private final Paint paint = new Paint();
    private final Rect dst = new Rect();

    private Random mRandom = new Random();

    private int mCenterX;
    private int mCenterY;

    private int mMinAngle;
    private int mMaxAngle;

    private double mMinScale;
    private double mMaxScale;
    private double mScale;
    private int mDuration;

    private int mVelocityX;
    private int mVelocityY;
    private int mVelocityXMax;
    private int mVelocityYMax;
    private float particleStandardSize;

    // Context
    private Context mContext;

    /**
     * Constructor
     * @param context
     * @param drawable
     * @param centerX
     * @param centerY
     * @param minScale
     * @param maxScale
     * @param minAngle
     * @param maxAngle
     * @param particleStandardSize
     * @param distance
     * @param duration
     * @param velocityX
     * @param velocityY
     */
    public Particle(Context context, @NonNull Drawable drawable, int centerX, int centerY, float minScale, float maxScale,
                    int minAngle, int maxAngle,
                    float particleStandardSize, int distance, int duration, int velocityX, int velocityY) {
        this(context, drawable, centerX, centerY, minScale, maxScale, minAngle, maxAngle,
                particleStandardSize, distance, duration);

        mVelocityXMax = velocityX;
        mVelocityYMax = velocityY;
    }


    /**
     * Constructor
     * @param context
     * @param drawable
     * @param centerX
     * @param centerY
     * @param minScale
     * @param maxScale
     * @param minAngle
     * @param maxAngle
     * @param particleStandardSize
     * @param distance
     * @param duration
     */
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


    /**
     * Start Particle Animation
     * @param inter
     * @param listener
     */
    public void start(Interpolator inter, ValueAnimator.AnimatorUpdateListener listener) {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration((long) mDuration);
        animator.setInterpolator(inter);
        animator.addUpdateListener(listener);
        animator.start();
    }

    /**
     * Return if the animation of particle is ending ?
     * @return
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Draw the particle depending of attributes
     * @param canvas
     */
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

    /**
     * Getter Context
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Getter Particle Standard Size
     * @return
     */
    public float getParticleStandardSize() {
        return particleStandardSize;
    }

    /**
     * Getter Height
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter local Random field
     * @return
     */
    public Random getRandom() {
        return mRandom;
    }

    /**
     * Setter Random Ditance
     * @param randomDistance
     */
    public void setRandomDistance(float randomDistance) {
        this.randomDistance = randomDistance;
    }

    /**
     * Getter Random Distance
     * @return
     */
    public float getRandomDistance() {
        return randomDistance;
    }

    /**
     * Setter Maximum Distance
     * @param maxDistance
     */
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * Setter Miminum Scale
     * @param minScale
     */
    public void setMinScale(double minScale) {
        this.mMinScale = minScale;
    }

    /**
     * Setter Maximum Scale
     * @param maxScale
     */
    public void setMaxScale(double maxScale) {
        this.mMaxScale = maxScale;
    }

    /**
     * Getter Distance
     * @return
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Getter Mimium Scale
     * @return
     */
    public double getMinScale() {
        return mMinScale;
    }

    /**
     * Getter Maximum Scale
     * @return
     */
    public double getMaxScale() {
        return mMaxScale;
    }

    /**
     * Getter Maximum Angle
     * @return
     */
    public int getMaxAngle() {
        return mMaxAngle;
    }

    /**
     * Getter Mimimum Angle
     * @return
     */
    public int getMinAngle() {
        return mMinAngle;
    }

    /**
     * Setter Cosinus
     * @param cos
     */
    public void setCos(double cos) {
        this.cos = cos;
    }

    /**
     * Setter Sinus
     * @param sin
     */
    public void setSin(double sin) {
        this.sin = sin;
    }

    /**
     * Getter Velocity X Maximum
     * @return
     */
    public int getVelocityXMax() {
        return mVelocityXMax;
    }

    /**
     * Getter Velocity Y maximum
     * @return
     */
    public int getVelocityYMax() {
        return mVelocityYMax;
    }

    /**
     * Setter Velocity X Maximum
     * @param velocityXMax
     */
    public void setVelocityXMax(int velocityXMax) {
        this.mVelocityXMax = velocityXMax;
    }

    /**
     * Setter Velocity Y Maximum
     * @param velocityYMax
     */
    public void setVelocityYMax(int velocityYMax) {
        this.mVelocityYMax = velocityYMax;
    }

    /**
     * Setter Scale
     * @param scale
     */
    public void setScale(double scale) {
        this.mScale = scale;
    }

    /**
     * Getter Duration
     * @return
     */
    public int getDuration() {
        return mDuration;
    }

    /**
     * Setter Duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    /**
     * Setter Distance
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Getter Maximum Distance
     * @return
     */
    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * Setter Alpha
     * @param alpha
     */
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    /**
     * Setter Dead
     * @param dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Setter Velocity X
     * @param velocityX
     */
    public void setVelocityX(int velocityX) {
        this.mVelocityX = velocityX;
    }

    /**
     * Setter Velocity Y
     * @param velocityY
     */
    public void setVelocityY(int velocityY) {
        this.mVelocityY = velocityY;
    }
}