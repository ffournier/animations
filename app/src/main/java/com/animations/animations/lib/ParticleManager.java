package com.animations.animations.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;


import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.decorator.DecoratorInitializer;
import com.animations.animations.lib.view.ParticleView;

/**
 * Define the manager of animations
 */
public class ParticleManager {


    // declaration
    private ParticleView p;
    private Context mContext;
    private ViewGroup mParent;


    /**
     * Constructor
     * @param context
     */
    public ParticleManager(Context context) {
        super();
        mContext = context;
    }

    /**
     * Constructor
     * @param context
     * @param parent
     * @param anchorView
     */
    public ParticleManager(Context context, @NonNull ViewGroup parent, @NonNull  View anchorView) {
        this(context);
        mParent = parent;
        init(anchorView, Gravity.CENTER);
    }

    /**
     * add Anchor View
     * @param parent
     * @param anchorView
     * @return
     */
    public ParticleManager addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView) {
        mParent = parent;
        init(anchorView, Gravity.CENTER);
        return this;
    }

    /**
     * Add anchor view
     * @param parent
     * @param anchorView
     * @param gravity
     * @return
     */
    public ParticleManager addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView, int gravity) {
        mParent = parent;
        init(anchorView, gravity);
        return this;
    }

    /**
     * init Particle Manager
     * @param anchorView
     * @param gravity
     */
    private void init(View anchorView, int gravity)  {
        cleanAnimation();

        p = new ParticleView(mContext);
        ViewGroupUtils.addView(mParent, p);
        p.setVisibility(View.VISIBLE);
        int[] mParentLocation = new int[2];
        mParent.getLocationInWindow(mParentLocation);
        int[] location = new int[2];
        anchorView.getLocationInWindow(location);

        int x;
        int y;

        if (gravity == Gravity.CENTER) {
            x = location[0]- mParentLocation[0] + anchorView.getWidth() / 2;
            y =  location[1] - mParentLocation[1] + anchorView.getHeight() / 2;
        } else {
            if (hasGravity(gravity, Gravity.CENTER_HORIZONTAL, false)) {
                x = location[0]- mParentLocation[0] + anchorView.getWidth() / 2;
            } else if(hasGravity(gravity, Gravity.LEFT, false)) {
                x = location[0]- mParentLocation[0];
            } else if(hasGravity(gravity, Gravity.RIGHT, false)) {
                x = location[0]- mParentLocation[0] + anchorView.getWidth();
            } else{
                x = location[0]- mParentLocation[0];
            }

            if (hasGravity(gravity, Gravity.CENTER_VERTICAL, true)) {
                y =  location[1] - mParentLocation[1] + anchorView.getHeight() / 2;
            } else if(hasGravity(gravity, Gravity.TOP, true)) {
                y =  location[1] - mParentLocation[1];
            } else if(hasGravity(gravity, Gravity.BOTTOM, true)) {
                y =  location[1] - mParentLocation[1] + anchorView.getHeight();
            } else{
                y =  location[1] - mParentLocation[1];
            }
        }

        p.setCenter(x, y);

    }

    /**
     * Test if it has gravity asked
     * @param gravity
     * @param gravityToCheck
     * @param vertical
     * @return
     */
    private boolean hasGravity(int gravity, int gravityToCheck, boolean vertical) {
        int checked = vertical ? Gravity.VERTICAL_GRAVITY_MASK : Gravity.HORIZONTAL_GRAVITY_MASK;
        return (gravity & checked) == gravityToCheck;
    }

    /**
     * Add Scale
     * @param minScale
     * @param maxScale
     * @return
     */
    public ParticleManager withScale(float minScale, float maxScale) {
        if (p == null)
            throw new NullPointerException();

        p.setMinScale(minScale);
        p.setMaxScale(maxScale);
        return this;
    }

    /**
     * Add Distance
     * @param distance
     * @return
     */
    public ParticleManager withDistance(int distance) {
        if (p == null)
            throw new NullPointerException();

        p.setDistance(distance);
        return this;
    }

    /**
     * Add Particle Standard Size
     * @param particleStandardSize
     * @return
     */
    public ParticleManager withParticleStandardSize(int particleStandardSize) {
        if (p == null)
            throw new NullPointerException();

        p.setParticleStandardSize(particleStandardSize);
        return this;
    }

    /**
     * Add Drawable Max
     * @param maxCount
     * @return
     */
    public ParticleManager withDrawableMax(int maxCount) {
        if (p == null)
            throw new NullPointerException();

        p.setParticleMax(maxCount);
        return this;
    }

    /**
     * Add Drawable in List
     * @param d
     * @param count
     * @return
     */
    public ParticleManager addDrawable(Drawable d, int count) {
        if (p == null)
            throw new NullPointerException();

        p.addDrawable(d, count);
        return this;
    }

    /**
     * Add Duration
     * @param duration
     * @return
     */
    public ParticleManager withDuration(int duration) {
        if (p == null)
            throw new NullPointerException();
        p.setDuration(duration);
        return this;
    }

    /**
     * Add Interpolator
     * @param inter
     * @return
     */
    public ParticleManager withInterpolator(Interpolator inter) {
        if (p == null)
            throw new NullPointerException();
        p.setInterpolator(inter);
        return this;
    }

    /**
     * Add Range Angle
     * @param angle
     * @return
     */
    public ParticleManager withRangeAngle(int angle) {
        if (p == null)
            throw new NullPointerException();
        p.setRangeAngle(angle, angle);
        return this;
    }

    /**
     * Add Range Angle
     * @param minAngle
     * @param maxAngle
     * @return
     */
    public ParticleManager withRangeAngle(int minAngle, int maxAngle) {
        if (p == null)
            throw new NullPointerException();
        p.setRangeAngle(minAngle, maxAngle);
        return this;
    }

    /**
     * Add Velocity
     * @param velocityX
     * @param velocityY
     * @return
     */
    public ParticleManager withVelocity(int velocityX, int velocityY) {
        if (p == null)
            throw new NullPointerException();
        p.setVelocity(velocityX, velocityY);
        return this;
    }

    /**
     * Start Animation
     * @param emitting
     */
    public void start(boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(new ParticleView.OnAnimationDoneListener() {
            @Override
            public void onAnimationDone() {
                cleanAnimation();
            }
        });

        if (emitting)
            p.startEmitting();
        else
            p.startOne();

    }

    /**
     * Start Animation
     */
    public void start() {
        start(false);
    }

    public void cleanAnimation() {
        if (p != null) {
            p.stopEmitting();
            ViewGroupUtils.removeView(p);
        }
    }

    /**
     * Add Decorator Initializer
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorInitializer(DecoratorInitializer decorator) {
        if (p == null)
            throw new NullPointerException();

        p.addDecoratorInitializer(decorator);
        return this;
    }

    /**
     * Add Decorator Behavior
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorBehavior(DecoratorBehavior decorator) {
        if (p == null)
            throw new NullPointerException();

        p.addDecoratorBehavior(decorator);
        return this;
    }

    /**
     * Test if the init is done
     * @return
     */
    public boolean hasAnchorView() {
        return p != null && mParent != null && ViewGroupUtils.hasView(mParent, p);
    }
}
