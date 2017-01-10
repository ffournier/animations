package com.animations.animations.lib.decorator;

import com.animations.animations.lib.particle.Particle;

/**
 * Decorator Behavior, so during the animation
 */
public interface DecoratorBehavior {

    /**
     * Update the particle depends of the given value.
     * @param value : 0 .. 1
     * @param p : the particle to update
     */
    void updateParticle(float value, Particle p);
}
