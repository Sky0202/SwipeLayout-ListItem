package com.sky.swipelayoutdemo;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 作者：SKY
 * 创建时间：2016-9-11 17:01
 * 描述：自定义的布局类
 */
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper helper;
    private View leftView;
    private View rightView;
    private int leftWidth;
    private int rightWidth;
    private int leftHeight;
    private TextView mPutTop;
    private TextView mDelete;

    public SwipeLayout (Context context) {
        this(context, null);
    }

    public SwipeLayout (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init () {

        helper = ViewDragHelper.create(this, callBack);
        initView();
    }

    private void initView () {
        mPutTop = (TextView) findViewById(R.id.putTop);
        mDelete = (TextView) findViewById(R.id.tv_delete);

    }

    private ViewDragHelper.Callback callBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView (View child, int pointerId) {
            return true;
        }

        // 只允许水平滑动
        @Override
        public int clampViewPositionHorizontal (View child, int left, int dx) {
            // 对滑动越界的处理
            if (child == leftView) {
                if (left > 0) {
                    left = 0;
                } else if (left < -rightWidth) {
                    left = -rightWidth;
                }
            } else {
                if (left < leftWidth - rightWidth) {
                    left = leftWidth - rightWidth;
                } else if (left > leftWidth) {
                    left = leftWidth;
                }
            }
            return left;
        }

        // 处理连带滑动
        @Override
        public void onViewPositionChanged (View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == leftView) {
                rightView.offsetLeftAndRight(dx);
            } else {
                leftView.offsetLeftAndRight(dx);
            }

            // 刷新控件，在此处刷新是为了防止 打开布局时会出现控件未绘制完成的情况
            invalidate();

            // 判断当前是否为打开状态，打开就记录对象
            if (leftView.getLeft() < 0) {
                SwipeLayoutManager manager = SwipeLayoutManager.getInstance();
                manager.setSwipeLayout(SwipeLayout.this);
                requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override
        public void onViewReleased (View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (leftView.getLeft() > -rightWidth * 0.5f) {
                close(true);
            } else {
                open();
            }
        }
    };

    public void open () {
        if (helper.smoothSlideViewTo(leftView, -rightWidth, 0)) {
            invalidate();
        }
    }

    public void close (boolean isSmooth) {
        if (isSmooth) {
            //只有在彻底关闭的时候清除引用
            //设置了滚动的目标点,开始执行
            if (helper.smoothSlideViewTo(leftView, 0, 0)) {
                invalidate();
            }
        } else {
            //将条目瞬间关闭
            leftView.layout(0, 0, leftWidth, leftHeight);
            rightView.layout(leftWidth, 0, leftWidth + rightWidth, leftWidth);
            SwipeLayoutManager.getInstance().clearSwipeLayout();
        }
    }

    @Override
    public void computeScroll () {
        super.computeScroll();
        if (helper.continueSettling(true)) {
            invalidate();
        } else {
            // 当动画执行完关闭后，移除控件的引用
            if (leftView.getLeft() == 0) {
                SwipeLayoutManager.getInstance().clearSwipeLayout();
            }
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 获取当前的 实例，判断与之前的实例是否一致
                SwipeLayout preSwipeLayout = SwipeLayoutManager.getInstance().getSwipeLayout();
                if (preSwipeLayout != null) {
                    if (preSwipeLayout != SwipeLayout.this) {
                        preSwipeLayout.close(true);
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_MOVE:
                preSwipeLayout = SwipeLayoutManager.getInstance().getSwipeLayout();
                if (preSwipeLayout != null) {
                    if (preSwipeLayout != SwipeLayout.this) {
                        requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }
                break;
        }
        helper.processTouchEvent(event);  // 将触摸事件交给 helper 处理
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent (MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev); // 将拦截事件交给 helper 处理
    }

    /**
     * 创建时间：2016-9-11 17:32  描述：获取布局
     */
    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("You must have two children !");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new RuntimeException("Your children must instance of ViewGroup !");
        }
        leftView = getChildAt(0);
        rightView = getChildAt(1);

    }

    /**
     * 创建时间：2016-9-11 17:37  描述：获取孩子布局的宽高
     */
    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        leftWidth = leftView.getMeasuredWidth();
        leftHeight = leftView.getMeasuredHeight();
        rightWidth = rightView.getMeasuredWidth();

    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 将子 View进行排版
        leftView.layout(0, 0, leftWidth, leftHeight);
        rightView.layout(leftWidth, 0, leftWidth + rightWidth, leftHeight);
    }
}
