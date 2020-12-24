package com.android.animation.point;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by wenjing.liu on 2020/12/24 in J1.
 * 自定义一个可以移动的点
 *
 * @author wenjing.liu
 */
public class PointView extends View {

    Paint paint;
    float cx;
    float cy;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        startAnimation();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDot(canvas, cx, cy);
    }

    private void drawDot(Canvas canvas, float cx, float cy) {
        canvas.drawCircle(cx, cy, 30, paint);
    }

    private void startAnimation() {
        Point start = new Point(0, 0);
        Point end = new Point(getDisplayWidth(), getDisplayHeight());
        Point middle = new Point(end.getX() / 2, end.getY() / 2);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), start, end, middle);
        //animator.setDuration(3000);
        //animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                cx = point.getX();
                cy = point.getY();
                invalidate();
            }
        });

        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(paint, "color", Color.RED, Color.GREEN);
        //colorAnimator.setDuration(3000);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(7000);
        set.play(animator).with(colorAnimator);
        set.start();

    }

    private int getDisplayHeight() {
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    private int getDisplayWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }
}
