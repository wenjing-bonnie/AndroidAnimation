package com.android.animation.point;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

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
    ;
    Paint linePaint;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        setBackgroundColor(Color.LTGRAY);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDisplayWidth();
        int height = getDisplayHeight();
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawDot(canvas, cx, cy);
    }

    private void drawLine(Canvas canvas) {
        int lineHeight = getMeasuredHeight() / 2;
        canvas.drawLine(0, lineHeight, getMeasuredWidth(), lineHeight, linePaint);
        canvas.drawLine(0, lineHeight - 50, 0, lineHeight + 50, linePaint);
    }

    private void drawDot(Canvas canvas, float cx, float cy) {
        canvas.drawCircle(cx, cy, 30, paint);
    }

    private void startAnimation() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //Log.d("PointView", "width = " + width + " , height = " + height);
        //在屏幕的中间位置让点按照正弦曲线移动
        Point start = new Point(0, height / 2);
        Point end = new Point(width, height / 2);
        Point middle = new Point(width / 2, height / 2);
        ValueAnimator dotAnim = ValueAnimator.ofObject(new PointEvaluator(), start, end, middle);
        //animator.setDuration(3000);
        //animator.start();
        dotAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                cx = point.getX();
                cy = point.getY();
                //Log.d("PointView", "width cx = " + cx + " , cy = " + cy);
                invalidate();
            }
        });

        //坐标轴的颜色属性动画
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(paint, "color", Color.RED, Color.GREEN);
        //colorAnimator.setDuration(3000);
        ObjectAnimator lineColorAnimator = ObjectAnimator.ofArgb(linePaint, "color", Color.BLUE, Color.RED);

        //所有的动画
        AnimatorSet set = new AnimatorSet();
        set.setDuration(7000);
        //set.play(dotAnim).with(colorAnimator).with(lineColorAnimator);
        set.playTogether(dotAnim, colorAnimator, lineColorAnimator);
        //set.setInterpolator(new LinearInterpolator());
        set.start();

    }

    private int getDisplayHeight() {
        // return getMeasuredHeight();
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    private int getDisplayWidth() {
        //  return getMeasuredWidth();
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }
}
