package com.tq.fruitviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by boobooL on 2016/4/19 0019
 * Created 邮箱 ：boobooMX@163.com
 */
public class FruitView extends FrameLayout {
    private final int[] mResDrawable = {
            R.mipmap.p1,
            R.mipmap.p3,
            R.mipmap.p5,
            R.mipmap.p7 ,
            R.mipmap.p2,
            R.mipmap.p4,
            R.mipmap.p6,
            R.mipmap.p8};
    private int mIndex;//当前图片的下标
    private boolean mSkip = true;
    private int MAX_HEIGHT = 100;
    private int DURATION = 600;
    private OnViewAnimEndListener mOnViewAnimEndListener;
    RotateAnimation mRotateAnimation;
    TranslateAnimation mTranslateAnimation, mBackAnimation;
   AnimationSet mAnimatorSet, mAnimatorSet2;
    private Context mContext;
    private ImageView mImageView;
    private CurveHeadLoadingView mCurveHeadLoadingView;

    public FruitView(Context context) {
        super(context);
        init(context);
    }

    public FruitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FruitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_fruit_loading, this);
        this.mContext = context;
        mImageView = (ImageView) view.findViewById(R.id.view_fruit_image);
        mImageView.setImageResource(mResDrawable[1]);
        mCurveHeadLoadingView = (CurveHeadLoadingView) view.findViewById(R.id.curheadloadingview);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, MAX_HEIGHT, 0, 0);
        mCurveHeadLoadingView.setLayoutParams(layoutParams);

    }

    public void setOnViewAnimEndListener(OnViewAnimEndListener onViewAnimEndListener) {
        mOnViewAnimEndListener = onViewAnimEndListener;
    }

    public void changeIcon() {
        mImageView.clearAnimation();
        if (mSkip) {
            mIndex = 2;
            mSkip = false;
        } else
            mIndex = (mIndex == mResDrawable.length - 1) ? 0 : mIndex + 1;
        mImageView.setImageResource(mResDrawable[mIndex]);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (MAX_HEIGHT + mImageView.getMeasuredHeight() + mCurveHeadLoadingView.getMeasuredHeight()));
        loadAnim();
    }

    private void loadAnim() {
        mTranslateAnimation = new TranslateAnimation(0, 0, 0, MAX_HEIGHT);
        mTranslateAnimation.setDuration(DURATION);
        mTranslateAnimation.setFillAfter(true);

        mBackAnimation = new TranslateAnimation(0, 0, MAX_HEIGHT, 0);
        mBackAnimation.setDuration(DURATION);
        mBackAnimation.setFillAfter(true);

        mRotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF,
                0.5f,//0.5 ==1/2的自己父控件的长度
                Animation.RELATIVE_TO_SELF,
                0.5f);//0.5==1/2的自己父控件的长度
        mRotateAnimation.setRepeatCount(0);
        mRotateAnimation.setDuration(DURATION);



        //动画的集合
        mAnimatorSet=new AnimationSet(true);
        mAnimatorSet.addAnimation(mRotateAnimation);
        mAnimatorSet.addAnimation(mTranslateAnimation);

        mAnimatorSet2=new AnimationSet(true);
        mAnimatorSet2.addAnimation(mRotateAnimation);
        mAnimatorSet2.addAnimation(mBackAnimation);

        mTranslateAnimation.setAnimationListener(animationListener);
        mBackAnimation.setAnimationListener(animationListener);
        mImageView.setAnimation(mAnimatorSet);
        mAnimatorSet2.setInterpolator(mContext,android.R.anim.decelerate_interpolator);
        mAnimatorSet.setInterpolator(mContext,android.R.anim.accelerate_interpolator);
        mAnimatorSet.start();

    }

    Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            if(animation==mTranslateAnimation){
                mCurveHeadLoadingView.startAnim();
                changeIcon();
                mImageView.setAnimation(mAnimatorSet2);
                mAnimatorSet2.start();
            }else{
                mImageView.setAnimation(mAnimatorSet);
                mAnimatorSet.start();
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public interface OnViewAnimEndListener {
        public void onDropDown();
    }
}
