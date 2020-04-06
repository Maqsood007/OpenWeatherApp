package com.maqsood007.weatherforecast.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.maqsood007.weatherforecast.R;

/**
 * Created by ZhengHongEn on 2016/3/31.
 */
public class SearchEditText extends androidx.appcompat.widget.AppCompatEditText {
    String hintText = "";
    float hintTextSize = 0;
    int hintTextColor = 0xFF000000;
    int hintImage;
    float hintImageSize = 0;

    Drawable mDrawable;
    Paint paint;

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResource(context, attrs);
        initPaint();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initResource(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHintIcon(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mDrawable == null) {
            try {
                mDrawable = getContext().getResources().getDrawable(hintImage);
                mDrawable.setBounds(0, 0, (int) hintImageSize, (int) hintImageSize);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            mDrawable = null;
        }
        super.onDetachedFromWindow();
    }


    /**
     * 初始化 资源
     *
     * @param context
     * @param attrs
     */
    private void initResource(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchEditText);
        float density = context.getResources().getDisplayMetrics().density;
        hintText = mTypedArray.getString(R.styleable.SearchEditText_text);
        hintTextSize = mTypedArray.getDimension(R.styleable.SearchEditText_textSize, 14 * density + 0.5F);
        hintTextColor = mTypedArray.getColor(R.styleable.SearchEditText_textColor, 0xFF848484);

        hintImage = mTypedArray.getResourceId(R.styleable.SearchEditText_image, android.R.drawable.ic_menu_search);
        hintImageSize = mTypedArray.getDimension(R.styleable.SearchEditText_imageSize, 18 * density + 0.5F);
        mTypedArray.recycle();
    }

    /**
     * 初始化 画笔
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(hintTextColor);
        paint.setTextSize(hintTextSize);
    }

    /**
     * @param canvas
     */
    private void drawHintIcon(Canvas canvas) {
        if (!this.isFocused()) {//没获取焦点时
            float textWidth = paint.measureText(hintText);
            float textHeight = getFontLeading(paint);

            float dx = (getWidth() - hintImageSize - textWidth - 8) / 2;
            float dy = (getHeight() - hintImageSize) / 2;

            canvas.save();
            canvas.translate(getScrollX() + dx, getScrollY() + dy);
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }
            canvas.drawText(hintText, getScrollX() + hintImageSize + 8, getScrollY() + (getHeight() - (getHeight() - textHeight) / 2) - paint.getFontMetrics().bottom - dy, paint);
            canvas.restore();
        } else {//获取焦点
            float dx = (getWidth() - hintImageSize - 20);
            float dy = (getHeight() - hintImageSize) / 2;

            canvas.save();
            canvas.translate(getScrollX() + dx, getScrollY() + dy);
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }

            canvas.restore();
        }
    }

    /**
     * @param paint
     * @return
     */
    private float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }
}