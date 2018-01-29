package com.driftparking.park013d;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import com.science.isharelib.ShareRequest;
import com.science.isharelib.data.DataCache;
import com.science.isharelib.util.HttpInterfaceUitl;
import com.science.isharelib.util.LogUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,HttpInterfaceUitl{
	
	private static boolean isExit = false;
	private Button btnRegist, btnRechange, btnPlayerCenter,btnAddrole;
	private TextView ver;
	private EditText edit,editip,playeracc,nicknametxt,occupationtxt,gameremarktxt,money,roleleveltxt,QQAppId,wetchatAppId,SinaWeiboAppid;
	private Spinner dropdownlist;
	private String SECEND_NUM = "";
	ShareRequest SR;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SR = new ShareRequest(this.getApplicationContext());
		
		//设置显示APP的图标、title 
		//requestWindowFeature(Window.FEATURE_NO_TITLE);    
        //设置全屏    
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   
		setContentView(R.layout.activity_main);
		
		ver = (TextView) findViewById(R.id.TextView09);
		QQAppId=(EditText) findViewById(R.id.QQAppId);
		wetchatAppId=(EditText) findViewById(R.id.wetchatAppId);
		SinaWeiboAppid=(EditText) findViewById(R.id.SinaWeiboAppid);
		edit = (EditText) findViewById(R.id.phone);
		money = (EditText) findViewById(R.id.money);
		playeracc = (EditText) findViewById(R.id.playerAcc);
		editip = (EditText) findViewById(R.id.ipEdit);
		nicknametxt = (EditText) findViewById(R.id.nickname);
		occupationtxt = (EditText) findViewById(R.id.occupation);
		gameremarktxt = (EditText) findViewById(R.id.gameremark);
		roleleveltxt = (EditText) findViewById(R.id.rolelevel);
		dropdownlist = (Spinner) findViewById(R.id.spinner1);
		btnRegist = (Button) findViewById(R.id.regist);
		btnRegist.setOnClickListener(this);
		btnAddrole = (Button) findViewById(R.id.addRole);
		btnAddrole.setOnClickListener(this);
		btnRechange = (Button) findViewById(R.id.recharge);
		btnRechange.setOnClickListener(this);
		btnPlayerCenter = (Button)findViewById(R.id.playercenter);
		btnPlayerCenter.setOnClickListener(this);
		
		
		SharedPreferences sharedPreferences= getSharedPreferences("test",Activity.MODE_PRIVATE); 
		playeracc.setText(sharedPreferences.getString("account", "")); 
		edit.setText(sharedPreferences.getString("cellnum", ""));
		nicknametxt.setText(sharedPreferences.getString("nickname", "")); 
		occupationtxt.setText(sharedPreferences.getString("occupation", "")); 
		gameremarktxt.setText(sharedPreferences.getString("remark", "")); 
		roleleveltxt.setText(sharedPreferences.getString("rolelevel", ""));
		editip.setText(sharedPreferences.getString("ip", ""));
		QQAppId.setText(sharedPreferences.getString("qqid", ""));
		wetchatAppId.setText(sharedPreferences.getString("wetchatid", ""));
		SinaWeiboAppid.setText(sharedPreferences.getString("sinaid", ""));
		try {
			PackageManager packageManager = this.getPackageManager();  
	        // getPackageName()是你当前类的包名，0代表是获取版本信息  
	        PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(this.getPackageName(),0);
			ver.setText("版本: "+packInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		//Debug日志启动标识
		LogUtil.debugLogEnable = true;
		String gameZone1 =dropdownlist.getSelectedItem().toString();
		String playerAcc = playeracc.getText().toString();
		String cellnm = edit.getText().toString();
		String nickname = nicknametxt.getText().toString();
		String occupation = occupationtxt.getText().toString();
		String gameremark = gameremarktxt.getText().toString();
		String rolelevel = roleleveltxt.getText().toString();
		String wetchatId = wetchatAppId.getText().toString();
		String qqAppId = QQAppId.getText().toString();
		String sinaWeiboAppid = SinaWeiboAppid.getText().toString();
		SR.setCallBack(this);
		if(v.getId()==R.id.regist) {
			String ip = editip.getText().toString();
			DataCache.IP_ADD=ip;
			if(!wetchatId.isEmpty()&&!qqAppId.isEmpty()&&!sinaWeiboAppid.isEmpty()){//!ip.isEmpty()&& 
				if(!cellnm.isEmpty() && !playerAcc.isEmpty()){
					SR.regist(playerAcc,gameZone1,cellnm,wetchatId,qqAppId,sinaWeiboAppid);
				}else{
					LogUtil.e("手机号或游戏账号不能为空！");
					Toast.makeText(this.getApplicationContext(), "手机号或游戏账号不能为空!", Toast.LENGTH_SHORT).show();
					return;
				}
			}else{Toast.makeText(this.getApplicationContext(), "平台APP账户没有输入", Toast.LENGTH_SHORT).show();}
		}		
		else if(v.getId()==R.id.recharge){
			String cash = money.getText().toString();
			if(!cash.isEmpty()){
				SR.rechangeMoney(cash);
			}else{
				LogUtil.e("请输入充值金额");
				Toast.makeText(this.getApplicationContext(), "请输入充值金额!", Toast.LENGTH_SHORT).show();
				return;
			}	
		}			
		else if(v.getId()==R.id.playercenter){
			if(!cellnm.isEmpty() && !playerAcc.isEmpty()){
				SR.playercenter();	
			}else{
				LogUtil.e("手机号或游戏账号不能为空！");
				Toast.makeText(this.getApplicationContext(), "手机号或游戏账号不能为空!", Toast.LENGTH_SHORT).show();
				return;
			}
		}else if(v.getId()==R.id.addRole){
			if(!nickname.isEmpty() && !occupation.isEmpty()){
				SR.addrole(nickname, occupation, gameremark, "", rolelevel);	
			}else{
				LogUtil.e("游戏角色信息输入不完整！包括：昵称、职业");
				Toast.makeText(this.getApplicationContext(), "游戏角色信息输入不完整！包括：昵称、职业", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		SharedPreferences mySharedPreferences= getSharedPreferences("test",	Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("account", playeracc.getText().toString()); 
		editor.putString("cellnum", edit.getText().toString()); 
		editor.putString("nickname", nicknametxt.getText().toString()); 
		editor.putString("occupation", occupationtxt.getText().toString());
		editor.putString("remark", gameremarktxt.getText().toString());
		editor.putString("rolelevel", roleleveltxt.getText().toString());
		editor.putString("ip", editip.getText().toString());
		editor.putString("qqid", QQAppId.getText().toString());
		editor.putString("wetchatid", wetchatAppId.getText().toString());
		editor.putString("sinaid",SinaWeiboAppid.getText().toString());
		editor.commit(); 	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 双击退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// moveTaskToBack(true);
			// super.onBackPressed();
			// return super.onKeyDown(KeyEvent.KEYCODE_HOME, event);
			return exitApplication();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 退出程序
	 */
	private boolean exitApplication() {
		// 设置定时器
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			// System.exit(0);
		}
		return false;
	}

	@Override
	public void callBack(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String arg0, int arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(this.getApplicationContext(),arg0, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void success(String arg0, int arg1) {
		// TODO Auto-generated method stub
		Toast.makeText(this.getApplicationContext(),arg0, Toast.LENGTH_SHORT).show();
	}
	

}
