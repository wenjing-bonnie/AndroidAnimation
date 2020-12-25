package com.android.animation.point;

import android.animation.TypeEvaluator;

/**
 * Created by wenjing.liu on 2020/12/24 in J1.
 * 自定义的TypeEvaluator用来实现Point从起始位置过渡到终点位置
 *
 * @author wenjing.liu
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point start = (Point) startValue;
        Point end = (Point) endValue;
        float x = start.getX() + fraction * (end.getX() - start.getX());
        // float y = start.getY() + fraction * (end.getY() - start.getY());
        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + end.getY();
        Point result = new Point(x, y);
        return result;
    }
}
