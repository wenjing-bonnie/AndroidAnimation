package com.android.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    private TextView tvAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAnimator = findViewById(R.id.tv_animator);
    }

    public void valueAnimator(View view) {

        ValueAnimator animator = ValueAnimator.ofInt(1, 20);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.d(TAG, "value = " + value);
                tvAnimator.setText("第一个按钮对应的动画的value的变化为：" + value);
            }
        });
        //animator.setDuration(1000);
        //animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();

    }

    public void objectAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvAnimator, "alpha", 1f, 0f, 1f);
        animator.setDuration(2000);
        animator.start();
    }

    public void objectAnimatorBackground(View view) {
        ObjectAnimator animator = ObjectAnimator.ofArgb(tvAnimator, "backgroundColor", Color.RED, Color.YELLOW);
        animator.setDuration(3000);
        animator.start();

    }


    public void objectAnimatorSet(View view) {

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(tvAnimator, "alpha", 1.0f, 0.0f, 1.0f);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(tvAnimator, "translationX", -500f, 0);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(tvAnimator, "rotation", 0, 360);

        AnimatorSet set = new AnimatorSet();
        set.play(alphaAnim).before(transXAnim).after(rotateAnim);
        set.setDuration(5000);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
    }

    public void xmlValueAnimator(View view) {
        Animator animator = xmlAnimator(view, R.animator.anim__value);
        if (animator instanceof ValueAnimator) {
            ((ValueAnimator) animator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d(TAG, "value = " + animation.getAnimatedValue());
                    tvAnimator.setText("第5个按钮对应的动画的value的变化为：" + animation.getAnimatedValue().toString());
                }
            });
        }
    }


    private Animator xmlAnimator(View targetView, int anim) {
        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, anim);
        animator.setTarget(targetView);
        animator.start();
        return animator;
    }
}