package com.animations.animations.lib.decorator;

import com.animations.animations.lib.Tools;
import com.animations.animations.lib.particle.Particle;


public class DefaultInitializer implements DecoratorInitializer {

    @Override
    public void initParticle(Particle p) {
        float correctiveScale = Tools.dipToPixels(p.getContext(), p.getParticleStandardSize()) / p.getHeight();
        p.setMinScale(p.getMinScale() * correctiveScale);
        p.setMaxScale(p.getMaxScale() * correctiveScale);

        double angle;
        if (p.getMaxAngle() > p.getMinAngle())
            angle = Math.toRadians(p.getRandom().nextInt(p.getMaxAngle() - p.getMinAngle()) + p.getMinAngle());
        else
            angle = Math.toRadians(p.getMinAngle());

        p.setCos(Math.cos(angle));
        p.setSin(Math.sin(angle));
    }
}
