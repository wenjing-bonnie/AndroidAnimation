package com.android.animation.point;

/**
 * Created by wenjing.liu on 2020/12/24 in J1.
 * 定义一个对象，里面含有一个x和y的坐标
 *
 * @author wenjing.liu
 */
public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
