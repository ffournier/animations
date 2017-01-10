package com.animations.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.animations.animations.lib.ParticleManager;
import com.animations.animations.lib.decorator.impl.ExplosionBehavior;
import com.animations.animations.lib.decorator.impl.ExplosionInitializer;

public class MainActivity extends AppCompatActivity {

    private ParticleManager mParticleManager;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_view);

        // start animation by drawables
        mParticleManager = new ParticleManager(this);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEplosion();
            }
        });

   }

    private void startEplosion() {
        mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), mImageView, Gravity.CENTER)
                .withScale(0.3f, 2f)
                .withDistance(1000)
                .withParticleStandardSize(48)
                .withDuration(1000)
                .withInterpolator(new LinearInterpolator())
                .withRangeAngle(0, 360)
                .withDrawableMax(60)
                .addDecoratorInitializer(new ExplosionInitializer())
                .addDecoratorBehavior(new ExplosionBehavior())
                .addDrawable(getResources().getDrawable(android.R.drawable.ic_btn_speak_now), 30)
                .addDrawable(getResources().getDrawable(android.R.drawable.ic_delete), 30)
                .start(false);
    }
}
