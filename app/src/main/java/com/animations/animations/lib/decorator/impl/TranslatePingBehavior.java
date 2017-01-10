package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.particle.Particle;

public class TranslatePingBehavior implements DecoratorBehavior {

    @Override
    public void updateParticle(float value, Particle p) {

        p.setAlpha(255);
        p.setScale(1.0f);

        p.setDistance(/**startDistance + **/(int) (p.getMaxDistance() * value));

        // --> 1 to max then max to min
        float distanceMiddle = p.getMaxDistance() / 2;
        if (p.getDistance() < distanceMiddle) {
            p.setScale(p.getDistance() / distanceMiddle * (p.getMaxScale() - 1) + 1f);
        } else {
            p.setScale((p.getDistance() - distanceMiddle) / distanceMiddle * (p.getMinScale() - p.getMaxScale()) + p.getMaxScale());
        }

        if (value < 0.5f) {
            p.setVelocityX((int) (value / 0.5f * p.getVelocityXMax())) ;
        } else {
            p.setVelocityX((int) ((value - 0.5f) / -0.5f * p.getVelocityXMax() + p.getVelocityXMax()));
        }

        if (value > 0.6f) {
            p.setAlpha(255 - (int)((value - 0.6f) * 5 * 255));
        }
        if (value == 1.0f) {
            p.setDead(true);
        }
    }
}
