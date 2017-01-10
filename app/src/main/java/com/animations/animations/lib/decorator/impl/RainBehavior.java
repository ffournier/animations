package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.decorator.DecoratorBehavior;
import com.animations.animations.lib.particle.Particle;

public class RainBehavior implements DecoratorBehavior {

    @Override
    public void updateParticle(float value, Particle p) {
        p.setAlpha(255);

        p.setVelocityX(p.getVelocityXMax());

       p.setDistance(/**startDistance + **/(int) (p.getMaxDistance() * value));

        //depends of velocity
        p.setVelocityY((int) (p.getVelocityYMax() * -(1 -value)));

        if (value > 0.6f) {
            p.setAlpha(255 - (int)((value - 0.6f) * 5 * 255));
        }
        if (value == 1.0f) {
            p.setDead(true);
        }

    }
}
