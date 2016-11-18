package com.example.hjbdemo;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

public class CircleDraw extends View{
	private Paint mArcPaint;
	private float mArcWidth;
	private Paint mArcBGPaint;
	private RectF mOval;
	private float all = 1000;
	private float yu = 900;
	private String Up;
	private String Down;
	private final int BGColor = 0xFFC8C8C8;
	private final int Color = 0xFFFE6F05;
	private final int TextColor = 0xFF000000;
	private boolean hasAnimation = false;

	public CircleDraw(Context context, String one, String two){
		super(context);
		mArcWidth = 10;
		Up = one;
		Down = two;
		//剩余总值
		mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
		mArcPaint.setStyle(Paint.Style.STROKE); 
        mArcPaint.setStrokeWidth(mArcWidth); 
        mArcPaint.setColor(Color);
        BlurMaskFilter mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER); //边缘模糊效果
        mArcPaint.setMaskFilter(mBlur);
        //总值
        mArcBGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcBGPaint.setStyle(Paint.Style.STROKE);
        mArcBGPaint.setStrokeWidth(mArcWidth);
        mArcBGPaint.setColor(BGColor);
        BlurMaskFilter mBGBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.INNER);
        mArcBGPaint.setMaskFilter(mBGBlur);
        
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		//Log.i("onSizeChanged w", w+"");
		//Log.i("onSizeChanged h", h+"");

		mOval = new RectF(10,10,190,190);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawCanvas(canvas);
		drawCircle(canvas);
	}

	private float start = 180;
	private float num = 0;
	private void drawCircle(Canvas canvas){
		float ft = yu / all *180;
		if( hasAnimation ){
			if( num<ft ){
				canvas.drawArc(mOval, start, num, false, mArcPaint);
				num=num+5;
				invalidate();
			}else{
				canvas.drawArc(mOval, start, ft, false, mArcPaint);
			}
			
		}else{
			canvas.drawArc(mOval, start, ft, false, mArcPaint);
		}
	}
	
	private void drawCanvas(Canvas canvas){
		canvas.drawArc(mOval, 179, 181, false, mArcBGPaint);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);  
        p.setColor(TextColor); 
        p.setTextSize(30);
        if(Up!=null && Up.length()>0){
        	canvas.drawText(Up, 50, 60, p);
        }
        p.setColor(BGColor);
        if(Down!=null && Up.length()>0){
        	canvas.drawText(Down, 40, 96, p);
        }
	}

}
