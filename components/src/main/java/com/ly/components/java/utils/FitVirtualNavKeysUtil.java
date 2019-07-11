package com.ly.components.java.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * 适配底部虚拟导航栏
 * 需要适配的页面调用assistActivity方法
 */

public class FitVirtualNavKeysUtil {
    /**
     *
     *
     * @param viewObserving 关联要监听的视图 , 通常为页面的父布局
     */

    public static void assistActivity(View viewObserving) {
        new FitVirtualNavKeysUtil(viewObserving);
    }

    private View mViewObserved;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private ViewGroup.LayoutParams frameLayoutParams;


    private FitVirtualNavKeysUtil(View viewObserving) {

        mViewObserved = viewObserving;

//给View添加全局的布局监听器

        mViewObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            public void onGlobalLayout() {
                    resetLayoutByUsableHeight(computeUsableHeight());

            }

        });

        frameLayoutParams = mViewObserved.getLayoutParams();

    }


    private void resetLayoutByUsableHeight(int usableHeightNow) {

//比较布局变化前后的View的可用高度

        if (usableHeightNow != usableHeightPrevious) {

//如果两次高度不一致

//将当前的View的可用高度设置成View的实际高度

            frameLayoutParams.height = usableHeightNow;

            mViewObserved.requestLayout();//请求重新布局

            usableHeightPrevious = usableHeightNow;
        }

    }



    /**
     *  * 计算视图可视高度
     * <p>
     *  *
     * <p>
     *  *@return
     * <p>
     *  
     */

    private int computeUsableHeight() {

        Rect r = new Rect();

        mViewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom );

    }

}
