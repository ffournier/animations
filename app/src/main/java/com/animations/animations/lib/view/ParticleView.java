package com.animations.animations.lib.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.animations.animations.R;
import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.decorator.DecoratorInitializer;
import com.animations.animations.lib.decorator.DefaultInitializer;
import com.animations.animations.lib.particle.Particle;


/**
 * Define the View to draws the animations
 */
public class ParticleView extends View implements ValueAnimator.AnimatorUpdateListener {

    // declaration static
    private static final int EMIT_DURATION = 12; /* ms per particles */
    private static final int MAX_PARTICLES = 1;
    private static final int DEFAULT_DURATION = 1000; /* ms */
    private static final float DEFAULT_SCALE_MIN = 1.0f;
    private static final float DEFAULT_SCALE_MAX = 3.0f;
    private static final int DEFAULT_PARTICLE_SIZE = 48;
    private static final int DEFAULT_DISTANCE = 1000;
    private static final int DEFAULT_ANGLE_MIN = 0;
    private static final int DEFAULT_ANGLE_MAX = 360;
    private static final long TIMER_TASK_INTERVAL = 80L;



    // declaration
    private final ArrayList<Particle> mParticleCache = new ArrayList<>();
    private final ArrayList<Particle> mParticles = new ArrayList<>();

    private final Map<Drawable, Integer> mCountPerDrawable = new HashMap<>();

    private int mDistance;
    private int mParticleStandardSize;

    private OnAnimationDoneListener mListener;

    private float mMinScale = DEFAULT_SCALE_MIN;
    private float mMaxScale = DEFAULT_SCALE_MAX;

    private int mMinAngle = DEFAULT_ANGLE_MIN;
    private int mMaxAngle = DEFAULT_ANGLE_MAX;

    private int mCenterX;
    private int mCenterY;
    private int mDuration;

    private int mVelocityX;
    private int mVelocityY;

    private Interpolator mInterpolator;
    private int mMaxParticle;

    private Timer mTimer;

    // decorator
    private List<DecoratorInitializer> mInitializer = new ArrayList<>();
    private List<DecoratorBehavior> mBehavior = new ArrayList<>();

    /**
     * Constructor
     * @param context
     */
    public ParticleView(Context context) {
        super(context);
        init(null, 0);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ParticleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * init
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {
        mDuration = DEFAULT_DURATION;
        mMinScale = DEFAULT_SCALE_MIN;
        mMaxScale = DEFAULT_SCALE_MAX;
        mParticleStandardSize = DEFAULT_PARTICLE_SIZE;
        mDistance = DEFAULT_DISTANCE;
        mInterpolator = new LinearInterpolator();
        mMaxParticle = MAX_PARTICLES;

        if (attrs != null) {
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.ParticleView, defStyle, 0);

            mMinScale = a.getFloat(R.styleable.ParticleView_minScale, mMinScale);
            mMaxScale = a.getFloat(R.styleable.ParticleView_maxScale, mMaxScale);

            a.recycle();
        }


    }

    /**
     * Setter Distance
     * @param distance
     */
    public void setDistance(int distance) {
        mDistance = distance;
    }

