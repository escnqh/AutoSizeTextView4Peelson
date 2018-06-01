package com.meitu.qihangni.autosizetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;


/**
 * @author nqh 2018/5/31
 */
public class AutoSizeTextView extends AppCompatTextView {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private Paint mPaint;
    private float mTextViewWidth;
    private float mTextViewHeight;
    private float mPreTextSize;
    private String mPreText = "";
    private float mRightTextSize;


    public AutoSizeTextView(Context context) {
        super(context);
        initPaint();
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mPreText = text.toString();
        super.setText(text, type);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.set(this.getPaint());
        Log.i(TAG, "mPaint inited!");
    }

    @Override
    public void setTextSize(float size) {
        mPreTextSize = size;
        Log.i(TAG, "setTextSize");
        super.setTextSize(size);
    }

    /**
     * 触发条件之一，当布局尺寸变化的时候
     *
     * @param w    新的宽度
     * @param h    新的高度
     * @param oldw 旧的宽度
     * @param oldh 旧的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw)
            getRightTextSize(mPreText.length());
    }

    /**
     * 触发条件之二，当文字改变时
     *
     * @param text         当前文本
     * @param start        被修改位置的偏移量
     * @param lengthBefore 之前的长度
     * @param lengthAfter  之后的长度
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mPreText = text.toString();
        getRightTextSize(lengthAfter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 计算合适的textsize
     *
     * @param textLength 改变文本之后的文本长度
     */
    private void getRightTextSize(float textLength) {
        if (textLength <= 0)
            return;

//        mTextViewWidth = this.getWidth();
//        mTextViewHeight = this.getHeight();
//        这么写拿不到值，原因是在onCreate()方法中控件还没有计算自己的参数所以没办法取到

        mTextViewWidth = textLength - this.getPaddingRight() - this.getPaddingLeft();

        float maxWidth = 100, minWidth = 2;
        float i=0.5f;
        mPaint.set(this.getPaint());
        while ((maxWidth - minWidth) > i) {
            mRightTextSize = (maxWidth + minWidth) / 2;
            mPaint.setTextSize(mRightTextSize);
            if (mPaint.measureText(mPreText) >= mTextViewWidth) {
                maxWidth = mRightTextSize;
            } else if (mPaint.measureText(mPreText) <= mTextViewWidth) {
                minWidth = mRightTextSize;
            }
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, minWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = getMeasuredHeight();
        getRightTextSize(parentWidth);
        this.setMeasuredDimension(parentWidth, height);
    }
}
