package com.example.hjbdemo;

import java.util.ArrayList;

import com.example.hjbdemo.view.ToastText;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommomToast {
	
	private static final int MAX_LENGTH = 5;
	private static Toast mToast = null;
	public static ArrayList<String> mTips = new ArrayList<String>();
	public static ArrayList<ToastText> mToastText = new ArrayList<ToastText>();
	private static LinearLayout mLinearLayout = null;
	private static View mLayout = null;
	
    public static void ShowToast(Context context,String tvString){  
    	if(mLayout == null){
    		mLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.toast, null);
    	}
    	if(mLinearLayout == null){
    		mLinearLayout = (LinearLayout) mLayout.findViewById(R.id.toast_layout_root);
    	}
        if(mToast == null){
    		mToast = new Toast(context);  
    	}
        mTips.add(tvString);
        if(mTips.size()>MAX_LENGTH){
        	mTips.remove(0);
        	return;
        }
        int num = mTips.size()-1;
        
        ToastText toastText = new ToastText(context, mTips.get(num));
        mToastText.add(toastText);
        mLinearLayout.addView(mToastText.get(num));
        
    	//mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 500);  
        mToast.setDuration(Toast.LENGTH_LONG);  
        mToast.setView(mLayout);  
        mToast.show();  
    }  
}
