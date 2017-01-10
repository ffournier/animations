package com.animations.animations.lib.decorator;

import com.animations.animations.lib.particle.Particle;

public interface DecoratorBehavior {

    void updateParticle(float value, Particle p);
}
