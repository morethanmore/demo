package com.example.hjbdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FilpView extends View{
	private final int foreward = 101;//倒计时，数字变小
	private final int reversal = 102;//正计时，数字变大
	private final String[] time={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private Surface surface;
	private int num = 0;
	private int speed = 10; //最好设置成2的整数倍
	private int turnTpye = foreward; //默认倒计时，可设置
	private int mTimeNum = 1;
	private String front = "";
	private String back = "";
	private Matrix matrix = new Matrix();
	private Matrix m = new Matrix();

	public FilpView(Context context) {
		super(context);
		init();
	}
	
	public FilpView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	private void init(){
		surface = new Surface();
		surface.init();
		setBackgroundColor(surface.bgColor);
	}
	
	/**
	 * 设置倒计时还是正计时，倒计时传入true
	 * @param isforeward
	 */
	public void setTurnType(boolean isforeward){
		if(isforeward){
			this.turnTpye = foreward;
		}else{
			this.turnTpye = reversal;
		}
	}
	
	public void setTimeNumber(String number){
		int nn = Integer.parseInt(number);
		Log.i("hjb", "设置的数字是 = "+nn);
		if(nn>=0 && nn<=9){
			this.mTimeNum = nn;
			if(turnTpye ==  foreward){ //倒计时
				back = time[mTimeNum];
				if(mTimeNum==9){
					front = time[0];
				}else{
					front = time[mTimeNum+1];
				}
			}else{ //正计时
				back = time[mTimeNum];
				if(mTimeNum==0){
					front = time[9];
				}else{
					front = time[mTimeNum-1];
				}
			}
			num = 0;
			Log.i("hjb", "front = "+front);
			Log.i("hjb", "back = "+back);
			invalidate();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//获取上层文字的上下bitmap
		float x = (surface.width-surface.BitmapPaint.measureText(front))/2f;
		float y = surface.height*0.8f;
		Bitmap fill_bitmap = Bitmap.createBitmap(surface.width, surface.height, Bitmap.Config.ARGB_8888);
		Canvas b_canvas = new Canvas(fill_bitmap); 
		b_canvas.drawColor(Color.WHITE);
		b_canvas.drawText(front, x, y, surface.BitmapPaint);
		Bitmap bitmap_up = Bitmap.createBitmap(fill_bitmap, 0, 0, surface.width, surface.height/2);
		Bitmap bitmap_down = Bitmap.createBitmap(fill_bitmap, 0, surface.height/2, surface.width, surface.height/2);
		
		//获取下层文字的上下bitmap
		float xx = (surface.width-surface.BackPaint.measureText(back))/2f;
		float yy = surface.height*0.8f;
		Bitmap back_fill_bitmap = Bitmap.createBitmap(surface.width, surface.height, Bitmap.Config.ARGB_8888);
		Canvas back_b_canvas = new Canvas(back_fill_bitmap); 
		back_b_canvas.drawColor(Color.WHITE);
		back_b_canvas.drawText(back, xx, yy, surface.BitmapPaint);
		Bitmap back_bitmap_down = Bitmap.createBitmap(back_fill_bitmap, 0, surface.height/2, surface.width, surface.height/2);
		m.postScale(1, -1);   //镜像垂直翻转
		Bitmap new2 = Bitmap.createBitmap(back_bitmap_down, 0, 0, surface.width, surface.height/2, m, true);
		
		//画下层文字
		canvas.drawText(back, xx, yy, surface.BackPaint);
		//画上层下半部
		if(num<=surface.height){
			canvas.drawBitmap(bitmap_down, 0, surface.height/2, surface.FrontPaint);
		}
		//矩阵画上层上半部
		if(num<=surface.height){
			float w = surface.width;
			float h = surface.height/2;
			float[] src = { 0, 0,
					w, 0, 
					w, h,
	                0, h };  
	        float[] dst = { 0, num,
	        		w, num,
	        		w, h,
	                0, h };
	        matrix.setPolyToPoly(src, 0, dst, 0, 4);  
	        if(num<surface.height/2){
		        canvas.drawBitmap(bitmap_up, matrix, surface.FrontPaint);
	        }else if(num>surface.height/2){
		        canvas.drawBitmap(new2, matrix, surface.FrontPaint);
	        }
	        num = num+speed;
	        invalidate();
		}else{
			
		}
	}

	/*@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}*/
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//surface.width = 200;
		//surface.height = 300;
		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width, View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height, View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private class Surface{
		public int width; // 整个控件的宽度，最好设置成2的整数倍
		public int height; // 整个控件的高度，最好设置成2的整数倍
		public Paint FrontPaint;
		public Paint BackPaint;
		public Paint BitmapPaint;
		public int bgColor = Color.parseColor("#FFFFFF");//背景
		
		private void init(){
			
			width = 60;
			height = 80;
			
			float textSize = height * 0.8f;
			
			FrontPaint = new Paint();
			FrontPaint.setAntiAlias(true); //抗锯齿 
			FrontPaint.setTextSize(textSize);
			FrontPaint.setTypeface(Typeface.DEFAULT); //字体样式
			
			BackPaint = new Paint();
			BackPaint.setAntiAlias(true);
			BackPaint.setTextSize(textSize);
			BackPaint.setTypeface(Typeface.DEFAULT);
			
			BitmapPaint = new Paint();
			BitmapPaint.setAntiAlias(true);
			BitmapPaint.setTextSize(textSize);
			BitmapPaint.setTypeface(Typeface.DEFAULT);
		}
	}
	
}
