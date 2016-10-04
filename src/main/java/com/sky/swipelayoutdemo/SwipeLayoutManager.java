package com.sky.swipelayoutdemo;

/**
 * 作者：SKY
 * 创建时间：2016-9-14 20:20
 * 描述：记录状态的单例
 */
public class SwipeLayoutManager {

    private static SwipeLayoutManager manager = new SwipeLayoutManager();

    public static SwipeLayoutManager getInstance () {
        return manager;
    }

    public SwipeLayout getSwipeLayout () {
        return swipeLayout;
    }

    public void setSwipeLayout (SwipeLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }

    // 用于存储自定义控件的引用
    private SwipeLayout swipeLayout;

    // 将记录的 swipeLayout清空
    public void clearSwipeLayout(){
        swipeLayout = null;
    }

}
