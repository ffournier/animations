package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.decorator.DefaultInitializer;
import com.animations.animations.lib.particle.Particle;

public class TranslatePingInitializer extends DefaultInitializer {

    @Override
    public void initParticle(Particle p) {
        super.initParticle(p);
        p.setVelocityXMax(p.getRandom().nextInt(p.getVelocityXMax()) - p.getVelocityXMax() / 2);
    }
}
