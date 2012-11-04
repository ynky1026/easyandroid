package com.zj.easyandroid.annotation;

import com.zj.easyandroid.core.Enum.TextChange;

public @interface TextChangeListener {

	public int id();
	
	public TextChange callback() default TextChange.ON;
}
