package com.one.domain.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.one.domain.R;

/**
 * description:
 * @author: LinDingQiang
 * @created on: 2016/3/31 16:00
 */
public class NavItem extends View {
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private int mTextColorSelected;
    private Bitmap mIcon;
    private Bitmap mIcon2;
    private Paint mTextPaint;
    private Paint mSelectedPaint;
    private Rect mTextRect;
    private Rect mIconRect;
    private int alpha;

    public NavItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initPaint();
        getTextBoundsRect();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.NavItem);
        mTextColor = typedArray.getColor(R.styleable.NavItem_nav_text_color, context.getResources().getColor(R.color.textColor_85));
        mTextColorSelected = typedArray.getColor(R.styleable.NavItem_nav_text_color_selected, context.getResources().getColor(R.color.colorAccent));
        mText = typedArray.getString(R.styleable.NavItem_nav_text);
        mTextSize = typedArray.getDimension(R.styleable.NavItem_nav_text_size, 10);
        mIcon = ((BitmapDrawable) typedArray.getDrawable(R.styleable.NavItem_nav_icon)).getBitmap();
        mIcon2 = ((BitmapDrawable) typedArray.getDrawable(R.styleable.NavItem_nav_icon_selected)).getBitmap();
        typedArray.recycle();
    }

    private void initPaint() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setColor(mTextColorSelected);
        mSelectedPaint.setTextSize(mTextSize);
        //bgPaint.setDither(true);
    }

    private void getTextBoundsRect() {
        mTextRect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initIconRect(w, h);
        initTextRect(w, h);
    }

    private void initIconRect(int w, int h) {
        int imgHeight = mIcon.getWidth();
        int imgWidth = mIcon.getHeight();
        int left = (w - imgWidth) / 2;
        int right = left + imgWidth;
        int top = (h - imgHeight - mTextRect.height()) / 2;
        int bottom = top + imgHeight;
        mIconRect = new Rect(left, top, right, bottom);
    }

    private void initTextRect(int w, int h) {
        int left = (w - mTextRect.width()) / 2;
        int right = left + mTextRect.width();
        int top = mIconRect.bottom+1;
        int bottom = top + mTextRect.height();
        mTextRect = new Rect(left, top, right, bottom);
    }

    public void updateItemAlpha(float alpha){
        int updateAlpha = (int) Math.ceil(255*alpha);
        if (updateAlpha != this.alpha){
            this.alpha = updateAlpha;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clearCanvas(canvas);
        drawIcon(canvas);
    }

    private void clearCanvas(Canvas canvas) {
        if (alpha < 254){
            canvas.drawBitmap(mIcon, null, mIconRect, null);
            canvas.drawText(mText,mTextRect.left,mTextRect.bottom,mTextPaint);
        }

    }

    private void drawIcon(Canvas canvas) {
        if (alpha > 1){
            mSelectedPaint.setAlpha(alpha);
            canvas.drawBitmap(mIcon2,null,mIconRect, mSelectedPaint);
            canvas.drawText(mText, mTextRect.left, mTextRect.bottom, mSelectedPaint);
        }
    }
}
