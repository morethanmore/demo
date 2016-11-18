package com.example.hjbdemo;

import java.util.Arrays;

import com.example.hjbdemo.view.FilpView;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	
	public LinearLayout ll;
	private RulerDraw bitmapView = null;
	private TextView mText;
	private SeparatorEditText mEdit;
	private int start = 0;
	private FilpView mFilpView;
	private int time = 9; //翻牌倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //半圆
        setContentView(R.layout.activity_main);
        ll = (LinearLayout)findViewById(R.id.circle);
/*        CircleDraw circle = new CircleDraw(this,"1万","剩余金额");
        ll.addView(circle);*/
        mText = (TextView) findViewById(R.id.fan);
        mFilpView = (FilpView) findViewById(R.id.filp);
        //mFilpView.setTurnType(false);
        mText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//startCount();
		        //String str = mEdit.getInputText();
		        //start = start + 1;
		        //String strstr = String.valueOf(start);
		        //CommomToast.ShowToast(MainActivity.this, strstr);
				mFilpView.setTimeNumber(String.valueOf(time));
				time--;
				if(time<0){
					time = 9;
				}
			}
		});
        mEdit = (SeparatorEditText) findViewById(R.id.money_edit);

        //bitmap
        //bitmapView = new RulerDraw(this);  
        //setContentView(bitmapView);
        //gestureDetector = new GestureDetector(MainActivity.this,onGestureListener);
    }

    /******************倒计时 S*********************/
    private TimeCount timeCount;
	public void startCount(){
		if (null != timeCount) {
			timeCount.cancel();
		}
		remindtime = 5*1;
		timeCount = new TimeCount((remindtime+1)*1000, 1000);
		timeCount.start();
		remindtime--;
	}
	
	private long remindtime = 5*1;
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			String[] str = getCountDown(remindtime--);
			Log.i("hjb", "onFinish: "+Arrays.toString(str));
			mText.setText(str[5]);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			String[] str = getCountDown(remindtime--);
			Log.i("hjb", "onTick: "+Arrays.toString(str)+"  remindtime= "+remindtime);
			//rotateyAnimRun(mText);
			mText.setText(str[5]);
			mText.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.pageturning_bottom));
		}
	}
	
	public void rotateyAnimRun(View view)  
    {  
         ObjectAnimator
         .ofFloat(view, "rotationX", 0.0F, 90.0F)
         .setDuration(1000)
         .start();  
    }  
	
	private String[]  getCountDown(long timestr) {
        String[] str = {"0","0","0","0","0","0"};
        try {
        	Log.i("hjb", "timestr: "+timestr);
            if (timestr != 0) {
                long time = timestr;
//                long current = System.currentTimeMillis();
                long diff = time*1000;	//如果后台配置的是秒单位，乘以1000
                long hour = diff/(60 * 60 * 1000);
                long minute = (diff % (60 * 60 * 1000))/(60 * 1000);
                long second = (diff % (60 * 1000))/1000;
                if(hour < 0){hour = 0;};
                if(minute < 0){minute = 0;};
                if(second < 0){second = 0;};
                String h = ""+hour,m=""+minute,s=""+second;
                if(h.length() < 2){
                	h = "0"+h;
                }
                if(h.length() > 2){
                	h = h.substring(1);
                }
                if(m.length() < 2){
                	m = "0"+m;
                }
                if(s.length() < 2){
                	s = "0"+s;
                }
                str[0] = ""+h.charAt(0);
                str[1] = ""+h.charAt(1);
                str[2] = ""+m.charAt(0);
                str[3] = ""+m.charAt(1);
                str[4] = ""+s.charAt(0);
                str[5] = ""+s.charAt(1);
            }else{
            	str[0] = "0";
                str[1] = "0";
                str[2] = "0";
                str[3] = "0";
                str[4] = "0";
                str[5] = "0";
            }
        } catch (NumberFormatException e) {
        	str = null;
            str = new String[]{"0","0","0","0","0","0"};
        }

        return str;
    }
	/******************倒计时 E*********************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    final int RIGHT = 0;  
    final int LEFT = 1;
    private GestureDetector gestureDetector;
	private GestureDetector.OnGestureListener onGestureListener =   
	        new GestureDetector.SimpleOnGestureListener() {  
	        @Override  
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	                float velocityY) {  
	            float x = 0;
	            if(e1!=null && e2!=null){
	            	x = e2.getX() - e1.getX();  
	            }
	            Log.i("hjb", "onFlingX = "+ x);
	  
	            if (x > 50) {  
	                doResult(RIGHT);  
	            } else if (x < 50) {  
	                doResult(LEFT);  
	            }  
	            return false;  
	        }  
	    };  
	  
    public void doResult(int action) {  
  
        switch (action) {  
        case LEFT:  
        	//doLeft();
            break;  
  
        case RIGHT:  
        	//doRight();
            break;  
  
        }  
    }

/*	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("hjb", "onTouchEvent");
		return gestureDetector.onTouchEvent(event);  
	}  */

}
