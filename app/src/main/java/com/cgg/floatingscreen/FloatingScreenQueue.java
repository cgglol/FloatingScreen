package com.cgg.floatingscreen;

import java.util.LinkedList;

public class FloatingScreenQueue {

    private LinkedList list = new LinkedList();

    //销毁队列
    public void clear() {
        list.clear();
    }

    //判断队列是否为空
    public boolean QueueEmpty() {
        return list.isEmpty();
    }

    //进队
    public void enQueue(FloatingScreenBean.TextBean bean) {
        list.addLast(bean);
    }

    //出队
    public void deQueue() {
        if (!list.isEmpty()) {
            list.remove(0);
        }
    }

    //获取队列长度
    public int QueueLength() {
        return list.size();
    }

    //查看队首元素
    public FloatingScreenBean.TextBean QueuePeek() {
        return (FloatingScreenBean.TextBean) list.getFirst();
    }

}
