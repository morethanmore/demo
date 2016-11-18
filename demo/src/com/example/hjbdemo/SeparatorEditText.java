package com.example.hjbdemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class SeparatorEditText extends EditText{
	
    private final int len = 4; //整数部分每隔len长度加一个re
    private final String re = ","; //分隔符
    private boolean isRun=false;
    private int lastIndex = 0; 

	public SeparatorEditText(Context context) {
		super(context);
	}

	public SeparatorEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public SeparatorEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public String getInputText() {
		String str = super.getText().toString().replace(re, "");
		return str;
	}
	
	private void init() {
		this.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	//这几句要加，不然每输入一个值都会执行两次onTextChanged()，导致堆栈溢出，原因不明
            	if(isRun){
                    isRun = false;
                    return;
                }
                isRun = true;
                
                String finalString = "";//最终显示的字符串
                int index = 0; //光标位置
                String zhen = "";//字符串处理部分
                
                index = getSelectionStart();
                String edtext = s.toString();
                int pt = edtext.length();
                if(pt > 0){
                	zhen = edtext.substring(0, pt);
                	zhen = zhen.replace(re, "");
                	String strZhen = "";
                	for(int i=0; i<zhen.length(); i++){
                		if(i%len==0 && i!=0){
                			strZhen += re;
                			strZhen += zhen.substring(i, i+1);
                			if(index<=(i+i/len) && index >= pt){
                				index += 1;
                			}else if(index==(i+i/len) && index < pt){
                				int cur = index-1;
                				String str = strZhen.substring(cur, cur+1);
                				if(str!=null && str.equals(re)){
                					if(index>lastIndex){
                						index += 1;
                					}else if(index<lastIndex){
                						index -= 1;
                					}
                				}
                			}
                		}
                		else{
                			strZhen += zhen.substring(i, i+1);
                		}
                	}
                	if(index > strZhen.length() && index<=pt){
                		index = strZhen.length();
                	}
                	finalString = strZhen;
                }else{
                	finalString = "";
                }
                SeparatorEditText.this.setText(finalString);
                if(index<0){
					index = 0;
				}
                if(index > finalString.length()){
                	index = finalString.length();
                }
                SeparatorEditText.this.setSelection(index);
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            	lastIndex = getSelectionStart();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
