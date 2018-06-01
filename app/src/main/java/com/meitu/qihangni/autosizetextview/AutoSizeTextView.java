package com.meitu.qihangni.autosizetextview;

import android.content.Context;
import android.graphics.Paint;
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
    private float mPrePadding = 0.5f;

    public AutoSizeTextView(Context context) {
        super(context);
        initPaint();
    }

    public AutoSizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public AutoSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        Log.i(TAG,"size changed!");
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw)
            getRightTextSize(this.getText().toString(), mPreText.length());
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
    protected void onTextChanged(final CharSequence text,final int start,final int lengthBefore,final int lengthAfter) {
        Log.i(TAG,"text changed!");
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mPreText = text.toString();
        getRightTextSize(text.toString(), this.getWidth());
    }

    /**
     * 触发条件之三，当初始化onMeasure()对textview大小进行判断时
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "widthMeasureSpec:" + widthMeasureSpec);
        Log.i(TAG, "heightMeasureSpec:" + heightMeasureSpec);
        mTextViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mTextViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        getRightTextSize(this.getText().toString(), mTextViewWidth);
        this.setMeasuredDimension((int) mTextViewWidth, (int) mTextViewHeight);
    }


    /**
     * 设置边界的精度值
     *
     * @param prePadding 控制边界精度
     */
    public void setPrePadding(float prePadding) {
        mPrePadding = prePadding;
    }

    /**
     * 计算合适的textsize
     *
     * @param textLength 改变文本之后的文本长度
     */
    private void getRightTextSize(String text, float textLength) {
        if (textLength <= 0)
            return;

//        mTextViewWidth = this.getWidth();
//        mTextViewHeight = this.getHeight();
//        这么写拿不到值，原因是在onCreate()方法中控件还没有计算自己的参数所以没办法取到

        mTextViewWidth = textLength - this.getPaddingRight() - this.getPaddingLeft();
//        mTextViewHeight =
        float maxWidth = 100, minWidth = 2;
        mPaint.set(this.getPaint());
        while ((maxWidth - minWidth) > mPrePadding) {
            mRightTextSize = (maxWidth + minWidth) / 2;
            mPaint.setTextSize(mRightTextSize);
            Log.i(TAG, "mRightTextSize:" + mRightTextSize + " and padding is: " + (maxWidth - minWidth));
            if (mPaint.measureText(text) >= mTextViewWidth) {
                maxWidth = mRightTextSize;
            } else if (mPaint.measureText(text) <= mTextViewWidth) {
                minWidth = mRightTextSize;
            }
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, minWidth);
    }


}
