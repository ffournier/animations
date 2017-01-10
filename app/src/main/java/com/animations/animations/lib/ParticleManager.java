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

public class ParticleManager {


    private ParticleView p;
    private Context mContext;
    private ViewGroup mParent;


    public ParticleManager(Context context) {
        super();
        mContext = context;
    }

    public ParticleManager(Context context, @NonNull ViewGroup parent, @NonNull  View anchorView) {
        this(context);
        mParent = parent;
        init(anchorView, Gravity.CENTER);
    }

    public ParticleManager addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView) {
        mParent = parent;
        init(anchorView, Gravity.CENTER);
        return this;
    }

    public ParticleManager addAnchorView(@NonNull ViewGroup parent, @NonNull  View anchorView, int gravity) {
        mParent = parent;
        init(anchorView, gravity);
        return this;
    }

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

    private boolean hasGravity(int gravity, int gravityToCheck, boolean vertical) {
        int checked = vertical ? Gravity.VERTICAL_GRAVITY_MASK : Gravity.HORIZONTAL_GRAVITY_MASK;
        return (gravity & checked) == gravityToCheck;
    }

    public ParticleManager withScale(float minScale, float maxScale) {
        if (p == null)
            throw new NullPointerException();

        //p.setMinScale(1.0f);
        //p.setMaxScale(3.0f);

        p.setMinScale(minScale);
        p.setMaxScale(maxScale);
        return this;
    }

    public ParticleManager withDistance(int distance) {
        if (p == null)
            throw new NullPointerException();

        //p.setDistance(4000);
        p.setDistance(distance);
        return this;
    }

    public ParticleManager withParticleStandardSize(int particleStandardSize) {
        if (p == null)
            throw new NullPointerException();

        //p.setDistance(4000);
        p.setParticleStandardSize(particleStandardSize);
        return this;
    }

    public ParticleManager withDrawableMax(int maxCount) {
        if (p == null)
            throw new NullPointerException();

        //p.setDistance(4000);
        p.setParticleMax(maxCount);
        return this;
    }

    public ParticleManager addDrawable(Drawable d, int count) {
        if (p == null)
            throw new NullPointerException();

        //p.setDistance(4000);
        p.addDrawable(d, count);
        return this;
    }

    public ParticleManager withDuration(int duration) {
        if (p == null)
            throw new NullPointerException();
        p.setDuration(duration);
        return this;
    }

    public ParticleManager withInterpolator(Interpolator inter) {
        if (p == null)
            throw new NullPointerException();
        p.setInterpolator(inter);
        return this;
    }

    public ParticleManager withRangeAngle(int angle) {
        if (p == null)
            throw new NullPointerException();
        p.setRangeAngle(angle, angle);
        return this;
    }

    public ParticleManager withRangeAngle(int minAngle, int maxAngle) {
        if (p == null)
            throw new NullPointerException();
        p.setRangeAngle(minAngle, maxAngle);
        return this;
    }

    public ParticleManager withVelocity(int velocityX, int velocityY) {
        if (p == null)
            throw new NullPointerException();
        p.setVelocity(velocityX, velocityY);
        return this;
    }

    /**public void startAnimatingExplosion(boolean emitting) {
        if (p == null)
            throw new NullPointerException();


        p.setOnAnimationDoneListener(() -> cleanAnimation());
        p.startAnimatingExplosion(emitting);
    }

    public void startAnimatingExplosion() {
        startAnimatingScale(false);
    }

    public void startAnimatingRain(boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(() -> cleanAnimation());
        p.startAnimatingRain(emitting);
    }

    public void startAnimatingRain() {
        startAnimatingScale(false);
    }

    public void startAnimatingScale(boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(() -> cleanAnimation());
        p.startAnimatingScale(emitting);
    }

    public void startAnimatingScale() {
        startAnimatingScale(false);
    }

    public void startAnimatingTranslate(boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(() -> cleanAnimation());
        p.startAnimatingTranslate(emitting);
    }

    public void startAnimatingTranslate() {
        startAnimatingTranslate(false);
    }

    public void startAnimatingTranslatePing(boolean emitting) {
        if (p == null)
            throw new NullPointerException();

        p.setOnAnimationDoneListener(() -> cleanAnimation());
        p.startAnimatingTranslatePing(emitting);
    }

    public void startAnimatingTranslatePing() {
        startAnimatingTranslate(false);
    }**/

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

    public void start() {
        start(false);
    }

    public void cleanAnimation() {
        if (p != null) {
            p.stopEmitting();
            ViewGroupUtils.removeView(p);
        }
    }

    public ParticleManager addDecoratorInitializer(DecoratorInitializer decorator) {
        if (p == null)
            throw new NullPointerException();

        p.addDecoratorInitializer(decorator);
        return this;
    }

    public ParticleManager addDecoratorBehavior(DecoratorBehavior decorator) {
        if (p == null)
            throw new NullPointerException();

        p.addDecoratorBehavior(decorator);
        return this;
    }
}
