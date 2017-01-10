package com.animations.animations.lib.decorator.impl;

import com.animations.animations.lib.Tools;
import com.animations.animations.lib.decorator.DefaultInitializer;
import com.animations.animations.lib.particle.Particle;

public class ExplosionInitializer extends DefaultInitializer {

    @Override
    public void initParticle(Particle p) {
        super.initParticle(p);

        float correctiveScale = Tools.dipToPixels(p.getContext(), p.getParticleStandardSize()) / p.getHeight();

        p.setRandomDistance(0.5f + p.getRandom().nextFloat());
        p.setMaxDistance((int) (p.getRandomDistance() * p.getMaxDistance()));
        float randomScale = 1.0f + p.getRandom().nextFloat() / 2;
        p.setMinScale(randomScale * p.getMinScale() * correctiveScale);
        p.setMaxScale(randomScale * p.getMaxScale() * correctiveScale);
    }

}
