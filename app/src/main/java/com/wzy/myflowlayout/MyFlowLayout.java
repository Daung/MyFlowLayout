package com.wzy.myflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyFlowLayout extends ViewGroup {

    private static final int Horizontal_space = dp2px(16);
    private static final int Vertical_space = dp2px(6);

    private List<List<View>> lineViews = new ArrayList<>();
    private List<Integer> heights = new ArrayList<>();

    private static final String TAG = "MyFlowLayout";

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static int dp2px(float dipValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: 调用");
        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();
        for (int i = 0; i < heights.size(); i++) {
            int rowHeight = heights.get(i);
            List<View> rowViews = lineViews.get(i);
            for (View rowView : rowViews) {
                MarginLayoutParams mlP = (MarginLayoutParams) rowView.getLayoutParams();
                int left = parentPaddingLeft + mlP.leftMargin;
                int top = parentPaddingTop + mlP.topMargin;
                int right = rowView.getMeasuredWidth() + left;
                int bottom = top + rowView.getMeasuredHeight();
                rowView.layout(left, top, right, bottom);
                parentPaddingLeft = right + Horizontal_space;
            }
            parentPaddingLeft = getPaddingLeft();
            parentPaddingTop += rowHeight;

        }
    }

    //测量的方法  除了要测量子view，还要测量自己
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        lineViews.clear();
        heights.clear();
        //获取当前view的宽
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        //获取当前view的高
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "onMeasure: 当前view的宽 = " + selfWidth + " 当前view的高 = " + selfHeight);


        int count = getChildCount();
        Log.d(TAG, "onMeasure: 当前view的子view数量为 " + count);


        int leftPadding = getPaddingLeft();
        int topPadding = getPaddingTop();
        int rightPadding = getPaddingRight();
        int bottomPadding = getPaddingBottom();

        Log.d(TAG, "onMeasure: 当前view的内边距 leftPadding = " + leftPadding +
                " rightPadding = " + rightPadding + " topPadding = " + topPadding +
                " bottomPadding = " + bottomPadding);
        Log.d(TAG, "onMeasure: ======================================================");
        int useWidth = 0;
        int rowHeight = 0;
        int parentNeedWidth = 0;
        int parentNeedHeight = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        List<View> rowViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);

            // int childWidthMode = getChildMeasureSpec(widthMeasureSpec, leftPadding + rightPadding, mlp.width);
            // int childHeightMode = getChildMeasureSpec(heightMeasureSpec, topPadding + bottomPadding, mlp.height);
            // childView.measure(childWidthMode, childHeightMode);

            // measureChild 方法就是测量子view，就是上述代码的实现
            this.measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = childView.getMeasuredWidth();
            int measureHeight = childView.getMeasuredHeight();


            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            int leftMargin = mlp.leftMargin;
            int rightMargin = mlp.rightMargin;
            int topMargin = mlp.topMargin;
            int bottomMargin = mlp.bottomMargin;
            Log.d(TAG, "onMeasure:" + "第" + i + "个子view的外边距 leftMargin = " + leftMargin +
                    " rightMargin = " + rightMargin + " topMargin = " + topMargin +
                    " bottomPadding = " + bottomMargin);


            Log.d(TAG, "onMeasure: " + "第" + i + "个子view的 measureWidth = "
                    + measuredWidth + " measureHeight = " + measureHeight);

            if (useWidth + measuredWidth >= selfWidth) {
                rowHeight += Vertical_space;
                lineViews.add(rowViews);
                heights.add(rowHeight);
                parentNeedHeight += rowHeight;
                parentNeedWidth = Math.max(parentNeedWidth, useWidth);

                //数据全部初始化
                rowViews = new ArrayList<>();
                useWidth = 0;
                rowHeight = 0;
            }
            rowViews.add(childView);
            useWidth += measuredWidth + Horizontal_space;
            rowHeight = Math.max(rowHeight, measureHeight + topMargin + bottomMargin);
            if (i == count - 1) {
                parentNeedHeight += rowHeight + Vertical_space;
                parentNeedWidth = Math.max(parentNeedWidth, useWidth);
                lineViews.add(rowViews);
                heights.add(rowHeight);
            }
        }
        int realWidth = MeasureSpec.EXACTLY == widthMode ? selfWidth : parentNeedWidth;
        int realHeight = MeasureSpec.EXACTLY == heightMode ? selfHeight : parentNeedHeight;
        Log.d("1111", "onLayout: realWidth = " + realWidth +
                " realHeight = " + realHeight);
        setMeasuredDimension(realWidth, realHeight);

        Log.d(TAG, "onMeasure: ======================================================");


    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (params instanceof MarginLayoutParams) {
            super.addView(child, params);
        } else {
            super.addView(child, new MarginLayoutParams(params));
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        Log.d("22222222", "generateLayoutParams: 调用");
        return new MarginLayoutParams(getContext(), attrs);
    }
}
