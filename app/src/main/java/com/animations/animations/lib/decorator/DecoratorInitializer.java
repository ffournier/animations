package com.animations.animations.lib.decorator;

import com.animations.animations.lib.particle.Particle;

/**
 * Decorator of Initializer, Define the properties of animations.
 */
public interface DecoratorInitializer {

    /**
     * Init Particle
     * @param p : the particle to init
     */
    void initParticle(Particle p);

}
