package com.android.animation.point;

import android.animation.TypeEvaluator;

/**
 * Created by wenjing.liu on 2020/12/24 in J1.
 *
 * @author wenjing.liu
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point start = (Point) startValue;
        Point end = (Point) endValue;
        float x = start.getX() + fraction * (end.getX() - start.getX());
        float y = start.getY() + fraction * (end.getY() - start.getY());
        Point result = new Point(x, y);
        return result;
    }
}
