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
import android.widget.GridLayout;

import androidx.annotation.Nullable;

/**
 * Created by wenjing.liu on 2020/12/24 in J1.
 * 自定义一个可以移动的点
 *
 * @author wenjing.liu
 */
public class PointView extends View {

    private Paint paint;
    private Paint linePaint;
    private float cx;
    private float cy;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化View
     *
     * @param context
     * @param attrs
     */
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

        int width = getMeasureWidth(widthMeasureSpec);
        int height = getMeasureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 获取到控件的width
     *
     * @param widthMeasureSpec
     * @return
     */
    private int getMeasureWidth(int widthMeasureSpec) {
        int width;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                width = getDisplayWidth();
                break;
            default:
                width = size;
        }
        return width;
    }

    /**
     * 获取到控件的height
     *
     * @param heightMeasureSpec
     * @return
     */
    private int getMeasureHeight(int heightMeasureSpec) {
        int height;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                height = getDisplayHeight();
                break;
            default:
                height = size;
        }
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //已经完成控件的放置的时候，启动动画
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawDot(canvas, cx, cy);
    }

    /**
     * 画坐标系
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int lineHeight = getMeasuredHeight() / 2;
        canvas.drawLine(0, lineHeight, getMeasuredWidth(), lineHeight, linePaint);
        canvas.drawLine(0, lineHeight - 50, 0, lineHeight + 50, linePaint);
    }

    /**
     * 画原点
     *
     * @param canvas
     * @param cx
     * @param cy
     */
    private void drawDot(Canvas canvas, float cx, float cy) {
        canvas.drawCircle(cx, cy, 30, paint);
    }

    /**
     * 启动动画
     */
    private void startAnimation() {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //Log.v("PointView", "width = " + width + " , height = " + height);
        //1.在屏幕的中间位置让点按照正弦曲线移动
        Point start = new Point(0, height / 2);
        Point end = new Point(width, height / 2);
        Point middle = new Point(width / 2, height / 2);
        ValueAnimator dotAnim = ValueAnimator.ofObject(new PointEvaluator(), start, end, middle);
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

        //2.坐标轴的颜色属性动画
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(paint, "color", Color.RED, Color.GREEN);
        ObjectAnimator lineColorAnimator = ObjectAnimator.ofArgb(linePaint, "color", Color.BLUE, Color.RED);

        //3. 多个动画一起播放：
        AnimatorSet set = new AnimatorSet();
        set.setDuration(7000);
        //set.setInterpolator(new LinearInterpolator());
        //(1)方法一
        //set.play(dotAnim).with(colorAnimator).with(lineColorAnimator);
        //set.start();
        //(2)方法二
        set.playTogether(dotAnim, colorAnimator, lineColorAnimator);
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
