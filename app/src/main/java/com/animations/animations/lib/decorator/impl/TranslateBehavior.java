package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.particle.Particle;

public class TranslateBehavior implements DecoratorBehavior {

    @Override
    public void updateParticle(float value, Particle p) {

        p.setAlpha(255);
        p.setScale(1.0f);

        p.setDistance(/**startDistance + **/(int) (p.getMaxDistance() * value));

        if (value > 0.8f) {
            p.setAlpha(255 - (int)((value - 0.8f) * 5 * 255));
        }
        if (value == 1.0f) {
            p.setDead(true);
        }
    }
}
