package com.example.hjbdemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class DivisionEditText extends EditText {
	/* 每组的长度 */
	private Integer length = 3;
	/* 分隔符 */
	private String delimiter = ",";

	private String text = "";

	private int maxLength = 20;

	public DivisionEditText(Context context) {
		super(context);
		init();
	}

	public DivisionEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DivisionEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/** 得到每组个数 */
	public Integer getLength() {
		return length;
	}

	/** 设置每组个数 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/** 得到间隔符 */
	public String getDelimiter() {
		return delimiter;
	}

	/** 设置间隔符 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getInputText() {
		return super.getText().toString().replace(getDelimiter(), "");
	}

	public int getEachLength() {
		return 4;
	}

	/**
	 * 初始化
	 */
	public void init() {

		// 内容变化监听
		this.addTextChangedListener(new DivisionTextWatcher());
		// 获取焦点监听
		this.setOnFocusChangeListener(new DivisionFocusChangeListener());
	}

	/**
	 * 文本监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 统计个数
			int len = s.length();

			// 输入首字母为0之后则不显示
			if (len > 1) {
				if (s.toString().charAt(0) == '0') {
					setText("0");
					setSelection(1);
					return;
				}
			}

			if (len < getLength())// 长度小于要求的数
				return;
			if (count > 1) {
				return;
			}
			if (len > maxLength) {// 限制输入的长度
				String str = getText().toString();
				// 截取新字符串
				String newStr = str.substring(0, maxLength);
				text = inversionString(formatSymbol(inversionString(newStr)));
				// maxListener.afterLengthMax();//自定义接口，实现监听回调
			} else {
				// 先倒置，运算之后再倒置回来
				text = inversionString(formatSymbol(inversionString(s
						.toString())));// 关键点
			}

			// text =
			// inversionString(formatSymbol(inversionString(s.toString())));
			setText(text);
			setSelection(text.length());
		}
	}

	/**
	 * 若有，先去除，进行计算之后再添加
	 */
	private String formatSymbol(String str) {
		char[] chars = str.replace(getDelimiter(), "").toCharArray();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < chars.length; i++) {
			if (i % getEachLength() == 0 && i != 0)// 每次遍历到4的倍数，就添加一个空格
			{
				sb.append(getDelimiter());
				sb.append(chars[i]);// 添加字符
			} else {
				sb.append(chars[i]);// 添加字符
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串逆序*
	 * 
	 * @param str
	 * @return
	 */
	private String inversionString(String str) {
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			sb.append(chars[chars.length - i - 1]);
		}
		return sb.toString();
	}

	/**
	 * 获取焦点监听
	 * 
	 * @author Administrator
	 * 
	 */
	private class DivisionFocusChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				// 设置焦点
				setSelection(getText().toString().length());
			}
		}
	}

	/** EditText 长度最大化监听 */
	public interface OnChangeLengthMaxListener {
		public void afterLengthMax();
	}

	public void setOnChangeLengthMaxListener(
			OnChangeLengthMaxListener maxListener) {
		this.maxListener = maxListener;
	}

	private OnChangeLengthMaxListener maxListener;
}