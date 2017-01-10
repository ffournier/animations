package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.decorator.DefaultInitializer;
import com.animations.animations.lib.particle.Particle;

public class RainInitializer extends DefaultInitializer {

    @Override
    public void initParticle(Particle p) {
        super.initParticle(p);

        int velocityXMaximum = p.getVelocityXMax();
        int velocityYMaximum = p.getVelocityYMax();
        if (velocityXMaximum > 0)
            p.setVelocityXMax(p.getRandom().nextInt(velocityXMaximum) - velocityXMaximum / 2);
        if (velocityYMaximum > 0)
            p.setVelocityYMax(p.getRandom().nextInt(velocityYMaximum));


        p.setScale( (p.getVelocityYMax()) / (velocityYMaximum * 1.0f) * (p.getMaxScale() - p.getMinScale()) + p.getMinScale());
        p.setDuration((int) (p.getDuration() + ((p.getVelocityYMax() / (velocityYMaximum * 1.0f)) * p.getDuration() * 2)));
    }
}
