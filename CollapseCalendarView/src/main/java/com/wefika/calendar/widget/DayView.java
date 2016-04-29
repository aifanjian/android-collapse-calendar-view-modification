package com.wefika.calendar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.wefika.calendar.R;

/**
 * Created by Blaz Solar on 24/05/14.
 */
public class DayView extends TextView {

    private static final int[] STATE_CURRENT = {R.attr.state_current};

    private boolean mCurrent; //是否选中
    private boolean mIsToday;// 是否是今日
    private boolean mIsOperationDay; //是否是手术日
    private Paint mDotPaint; //绘制手术日期上小圆点的画笔
    private Paint mTextPaint; //绘制手术日期上“手术”二字的画笔
    private Paint mHollowCirclePaint;
    private float mHollowCircleStokeWidth;
//    private float mStartTop_dot;// 绘制小圆点的起始Y点。
//    private float mStartTop_text;// 绘制最下面注释小字的起始Y点

    private float mDotRadius;
    private int mBottomTextColor;
    private int mDotColor;
    private float mBottomTextSize;
    private float mBottomTextMargin_top;
    private String mBottomTextContent = "";


    public DayView(Context context) {
        this(context, null);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mBottomTextMargin_top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.day);
        mDotRadius = typedArray.getDimension(R.styleable.day_dot_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, context.getResources().getDisplayMetrics()));
        mDotColor = typedArray.getColor(R.styleable.day_dot_color, context.getResources().getColor(R.color.mainColor));
        mBottomTextColor = typedArray.getColor(R.styleable.day_text_bottom_color, context.getResources().getColor(R.color.text_calendar));
        mBottomTextSize = typedArray.getDimension(R.styleable.day_text_bottom_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics()));
        mBottomTextContent = typedArray.getString(R.styleable.day_text_bottom_text);
        typedArray.recycle();
        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setColor(mDotColor);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mBottomTextColor);
        mTextPaint.setTextSize(mBottomTextSize);
        mHollowCirclePaint = new Paint();
        mHollowCirclePaint.setAntiAlias(true);
        mHollowCircleStokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
        mHollowCirclePaint.setColor(mDotColor);
        mHollowCirclePaint.setStrokeWidth(mHollowCircleStokeWidth);
        mHollowCirclePaint.setStyle(Paint.Style.STROKE);
    }

    public void setIsToady(boolean isToday) {
        mIsToday = isToday;
        invalidate();
    }

    public boolean isToday() {
        return mIsToday;
    }

    public void setIsOperationDay(boolean isOperationDay) {
        mIsOperationDay = isOperationDay;
        invalidate();
    }

    public boolean getIsOperationDay() {
        return mIsOperationDay;
    }


    public void setCurrent(boolean current) {
        if (mCurrent != current) {
            mCurrent = current;
            refreshDrawableState();
        }
    }

    public boolean isCurrent() {
        return mCurrent;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] state = super.onCreateDrawableState(extraSpace + 1);

        if (mCurrent) {
            mergeDrawableStates(state, STATE_CURRENT);
        }
        return state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsToday && !isSelected()) {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2 - mHollowCircleStokeWidth, mHollowCirclePaint);
        }
        if (mIsOperationDay) {
            float startLeft_dot = canvas.getWidth() / 2;
            float startLeft_text = (canvas.getWidth() - getPaddingLeft() - getPaddingRight() - mTextPaint.measureText(mBottomTextContent)) / 2;
            float startTop_dot = getPaddingTop() + mDotRadius;
            Rect rect = new Rect();
            mTextPaint.getTextBounds(getText().toString(), 0, 1, rect);
            int textHeight = rect.height();
            float startTop_text = getBottom() - getPaddingBottom() + textHeight / 2 + mBottomTextMargin_top;
            canvas.drawCircle(startLeft_dot, startTop_dot, mDotRadius, mDotPaint);
            canvas.drawText(mBottomTextContent, startLeft_text, startTop_text, mTextPaint);
        }
    }
}
