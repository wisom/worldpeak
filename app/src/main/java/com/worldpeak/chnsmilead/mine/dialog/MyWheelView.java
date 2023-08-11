package com.worldpeak.chnsmilead.mine.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CrazyFlower on 2018/4/2.
 */

public class MyWheelView extends View {

    private static final String TAG = "MyWheelView";

    private List<String> data;

    //lastY是上次OnTouchEvent的Y值,scrollY这次Action_down到Action_up过程中拉动的距离模itemHeight的结果
    private float lastY = 0;
    private float scrollY = 0;

    private int viewWidth = 0;
    private int viewHeight = 0;
    private int itemHeight = 0;

    //是否在触摸,暂时看来没用
    private boolean isTouch = false;

    //当前选中项在data中的下标
    private int currentItemIndex = 0;

    Paint linePaint;
    Paint textPaint;

    //用来处理回弹的,这部分可能不正确
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

    private Timer timer = new Timer();

    public MyWheelView(Context context) {
        this(context, null);
    }

    public MyWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public MyWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDataAndPaint();
    }

    private void initDataAndPaint() {
        Log.d(TAG, "initData: ");
        data = new ArrayList<>();
        currentItemIndex = 0;
        linePaint = new Paint();
        textPaint = new Paint();
        linePaint.setColor(0xffF5F7FB);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测定View的宽高,这个看个人吧,我这里部分抄了https://blog.csdn.net/junzia/article/details/50979382
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                width = 100;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = 100;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                height = 100;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = 100;
                break;
        }

        viewWidth = width;
        viewHeight = height;
        itemHeight = height / 5;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == data) {
            return;
        }

        String text = "";
        float topY = 0;

        canvas.drawRect(new RectF(0, itemHeight * 2, viewWidth, itemHeight * 3), linePaint);

        //画出每个item项,不论是否能被看到
        for (int i = 0, length = data.size(); i < length; i++) {
            //计算item的最上面的Y值
            topY = (i - currentItemIndex + 2) * itemHeight + scrollY;
            text = data.get(i);
            setTextPaint(topY - viewHeight * 2 / 5);
            canvas.drawText(text, getMidText(textPaint, text, viewWidth), getBaseLine(textPaint, topY, itemHeight), textPaint);
        }
        //画出中间项上下两端的线
//        for (int count = 2; count < 4; count++) {
//            canvas.drawLine(0, itemHeight * count, viewWidth, itemHeight * count, linePaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                lastY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                scrollY = scrollY + event.getY() - lastY;
                lastY = event.getY();
                confirmCurrentItem(1);
                break;
            case MotionEvent.ACTION_UP:
                scrollY = scrollY + event.getY() - lastY;
                lastY = 0;
                isTouch = false;
                confirmCurrentItem(2);
                break;
        }
        return true;
    }


    /**
     * @return 该字串在width下 中间对齐中间的x
     */
    private float getMidText(Paint paint, String text, float width) {
        float fontWidth = paint.measureText(text);
        return (width - fontWidth) / 2;
    }

    /**
     * @return 该字串在itemheight下 中间对齐中间的y 该函数copy by https://blog.csdn.net/junzia/article/details/50979382
     */
    private float getBaseLine(Paint paint, float top, float height) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return (2 * top + height - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    //判断当前最中间的是哪个项,同时发出消息去刷新画面
    private void confirmCurrentItem(int level) {
        timer.cancel();
        float topY = 0;
        float min = 0x10000000;
        int index = 0;
        //计算哪个项离中间最近
        for (int i = 0, length = data.size(); i < length; i++) {
            topY = (i - currentItemIndex + 2) * itemHeight + scrollY;
            if (Math.abs((topY + itemHeight / 2) - viewHeight / 2) < Math.abs(min)) {
                min = (topY + itemHeight / 2) - viewHeight / 2;
                index = i;
            }
        }
        scrollY -= itemHeight * (currentItemIndex - index);
        currentItemIndex = index;
        //如果是Action_move,可以直接退出,不用处理回弹动作
        if (level == 1) {
            invalidate();
            return;
        }

        //下面一段代码是用来处理放手后的最靠近的项回弹到中间的效果.效果是对的,但有关定时的处理可能不正确
        final float finalMin = min;
        TimerTask task = new TimerTask() {
            int times = 0;
            float reduceDistance = finalMin / 5;

            @Override
            public void run() {
                times++;
                if (times == 6) {
                    scrollY = 0;
                    super.cancel();
                    return;
                }
                scrollY -= reduceDistance;
                Log.d(TAG, "run: " + times);
                handler.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 60);
    }

    //根据距离view中间的距离设置颜色和字的大小
    private void setTextPaint(float distance) {
        distance = Math.abs(distance);
        int color = (int) (255 - (distance * 62.4 / viewHeight));
        textPaint.setColor(color * 0x1000000);
        float textSize = (float) (0.35 * itemHeight - distance / 10);
        textPaint.setTextSize(textSize);
    }

    public int getCurrentIndex() {
        return currentItemIndex;
    }

    public String getCurrentText() {
        return data.get(currentItemIndex);
    }

    public void addData(String text) {
        if (null == data)
            data = new ArrayList<>();
        data.add(text);
        invalidate();
    }

    public void setData(List<String> data,int curIndex) {
        this.data = data;
        this.currentItemIndex=curIndex;
        invalidate();
    }
}