    /**
     * Setter Minimum Scale
     * @param minScale
     */
    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }

    /**
     * Setter Maximum Scale
     * @param maxScale
     */
    public void setMaxScale(float maxScale) {
        mMaxScale = maxScale;
    }

    /**
     * Setter Center
     * @param x
     * @param y
     */
    public void setCenter(int x, int y) {
        mCenterX = x;
        mCenterY = y;
    }

    /**
     * Setter Particle Standard Size
     * @param particleStandardSize
     */
    public void setParticleStandardSize(int particleStandardSize) {
        mParticleStandardSize = particleStandardSize;
    }

    /**
     * Setter Velocity
     * @param velocityX
     * @param velocityY
     */
    public void setVelocity(int velocityX, int velocityY) {
        mVelocityX = velocityX;
        mVelocityY = velocityY;
    }

    /**
     * Add Drawable
     * @param drawable
     * @param count
     */
    public void addDrawable(Drawable drawable, int count) {
        int newCount = count;
        Integer currentCount = mCountPerDrawable.get(drawable);
        if (currentCount != null) {
            newCount += currentCount;
        }

        mCountPerDrawable.put(drawable, newCount);
    }

    /**
     * Start One Shot
     */
    public void startOne() {
        if (mMaxParticle == 1) {

            Set<Drawable> drawables = mCountPerDrawable.keySet();
            Drawable drawable = drawables.isEmpty() ? null : drawables.iterator().next();
            if (drawable == null) {
                reset();
                if (mListener != null) {
                    mListener.onAnimationDone();
                }

                return;
            }

            final Particle particle = new Particle(getContext(), drawable, mCenterX, mCenterY, mMinScale, mMaxScale, mMinAngle, mMaxAngle, mParticleStandardSize, mDistance, mDuration);
            mParticles.add(runInitializer(particle));
            particle.start(mInterpolator, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    runBehavior((float) animation.getAnimatedValue(), particle);
                }
            });
        } else {
            // calculate total number
            int totalCount = 0;
            for (int count : mCountPerDrawable.values()) {
                totalCount += count;
            }
            int actualTotalCount = Math.max(totalCount, mMaxParticle);
            float ratio = Math.min(1f, (float) mMaxParticle / Math.min(totalCount, mMaxParticle));
            long duration = actualTotalCount * EMIT_DURATION;

            // create particles for each drawable
            for (Map.Entry<Drawable, Integer> entry : mCountPerDrawable.entrySet()) {
                int actualCount = Math.max(1, (int) (ratio * entry.getValue()));
                for (int i = 0; i < actualCount; i++) {

                    Particle p = new Particle(getContext(), entry.getKey(), mCenterX, mCenterY, mMinScale, mMaxScale, mMinAngle, mMaxAngle, mParticleStandardSize, mDistance, mDuration, mVelocityX, mVelocityY);
                    mParticleCache.add(runInitializer(p));
                }
            }
            ValueAnimator emitAnimator = new ValueAnimator();
            emitAnimator.setIntValues(0, actualTotalCount);
            emitAnimator.setCurrentPlayTime(Math.min(2 * EMIT_DURATION, duration));
            emitAnimator.setDuration(duration);
            emitAnimator.addUpdateListener(this);
            emitAnimator.start();
        }
    }


    /**
     * Start Emitting
     */
    public void startEmitting() {
        stopEmitting();
        mTimer = new Timer();
        mTimer.schedule(new MyTimerTask(), 0, TIMER_TASK_INTERVAL);
    }

    /**
     * Stop Emitting
     */
    public void stopEmitting() {
        if (mTimer != null) {
            mTimer.purge();
            mTimer.cancel();
            reset();
        }
    }

    /**
     * Add Decorator Initializer
     * @param decorator
     */
    public void addDecoratorInitializer(DecoratorInitializer decorator) {
        mInitializer.add(decorator);
    }

    /**
     * Add Decorator Behavior
     * @param decorator
     */
    public void addDecoratorBehavior(DecoratorBehavior decorator) {
        mBehavior.add(decorator);
    }

    /**
     * Timer to refresh the animation
     */
    private class MyTimerTask extends TimerTask {

        private Handler mHandler;

        MyTimerTask() {
            super();
            Looper looper = Looper.getMainLooper();
            mHandler = new Handler(looper);
        }

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // remove particle dead

                    for (Iterator<Particle> iterator = mParticles.iterator(); iterator.hasNext(); ) {
                        Particle particle = iterator.next();
                        if (particle.isDead()) {
                            // Remove the current element from the iterator and the list.
                            iterator.remove();
                        }
                    }

                    // create particles for each drawable
                    int totalCount = 0;
                    for (int count : mCountPerDrawable.values()) {
                        totalCount += count;
                    }
                    float ratio = Math.min(1f, (float) mMaxParticle / Math.min(totalCount, mMaxParticle));
                    for (Map.Entry<Drawable, Integer> entry : mCountPerDrawable.entrySet()) {
                        int actualCount = Math.max(1, (int) (ratio * entry.getValue()));
                        for (int i = 0; i < actualCount; i++) {
                            Particle p = new Particle(getContext(), entry.getKey(), mCenterX, mCenterY, mMinScale, mMaxScale, mMinAngle, mMaxAngle, mParticleStandardSize, mDistance, mDuration, mVelocityX, mVelocityY);
                            mParticleCache.add(runInitializer(p));
                        }
                    }

                    ValueAnimator anim = new ValueAnimator();
                    anim.setIntValues(0);
                    onAnimationUpdate(anim);
                }
            });

        }
    }

    /**
     * Run All Initializer
     * @param p
     * @return
     */
    private Particle runInitializer(Particle p) {
        if (mInitializer.size() > 0) {
            for (DecoratorInitializer deco : mInitializer) {
                deco.initParticle(p);
            }
        } else {
            new DefaultInitializer().initParticle(p);
        }
        return p;
    }

    /**
     * Run All Behavior
     * @param value
     * @param p
     * @return
     */
    private Particle runBehavior(float value, Particle p) {
        if (mBehavior.size() > 0) {
            for (DecoratorBehavior deco : mBehavior) {
                deco.updateParticle(value, p);
            }
        }
        return p;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int count = (int) animation.getAnimatedValue();
        while ((count == 0 || mParticles.size() < count) && mParticleCache.size() > 0) {
            final Particle particle = mParticleCache.remove(0);
            particle.start(mInterpolator, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    runBehavior((float)animation.getAnimatedValue(), particle);
                }
            });
            mParticles.add(particle);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean alive = false;
        for (Particle particle : mParticles) {
            particle.draw(canvas);
            if (!particle.isDead()) {
                alive = true;
            }
        }

        if (alive || mParticles.size() == 0) {
            invalidate();
        } else {
            reset();
            if (mListener != null) {
                mListener.onAnimationDone();
            }
        }
    }

    /**
     * Set Listener
     * @param listener
     */
    public void setOnAnimationDoneListener(OnAnimationDoneListener listener) {
        mListener = listener;
    }

    /**
     * Reset All Data
     */
    private void reset() {
        mParticleCache.clear();
        mParticles.clear();
        mCountPerDrawable.clear();
    }

    /**
     * Setter Duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    /**
     * Setter Interpolator
     * @param interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    /**
     * Setter Range Angle
     * @param minAngle
     * @param maxAngle
     */
    public void setRangeAngle(int minAngle, int maxAngle) {
        if (minAngle < DEFAULT_ANGLE_MIN && minAngle > DEFAULT_ANGLE_MAX && minAngle > maxAngle
                && maxAngle < DEFAULT_ANGLE_MIN && maxAngle > DEFAULT_ANGLE_MAX)
            throw new IllegalArgumentException();
        mMaxAngle = maxAngle;
        mMinAngle = minAngle;
    }

    /**
     * Set Particle Max
     * @param particleMax
     */
    public void setParticleMax(int particleMax) {
        this.mMaxParticle = particleMax;
    }


    /**
     * Interface Call Back
     */
    public interface OnAnimationDoneListener {
        void onAnimationDone();
    }

}