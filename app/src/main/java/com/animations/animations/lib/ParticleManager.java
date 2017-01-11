package com.animations.animations.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;


import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.decorator.DecoratorInitializer;
import com.animations.animations.lib.particle.Particle;
import com.animations.animations.lib.view.ParticleView;

import java.util.HashMap;

/**
 * Define the manager of animations
 */
public class ParticleManager {


    // declaration
    private HashMap<String, ParticleView> pViews = new HashMap<>();
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
     * add Anchor View
     * @param parent
     * @param anchorView
     * @return
     */
    public ParticleView addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView, String tag) {
        mParent = parent;
        return init(anchorView, Gravity.CENTER, tag);
    }

    /**
     * Add anchor view
     * @param parent
     * @param anchorView
     * @param gravity
     * @return
     */
    public ParticleView addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView, int gravity, String tag) {
        mParent = parent;
        return init(anchorView, gravity, tag);
    }

    public ParticleView getLastParticleView() {
        if (pViews != null && pViews.size() > 0)
            return pViews.get(pViews.size() - 1);
        return null;
    }

    public int getCountParticleView() {
        if (pViews != null)
            return pViews.size();
        return -1;
    }

    public ParticleView getParticleView(String tag) {
        if (pViews != null && pViews.size() > 0)
            return pViews.get(tag);
        return null;
    }


    /**
     * init Particle Manager
     * @param anchorView
     * @param gravity
     * @param tag
     */
    private ParticleView init(View anchorView, int gravity, String tag)  {
        ParticleView p = new ParticleView(mContext);
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

        pViews.put(tag, p);

        return p;

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
        ParticleView p = getLastParticleView();
        return withScale(p, minScale, maxScale);
    }

    /**
     * Add Scale
     * @param tag
     * @param minScale
     * @param maxScale
     * @return
     */
    public ParticleManager withScale(String tag, float minScale, float maxScale) {
        ParticleView p = getParticleView(tag);
        return withScale(p, minScale, maxScale);
    }

    /**
     * Add Scale
     * @param p
     * @param minScale
     * @param maxScale
     * @return
     */
    public ParticleManager withScale(ParticleView p, float minScale, float maxScale) {
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
        ParticleView p = getLastParticleView();
        return withDistance(p, distance);
    }

    /**
     * Add Distance
     * @param tag
     * @param distance
     * @return
     */
    public ParticleManager withDistance(String tag, int distance) {
        ParticleView p = getParticleView(tag);
        return withDistance(p, distance);
    }

    /**
     * Add Distance
     * @param p
     * @param distance
     * @return
     */
    public ParticleManager withDistance(ParticleView p, int distance) {
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
        ParticleView p = getLastParticleView();
        return withParticleStandardSize(p, particleStandardSize);
    }

    /**
     * Add Particle Standard Size
     * @param tag
     * @param particleStandardSize
     * @return
     */
    public ParticleManager withParticleStandardSize(String tag, int particleStandardSize) {
        ParticleView p = getParticleView(tag);
        return withParticleStandardSize(p, particleStandardSize);
    }


    /**
     * Add Particle Standard Size
     * @param p
     * @param particleStandardSize
     * @return
     */
    public ParticleManager withParticleStandardSize(ParticleView p, int particleStandardSize) {
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
        ParticleView p = getLastParticleView();
        return withDrawableMax(p, maxCount);
    }

    /**
     * Add Drawable Max
     * @param tag
     * @param maxCount
     * @return
     */
    public ParticleManager withDrawableMax(String tag, int maxCount) {
        ParticleView p = getParticleView(tag);
        return withDrawableMax(p, maxCount);
    }

    /**
     * Add Drawable Max
     * @param p
     * @param maxCount
     * @return
     */
    public ParticleManager withDrawableMax(ParticleView p,int maxCount) {
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
        ParticleView p = getLastParticleView();
        return addDrawable(p, d , count);
    }

    /**
     * Add Drawable in List
     * @param tag
     * @param d
     * @param count
     * @return
     */
    public ParticleManager addDrawable(String tag, Drawable d, int count) {
        ParticleView p = getParticleView(tag);
        return addDrawable(p, d , count);
    }

    /**
     * Add Drawable in List
     * @param p
     * @param d
     * @param count
     * @return
     */
    public ParticleManager addDrawable(ParticleView p, Drawable d, int count) {
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
        ParticleView p = getLastParticleView();
        return withDuration(p, duration);
    }

    /**
     * Add Duration
     * @param tag
     * @param duration
     * @return
     */
    public ParticleManager withDuration(String tag, int duration) {
        ParticleView p = getParticleView(tag);
        return withDuration(p, duration);
    }


    /**
     * Add Duration
     * @param p
     * @param duration
     * @return
     */
    public ParticleManager withDuration(ParticleView p,int duration) {
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
        ParticleView p = getLastParticleView();
        return withInterpolator(p, inter);
    }

    /**
     * Add Interpolator
     * @param tag
     * @param inter
     * @return
     */
    public ParticleManager withInterpolator(String tag, Interpolator inter) {
        ParticleView p = getParticleView(tag);
        return withInterpolator(p, inter);
    }


    /**
     * Add Interpolator
     * @param p
     * @param inter
     * @return
     */
    public ParticleManager withInterpolator(ParticleView p, Interpolator inter) {
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
        ParticleView p = getLastParticleView();
        return withRangeAngle(p, angle);
    }

    /**
     * Add Range Angle
     * @param tag
     * @param angle
     * @return
     */
    public ParticleManager withRangeAngle(String tag, int angle) {
        ParticleView p = getParticleView(tag);
        return withRangeAngle(p, angle);
    }

    /**
     * Add Range Angle
     * @param p
     * @param angle
     * @return
     */
    public ParticleManager withRangeAngle(ParticleView p, int angle) {
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
        ParticleView p = getLastParticleView();
        return withRangeAngle(p, minAngle, maxAngle);
    }

    /**
     * Add Range Angle
     * @param tag
     * @param minAngle
     * @param maxAngle
     * @return
     */
    public ParticleManager withRangeAngle(String tag, int minAngle, int maxAngle) {
        ParticleView p = getParticleView(tag);
        return withRangeAngle(p, minAngle, maxAngle);
    }


    /**
     * Add Range Angle
     * @param p
     * @param minAngle
     * @param maxAngle
     * @return
     */
    public ParticleManager withRangeAngle(ParticleView p, int minAngle, int maxAngle) {
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
        ParticleView p = getLastParticleView();
        return withVelocity(p, velocityX, velocityY);
    }

    /**
     * Add Velocity
     * @param tag
     * @param velocityX
     * @param velocityY
     * @return
     */
    public ParticleManager withVelocity(String tag, int velocityX, int velocityY) {
        ParticleView p = getParticleView(tag);
        return withVelocity(p, velocityX, velocityY);
    }


    /**
     * Add
     * @param p
     * @param velocityX
     * @param velocityY
     * @return
     */
    public ParticleManager withVelocity(ParticleView p, int velocityX, int velocityY) {
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
        ParticleView p = getLastParticleView();
        start(p, emitting);
    }

    /**
     * Start Animation
     * @param tag
     * @param emitting
     */
    public void start(String tag, boolean emitting) {
        ParticleView p = getParticleView(tag);
        start(p, emitting);
    }

    /**
     * Start Animation
     * @param p
     * @param emitting
     */
    public void start(final ParticleView p, boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(new ParticleView.OnAnimationDoneListener() {
            @Override
            public void onAnimationDone() {
                cleanAnimation(p);
            }
        });

        if (emitting)
            p.startEmitting();
        else
            p.startOne();

    }

    /**
     * Clean one particleView
     * @param p
     */
    public void cleanAnimation(final ParticleView p) {
        if (p != null) {
            if (pViews != null && pViews.size() > 0) {
                pViews.remove(p);
            }
            p.stopEmitting();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroupUtils.removeView(p);
                }
            }, 100);

        }
    }

    /**
     * Clear All Animations
     */
    public void cleanAnimations() {
        if (pViews != null && pViews.size() > 0) {
            for (final ParticleView p : pViews.values()) {
                p.stopEmitting();
                ViewGroupUtils.removeView(p);
            }
            pViews.clear();
        }
    }

    /**
     * Add Decorator Initializer
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorInitializer(DecoratorInitializer decorator) {
        ParticleView p = getLastParticleView();
        return addDecoratorInitializer(p, decorator);
    }

    /**
     * Add Decorator Initializer
     * @param tag
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorInitializer(String tag, DecoratorInitializer decorator) {
        ParticleView p = getParticleView(tag);
        return addDecoratorInitializer(p, decorator);
    }

    /**
     * Add Decorator Initializer
     * @param p
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorInitializer(ParticleView p ,DecoratorInitializer decorator) {
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
        ParticleView p = getLastParticleView();
        return addDecoratorBehavior(p, decorator);
    }

    /**
     * Add Decorator Behavior
     * @param tag
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorBehavior(String tag, DecoratorBehavior decorator) {
        ParticleView p = getParticleView(tag);
        return addDecoratorBehavior(p, decorator);
    }

    /**
     * Add Decorator Behavior
     * @param p
     * @param decorator
     * @return
     */
    public ParticleManager addDecoratorBehavior(ParticleView p, DecoratorBehavior decorator) {
        if (p == null)
            throw new NullPointerException();

        p.addDecoratorBehavior(decorator);
        return this;
    }

    /**
     * Test if one init is done
     * @return
     */
    public boolean hasAnchorView() {
        if(pViews != null && mParent != null && pViews.size() > 0) {
            for (ParticleView p : pViews.values()) {
                if(ViewGroupUtils.hasView(mParent, p))
                    return true;
            }
        }
        return false;
    }

    /**
     * Test if p has init done
     * @param p
     * @return
     */
    public boolean hasAnchorView(ParticleView p) {
        return ViewGroupUtils.hasView(mParent, p);
    }
}
