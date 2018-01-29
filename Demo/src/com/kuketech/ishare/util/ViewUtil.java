package com.kuketech.ishare.util;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewUtil {
	private Context mContext;
	public ViewUtil(Context context){
		this.mContext = context;
	}
	public View getView(){
		return this.addViewByJava();
	}
	private LinearLayout addViewByJava() {
	    LinearLayout container = new LinearLayout(mContext);//主布局container
	    TextView tv = new TextView(mContext);//子View TextView
	    // 为主布局container设置布局参数
	    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
	      LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	    container.setLayoutParams(llp);//设置container的布局
	    container.setOrientation(LinearLayout.HORIZONTAL);// 设置主布局的orientation
	    // 为子View设置布局参数
	    ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
	      ViewGroup.LayoutParams.WRAP_CONTENT,
	      ViewGroup.LayoutParams.WRAP_CONTENT);
	    tv.setLayoutParams(vlp);// 设置TextView的布局
	    tv.setText("hello word");
	    container.addView(tv);// 将TextView 添加到container中
	    return container;
	   }
}
