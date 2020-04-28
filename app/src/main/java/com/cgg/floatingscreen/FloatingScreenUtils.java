package com.cgg.floatingscreen;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FloatingScreenUtils {

    //TAG 日志标识
    String TAG = this.getClass().getSimpleName();
    //单列
    private static volatile FloatingScreenUtils bulletChatUtils = null;

    //初始化
    public FloatingScreenUtils getInstance() {
        if (bulletChatUtils == null) {
            synchronized (FloatingScreenUtils.class) {
                if (bulletChatUtils == null) {
                    bulletChatUtils = new FloatingScreenUtils();
                }
            }
        }
        return bulletChatUtils;
    }

    //用于存储和删除飘屏消息的队列
    private FloatingScreenQueue queue = new FloatingScreenQueue();
    //飘屏动画
    private ObjectAnimator animator;
    //是否在播放动画  默认未播放
    private boolean isStart = false;
    //是否在停止显示  默认不是
    private boolean isTemp = false;
    //是否隐藏飘屏 默认显示
    private boolean isShow = true;
    //是否第一段动画被Cancel掉 默认不是
    private boolean isHide = false;
    //飘屏背景图片
    private ImageView iv_background;
    //飘屏左边图片
    private ImageView iv_left;
    //飘屏礼物图片
    private ImageView iv_gift;
    //飘屏文字
    private TextView tv_bulletchat;
    //飘屏礼物数量
    private TextView tv_number;
    //飘屏布局
    private RelativeLayout relativeLayout;
    //动画插值器  进入   先加速后减速
    private SqrtInterpolator mGetIntoInterpolator = new SqrtInterpolator();
    //动画插值器  出去   加速离开
    private IndexInterpolator mLeaveInterpolator = new IndexInterpolator();
    //屏幕宽度
    private int screenWidth;
    //动画进场时间
    private int inTime;
    //动画出场时间
    private int outTime;
    //动画停留时间
    private int showTime;

//    WeakReference<Handler> cacheRef = new WeakReference<>();

    //飘屏进屏幕动画
    private void showFirst(FloatingScreenBean.TextBean bean) {

        if (!StringUtil.isEmpty(bean.getImg())) {
            iv_background.setBackground(iv_background.getContext().getResources().getDrawable(R.mipmap.im_gift_bj));
        }

        if (!StringUtil.isEmpty(bean.getIcon_img())) {
            iv_left.setBackground(iv_left.getContext().getResources().getDrawable(R.mipmap.im_gift));
        }
        tv_bulletchat.setText(Html.fromHtml(bean.getTitle()));

        //type  1、4 布局不一样   可以自行模拟
        if ("1".equals(queue.QueuePeek().getType()) || "4".equals(queue.QueuePeek().getType())) {
            iv_gift.setVisibility(View.VISIBLE);
            tv_number.setVisibility(View.VISIBLE);
            if (!StringUtil.isEmpty(bean.getGift_img())) {
                iv_gift.setBackground(iv_gift.getContext().getResources().getDrawable(R.mipmap.gift));
            }
            tv_number.setText(" x " + bean.getGift_num());
        }

        if (!StringUtil.isEmpty(bean.getShow_time())) {
            showTime = (int) (Double.valueOf(bean.getShow_time()) * 1000);
        }
        if (!StringUtil.isEmpty(bean.getIn_time())) {
            inTime = (int) (Double.valueOf(bean.getIn_time()) * 1000);
        }
        if (!StringUtil.isEmpty(bean.getOut_time())) {
            outTime = (int) (Double.valueOf(bean.getOut_time()) * 1000);
        }

        //获取屏幕宽度
        screenWidth = ScreenUtils.getScreenWidth(relativeLayout.getContext());

        animator = ObjectAnimator.ofFloat(relativeLayout, "translationX", screenWidth, 0);
        animator.setDuration(inTime);
        animator.setInterpolator(mGetIntoInterpolator);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                isHide = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 这里就是动画1结束  暂停1s
                isStart = false;
                isTemp = true;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(showTime);
                            mHandler.sendEmptyMessage(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isHide = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(4);
            }
        });
        if (isShow) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
        animator.start();
    }

    //飘屏出屏幕动画
    private void showSecond() {

        animator = ObjectAnimator.ofFloat(relativeLayout, "translationX", 0, -screenWidth);
        animator.setDuration(outTime);
        animator.setInterpolator(mLeaveInterpolator);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStart = true;
                isTemp = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 这里就是动画结束   开始下一个动画
                isStart = false;
                if (null != relativeLayout) {
                    relativeLayout.setVisibility(View.GONE);
                }
                if (queue.QueueLength() > 0) {
                    if ("1".equals(queue.QueuePeek().getType()) || "4".equals(queue.QueuePeek().getType())) {
                        if (null != relativeLayout && relativeLayout.getChildCount() > 0) {
                            iv_gift.setVisibility(View.GONE);
                            tv_number.setVisibility(View.GONE);
                        }
                    }
                    queue.deQueue();
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(showTime);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(4);
            }
        });

        if (isShow) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
        animator.start();
    }

    //添加飘屏消息 并且看是否需要播放动画
    public void add(FloatingScreenBean.TextBean bean) {
        queue.enQueue(bean);
        //判断动画是否在执行
        mHandler.sendEmptyMessage(1);
    }

    //初始化飘屏 并且看是否需要播放动画
    public void initBulletChat(RelativeLayout relLayout) {
        Log.e(TAG, " initBulletChatUtils ");
        relativeLayout = relLayout;
        initChildView();
        //判断动画是否在执行
        mHandler.sendEmptyMessage(2);
    }

    //初始化布局findViewById
    private void initChildView() {
        iv_background = relativeLayout.findViewById(R.id.iv_background);
        iv_left = relativeLayout.findViewById(R.id.iv_left);
        tv_bulletchat = relativeLayout.findViewById(R.id.tv_bulletchat);
        iv_gift = relativeLayout.findViewById(R.id.iv_gift);
        tv_number = relativeLayout.findViewById(R.id.tv_number);
    }

    //释放资源 参数初始化
    public void destroy(boolean isClear) {
        Log.e(TAG, " destroy initBulletChatUtils ");
        if (null != animator) {
            animator.pause();
            animator.cancel();
            animator = null;
            relativeLayout.clearAnimation();
            relativeLayout.setVisibility(View.GONE);
        }
        isTemp = false;
        isStart = false;
        isShow = true;
        relativeLayout = null;
        if (queue.QueueLength() > 0) {
            queue.deQueue();
        }
        if (isClear) {
            queue.clear();
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //播放进屏幕动画
                    if (!isStart && queue.QueueLength() > 0 && null != relativeLayout && !isTemp) {
                        showFirst(queue.QueuePeek());
                    }
                    break;
                case 2:
                    //延迟一下，确保直播间已经加载完成
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(2000);
                                mHandler.sendEmptyMessage(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                case 3:
                    //播放出屏幕动画
                    if (!isStart && queue.QueueLength() > 0 && null != relativeLayout && !isHide) {
                        showSecond();
                    }
                    break;
                case 4:
                    //点击了飘屏
                    if (queue.QueueLength() > 0) {
                        //进行对应的操作
                        Log.e(TAG, " 点击了  飘屏 ");
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    //隐藏飘屏
    public void hideBulletChat() {
        if (null != relativeLayout) {
            isShow = false;
            relativeLayout.setVisibility(View.GONE);
        }
    }

    //显示飘屏
    public void showBulletChat() {
        if (null != relativeLayout) {
            isShow = true;
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    //进入插值器
    public class SqrtInterpolator extends LinearInterpolator {

        @Override
        public float getInterpolation(float input) {
            return (float) Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(input)))));
        }
    }

    //出去插值器
    public class IndexInterpolator extends LinearInterpolator {
        @Override
        public float getInterpolation(float input) {
            return input * input * input * input * input * input * input;
        }
    }


}
