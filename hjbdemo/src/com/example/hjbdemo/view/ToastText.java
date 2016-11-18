package com.example.hjbdemo.view;

import com.example.hjbdemo.CommomToast;
import com.example.hjbdemo.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToastText extends LinearLayout{
	
	private Context mContext;
	private TextView mTextView;
	
	public ToastText(Context context, String str){
		super(context);
		mContext = context;
		initView(str);
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(CommomToast.mTips.size()>0){
				CommomToast.mTips.remove(0);
				mTextView.setVisibility(View.GONE);
			}
			if(CommomToast.mToastText.size()>0){
				CommomToast.mToastText.remove(0);
			}
		}
	};
	
	private void initView(String str){
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.toast_text, this, true);
		mTextView = (TextView) findViewById(R.id.text0);
		mTextView.setText(str);
		mHandler.sendEmptyMessageDelayed(1, 3000);
	}
	
/*	Android中关于Toast的显示时间变量如下：
	private static final int LONG_DELAY = 3500; // 3.5 seconds           
	private static final int SHORT_DELAY = 2000; // 2 seconds
*/
}
