package com.example.testing.pageindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lee on 15/12/7.
 */
public class PageIndicator extends View {
    private static final int  DefRadius = 8;
    private static final int  DefSpacing = 5;
    private static final int  DefStrokeWidth = 2;
    private static final int  DefNum = 3;
    private static final String DEBUG = PageIndicator.class.getSimpleName();
    private int num;//数量
    private int strokeColor;
    private int fillColor;
    private float radius;//外直径
    private float strokeWidth;
    private float spacing;
    private Paint strokePaint;
    private Paint fillPaint;
    private int currentPage;
    private OnPageIndicatorChangeOffset pageIndicatorChangeOffset;
    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttribute(context, attrs, defStyleAttr);
        Log.d(DEBUG, "radius==" + radius);
    }

    public void setPageIndicatorChangeOffset(OnPageIndicatorChangeOffset pageIndicatorChangeOffset) {
        this.pageIndicatorChangeOffset = pageIndicatorChangeOffset;
    }

    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.PageIndicator,defStyleAttr,0);
        int index = typedArray.getIndexCount();
        for (int i=0;i<index;i++){
            int type = typedArray.getIndex(i);
            switch (type){
                case R.styleable.PageIndicator_strokeColor:
                    strokeColor = typedArray.getColor(type,strokeColor);
                    break;
                case R.styleable.PageIndicator_fillColor:
                    fillColor = typedArray.getColor(type,fillColor);
                    break;
                case R.styleable.PageIndicator_radius:
                    radius = typedArray.getDimension(type, radius);
                    break;
                case R.styleable.PageIndicator_spacing:
                    spacing = typedArray.getDimension(type,spacing);
                    break;
//                case R.styleable.PageIndicator_num:
//                    num = typedArray.getInt(type,num);
//                    break;
            }
        }
        typedArray.recycle();
    }

    private void init(Context context) {
        currentPage=0;
        num = DefNum;
        strokeColor = Color.WHITE;
        fillColor = Color.WHITE;
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DefRadius,context.getResources().getDisplayMetrics());
        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DefStrokeWidth,context.getResources().getDisplayMetrics());
        spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,DefSpacing,context.getResources().getDisplayMetrics());
        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setColor(strokeColor);
        fillPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(fillColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if (widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
           width = (int) (getPaddingLeft() + radius*2*num+spacing*(num-1) + getPaddingRight());
        }

        if (heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = (int) (getPaddingTop() + radius*2 +getPaddingBottom());
        }
        setMeasuredDimension(width, height);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNum() {
        return num;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage>num-1){
            this.currentPage = num-1;
            return;
        }else if (currentPage<0){
            this.currentPage = 0;
            return;
        }
        if (this.currentPage!=currentPage){
            this.currentPage = currentPage;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        for (int i = 0;i<=currentPage;i++){
//            PointF pointF=getCenterWithIndex(i);
//            canvas.drawCircle(pointF.x,pointF.y,radius-strokeWidth/2,fillPaint);
//        }
//        for (int i = currentPage+1;i<num;i++){
//            PointF pointF=getCenterWithIndex(i);
//            canvas.drawCircle(pointF.x,pointF.y,radius-strokeWidth/2,strokePaint);
//        }
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,radius-strokeWidth/2,strokePaint);
        strokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        strokePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getMeasuredWidth()/2-20,getMeasuredHeight()/2,radius-strokeWidth/2,strokePaint);

    }

    private PointF getCenterWithIndex(int index){
        float cx,cy;
        cx = getPaddingLeft()+radius*(2*index+1)+spacing*index;
        cy = getPaddingTop() + radius;
        return  new PointF(cx,cy);
    }
    public interface OnPageIndicatorChangeOffset{
        public void OnChangeOffset(int position, float positionOffset, int positionOffsetPixels);
    }
}
