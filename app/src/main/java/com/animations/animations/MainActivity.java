package com.animations.animations;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.animations.animations.lib.ParticleManager;
import com.animations.animations.lib.Tools;
import com.animations.animations.lib.decorator.DefaultInitializer;
import com.animations.animations.lib.decorator.impl.ExplosionBehavior;
import com.animations.animations.lib.decorator.impl.ExplosionInitializer;
import com.animations.animations.lib.decorator.impl.RainBehavior;
import com.animations.animations.lib.decorator.impl.RainInitializer;
import com.animations.animations.lib.decorator.impl.ScaleBehavior;
import com.animations.animations.lib.decorator.impl.TranslateBehavior;
import com.animations.animations.lib.decorator.impl.TranslatePingBehavior;
import com.animations.animations.lib.decorator.impl.TranslatePingInitializer;
import com.animations.animations.lib.view.ParticleView;

public class MainActivity extends AppCompatActivity {

    private static final String EXPLOSION = "EXPLOSION";
    private static final String SCALING = "SCALING";
    private static final String TRANSLATE = "TRANSLATE";
    private static final String PING = "PING";
    private static final String RAIN = "RAIN";
    private ParticleManager mParticleManager;
    private ImageView mImageViewExplosion;
    private ImageView mImageViewScaling;
    private ImageView mImageViewTranslate;
    private ImageView mImageViewRain;
    private ImageView mImageViewPing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageViewExplosion = (ImageView) findViewById(R.id.image_view_explosion);
        mImageViewTranslate = (ImageView) findViewById(R.id.image_view_translate);
        mImageViewPing = (ImageView) findViewById(R.id.image_view_ping);
        mImageViewRain = (ImageView) findViewById(R.id.image_view_rain);
        mImageViewScaling= (ImageView) findViewById(R.id.image_view_scaling);

        // start animation by drawables
        mParticleManager = new ParticleManager(this);


        mImageViewExplosion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExplosion();
            }
        });

        mImageViewTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTranslate();
            }
        });

        mImageViewPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPing();
            }
        });

        mImageViewRain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRain();
            }
        });

        mImageViewScaling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScaling();
            }
        });

    }

    private void startExplosion() {

        // if we don't have the clean animations we have a problem of initialisation of particle, to se
        if (!mParticleManager.hasAnchorView(mParticleManager.getParticleView(EXPLOSION))) {
            ParticleView p = mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), mImageViewExplosion, Gravity.CENTER, EXPLOSION);
            mParticleManager.withScale(p, 0.3f, 2f)
                    .withDistance(p, 1000)
                    .withParticleStandardSize(p, 48)
                    .withDuration(p, 1000)
                    .withInterpolator(p, new LinearInterpolator())
                    .withRangeAngle(p, 0, 360)
                    .withDrawableMax(p, 60)
                    .addDecoratorInitializer(p, new ExplosionInitializer())
                    .addDecoratorBehavior(p, new ExplosionBehavior())
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_btn_speak_now), 30)
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_delete), 30);
        }
        mParticleManager.start(EXPLOSION, false);
    }

    public void startScaling() {

        if (!mParticleManager.hasAnchorView(mParticleManager.getParticleView(SCALING))) {
            //SCALE
            ParticleView p = mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), mImageViewScaling, Gravity.CENTER, SCALING);
            mParticleManager.withScale(p, 0.1f, 3f)
                    .withDistance(p, 1000)
                    .withParticleStandardSize(p, 48)
                    .withDuration(p, 1000)
                    .withInterpolator(p, new LinearInterpolator())
                    .addDecoratorInitializer(p, new DefaultInitializer())
                    .addDecoratorBehavior(p, new ScaleBehavior())
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_btn_speak_now), 1);
        }
        mParticleManager.start(SCALING, false);
    }

    public void startTranslate() {
        if (!mParticleManager.hasAnchorView(mParticleManager.getParticleView(TRANSLATE))) {
            // TRANSLATE
            ParticleView p =  mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), mImageViewTranslate, Gravity.CENTER, TRANSLATE);
            mParticleManager.withScale(p, 0.1f, 3f)
                    .withDistance(p, 1000)
                    .withParticleStandardSize(p, 48)
                    .withDuration(p, 1000)
                    .withRangeAngle(p, 30)
                    .withInterpolator(p, new LinearInterpolator())
                    .addDecoratorInitializer(p, new DefaultInitializer())
                    .addDecoratorBehavior(p, new TranslateBehavior())
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_btn_speak_now), 1);
        }
        mParticleManager.start(TRANSLATE, false);
    }

    public void startPing() {

        if (!mParticleManager.hasAnchorView(mParticleManager.getParticleView(PING))) {

            int[] pointStart = new int[2];
            mImageViewPing.getLocationOnScreen(pointStart);

            pointStart[0] += mImageViewPing.getWidth() / 2;
            pointStart[1] += mImageViewPing.getHeight() / 2;

            int[] pointTarget = new int[2];
            mImageViewExplosion.getLocationOnScreen(pointTarget);

            pointTarget[0] += mImageViewExplosion.getWidth() / 2;
            pointTarget[1] += mImageViewExplosion.getHeight() / 2;

            // TRANSLATE PING
            ParticleView p = mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), mImageViewPing, Gravity.CENTER, PING);
            mParticleManager.withScale(p, 0.3f, 2f)
                    .withDistance(p, Tools.getDistance(pointStart, pointTarget))
                    .withParticleStandardSize(p, 48)
                    .withDuration(p, 1000)
                    .withInterpolator(p, new LinearInterpolator())
                    .withRangeAngle(p, Tools.getAngle(pointStart, pointTarget))
                    .withVelocity(p, 100, 0)
                    .withDrawableMax(p, 1)
                    .addDecoratorInitializer(p, new TranslatePingInitializer())
                    .addDecoratorBehavior(p, new TranslatePingBehavior())
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_delete), 1);
        }
        mParticleManager.start(PING, true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mParticleManager.cleanAnimation(mParticleManager.getParticleView(PING));
            }
        }, 10000);
    }

    public void startRain() {

        if (!mParticleManager.hasAnchorView(mParticleManager.getParticleView(RAIN))) {

            int width = findViewById(android.R.id.content).getWidth();
            int height = findViewById(android.R.id.content).getHeight();

            // RAIN
            ParticleView p = mParticleManager.addAnchorView((ViewGroup) findViewById(android.R.id.content), (ViewGroup) findViewById(android.R.id.content), Gravity.TOP | Gravity.CENTER_HORIZONTAL, RAIN);
            mParticleManager.withScale(p, 0.3f, 2f)
                    .withDistance(p, height)
                    .withParticleStandardSize(p, 48)
                    .withDuration(p, 1500)
                    .withInterpolator(p, new BounceInterpolator())
                    .withRangeAngle(p, 90)
                    .withVelocity(p, width, height / 2)
                    .withDrawableMax(p, 20)
                    .addDecoratorInitializer(p, new RainInitializer())
                    .addDecoratorBehavior(p, new RainBehavior())
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_btn_speak_now), 10)
                    .addDrawable(p, getResources().getDrawable(android.R.drawable.ic_delete), 10);
        }
        mParticleManager.start(RAIN, true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mParticleManager.cleanAnimation(mParticleManager.getParticleView(RAIN));
            }
        }, 10000);
    }

    public void stopAnimation() {
        mParticleManager.cleanAnimations();
    }

}
