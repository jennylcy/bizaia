package com.bizaia.zhongyin.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by yan on 2017/3/11.
 */

public class AnimationGroupManager {
    private View target;
    private AnimationUtils.AnimationType animationType;
    private float[] animationValues;
    private float[] animationReversalValues;
    private long animationDuring;
    private float[] animationCurrentValue;
    private ValueAnimator goAnimation;
    private ValueAnimator backAnimation;
    private Interpolator interpolator;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private AnimatorListenerAdapter listenerAdapter;
    private boolean isRestart = false;

    public AnimationGroupManager(View target, long animationDuring, AnimationUtils.AnimationType animationType, AnimatorListenerAdapter listenerAdapter, Interpolator interpolator, float... animationValues) {
        this.target = target;
        this.animationDuring = animationDuring;
        this.animationValues = animationValues;
        this.animationType = animationType;
        this.interpolator = interpolator;
        this.listenerAdapter = listenerAdapter;
        this.animationCurrentValue = new float[]{-1};
        this.animationReversalValues = new float[this.animationValues.length];
        for (int i = 0; i < this.animationValues.length; i++) {
            animationReversalValues[i] = this.animationValues[this.animationValues.length - i - 1];
        }
    }

    public AnimationGroupManager(long animationDuring, AnimatorListenerAdapter listenerAdapter, Interpolator interpolator, ValueAnimator.AnimatorUpdateListener animatorUpdateListener, float... animationValues) {
        this(null, animationDuring, null, listenerAdapter, interpolator, animationValues);
        this.animatorUpdateListener = animatorUpdateListener;
    }

    public void show() {
        if (!isRestart && animationCurrentValue[0]
                == animationValues[animationValues.length - 1]
                && animationCurrentValue[0] != -1
                ) {
            return;
        }
        if (target != null && target.getVisibility() != View.VISIBLE)
            target.setVisibility(View.VISIBLE);

        if (backAnimation != null && backAnimation.isRunning()) {
            backAnimation.cancel();
        }
        if (goAnimation == null) {
            if (animatorUpdateListener == null) {
                goAnimation = AnimationUtils.getInstance().createAnimation(
                        target
                        , animationType
                        , animationDuring
                        , interpolator
                        , null
                        , animationCurrentValue
                        , animationValues);
            } else {
                goAnimation = AnimationUtils.getInstance().createAnimation(
                        animationDuring
                        , interpolator
                        , animatorUpdateListener
                        , null
                        , animationCurrentValue
                        , animationValues);
            }
        } else {
            goAnimation.cancel();
            if (!isRestart) {
                goAnimation.setFloatValues(animationCurrentValue[0], animationValues[animationValues.length - 1]);
            } else {
                goAnimation.setFloatValues(animationValues);
            }
        }

        goAnimation.start();
    }


    public void reShow() {
        isRestart = true;
        show();
    }

    public void hide() {
        if (!isRestart && animationCurrentValue[0]
                == animationValues[0]
                && animationCurrentValue[0] != -1
                ) {
            return;
        }
        if (goAnimation != null && goAnimation.isRunning()) {
            goAnimation.cancel();
        }
        if (backAnimation == null) {
            if (animatorUpdateListener == null) {
                backAnimation = AnimationUtils.getInstance().createAnimation(
                        target
                        , animationType
                        , animationDuring
                        , interpolator
                        , new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (animationCurrentValue[0]
                                        == animationValues[0]) {
                                    if (listenerAdapter != null)
                                        listenerAdapter.onAnimationEnd(backAnimation);
                                }
                            }
                        }
                        , animationCurrentValue
                        , animationReversalValues);
            } else {
                backAnimation = AnimationUtils.getInstance().createAnimation(
                        animationDuring
                        , interpolator
                        , animatorUpdateListener
                        , new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (animationCurrentValue[0]
                                        == animationValues[0]) {
                                    if (listenerAdapter != null)
                                        listenerAdapter.onAnimationEnd(backAnimation);
                                }
                            }
                        }
                        , animationCurrentValue
                        , animationReversalValues);
            }
        } else {
            backAnimation.cancel();
            if (!isRestart) {
                backAnimation.setFloatValues(animationCurrentValue[0], animationValues[0]);
            } else {
                backAnimation.setFloatValues(animationReversalValues);
            }
        }
        backAnimation.start();
    }


    public void reHide() {
        isRestart = true;
        hide();
    }
}
