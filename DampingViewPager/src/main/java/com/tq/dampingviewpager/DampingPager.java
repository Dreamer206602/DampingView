package com.tq.dampingviewpager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;

/**
 * Created by boobooL on 2016/4/20 0020
 * Created 邮箱 ：boobooMX@163.com
 */
public class DampingPager extends ViewPager {
    private Rect mRect = new Rect();
    private int pagerCount = 3;
    private int currentItem = 0;
    private boolean handleDefault = true;
    private float preX = 0f;
    private float firstX = 0f;
    private float RATIO = 0.8f;// 摩擦系数
    private float limit = 0f;//这是设置当手指滑动超过多少个ViewPager才开始滑动，默认是0

    public DampingPager(Context context) {
        super(context);
    }

    public DampingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setPagerCount(int pagerCount){
        this.pagerCount=pagerCount;
    }

    //在onPageSelect方法中调用它
    public void setCurrentIndex(int currentItem) {
        this.currentItem = currentItem;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            preX = ev.getX();//记录起点
            firstX = ev.getX();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                onTouchActionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                //当时滑到第一项或者是最后 一项的时候
                if ((currentItem == 0 || currentItem == pagerCount - 1)) {
                    float nowX = ev.getX();
                    float offset = nowX - preX;
                    RATIO = (float) (0.8f - (Math.abs(nowX - firstX)) * 0.001);
                    RATIO = RATIO < 0 ? 0 : RATIO;
                    preX = nowX;
                    if (currentItem == 0) {
                        if (offset > limit) {
                            //手指滑动的距离大于设定值
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {
                            //往回滑动的时候
                            if (getLeft() + (int) (offset * RATIO) >= mRect.left) {
                                layout(getLeft() + (int) (offset * RATIO),
                                        getTop(),
                                        getRight() + (int) (offset * RATIO), getBottom());
                            }
                        }
                    } else {
                        if (offset < -limit) {
                            whetherConditionIsRight(offset);
                        } else if (!handleDefault) {
                            if (getRight() + (int) (offset * RATIO) <= mRect.right) {
                                layout(getLeft() + (int) (offset * RATIO), getTop(), getRight() + (int) (offset * RATIO), getBottom());
                            }
                        }
                    }
                } else {
                    handleDefault = true;
                }
                if (!handleDefault) {
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void whetherConditionIsRight(float offset) {
        if (mRect.isEmpty()) {
            mRect.set(getLeft(), getTop(), getRight(), getBottom());
        }
        handleDefault = false;
        layout(getLeft() + (int) (offset * RATIO),
                getTop(), getRight() + (int) (offset * RATIO), getBottom());
    }

    private void onTouchActionUp() {
        RATIO = 0.8f;
        if (!mRect.isEmpty()) {
            recoveryPosition();
        }

    }

    private void recoveryPosition() {
        TranslateAnimation ta = new TranslateAnimation(
                getLeft(), mRect.left, 0, 0);
        int recoveryTime = (int) Math.abs(getLeft() * 1.4) + 50;
        ta.setDuration(recoveryTime);
        startAnimation(ta);
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        handleDefault = true;

    }
}
