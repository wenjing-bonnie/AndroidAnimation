package com.android.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.animation.point.Point;
import com.android.animation.point.PointActivity;
import com.android.animation.point.PointEvaluator;

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

    public void xmlObjectAnimator(View view) {
        xmlAnimator(tvAnimator, R.animator.anim__object);
    }

    public void xmlSetAnimator(View view) {
        xmlAnimator(tvAnimator, R.animator.anim_set);
    }


    public void valueTypeEvaluator(View view) {
        Point start = new Point(0, 0);
        Point end = new Point(300, 300);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), start, end);
        animator.setDuration(4000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                tvAnimator.setText(String.format("The Point Type Evaluator x = %.2f y = %.2f", point.getX(), point.getY()));
            }
        });
    }

    public void goPointActivity(View view) {
        Intent intent = new Intent(MainActivity.this, PointActivity.class);
        startActivity(intent);
    }

    private Animator xmlAnimator(View targetView, int anim) {
        //加载xml文件
        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, anim);
        //将该动画加载到对应的view
        animator.setTarget(targetView);
        animator.start();
        return animator;
    }
}