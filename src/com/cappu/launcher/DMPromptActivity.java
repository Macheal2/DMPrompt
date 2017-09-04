package com.cappu.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.WindowManager;

public class DMPromptActivity extends Activity {

	private Button mOkButton;
	private CheckBox mCheckBox;
	private boolean isChecked;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_launcher_main);

		initView();
	}

	private void initView() {
		mCheckBox = (CheckBox) findViewById(R.id.launcher_checkBox);
		mOkButton = (Button) findViewById(R.id.launcher_ok);

		isChecked = mCheckBox.isChecked();

		mCheckBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!isChecked) {
					isChecked = true;
					mOkButton.setClickable(true);
					mOkButton.setTextColor(getResources().getColor(
							R.color.colorTextButtonnormal));
					// mCheckBox.setBackgroundResource(R.drawable.launcher_checkbox_pressed);
				} else {
					isChecked = false;
					mOkButton.setClickable(false);
					mOkButton.setTextColor(getResources().getColor(
							R.color.colorTextButton));
					// mCheckBox.setBackgroundResource(R.drawable.launcher_checkbox_unpressed);
				}
			}
		});

		mOkButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			//	Toast.makeText(DMPromptActivity.this, "this is ok button!",
			//			Toast.LENGTH_SHORT).show();
				
				finishSetupWizard();
			
			}
		});

	}
	   private void finishSetupWizard() {
	        // Add a persistent setting to allow other apps to know the device has been provisioned.
	        Settings.Global.putInt(getContentResolver(), Settings.Global.DEVICE_PROVISIONED , 1);
	        Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 1);

	        // remove this activity from the package manager.
	        PackageManager pm = getPackageManager();
	        ComponentName name = new ComponentName(this, DMPromptActivity.class);
	        pm. setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	        finish();
	    }

	   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		   if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
					   return false; 
			   } else if(keyCode == KeyEvent.KEYCODE_MENU) {
		//	   Toast.makeText(DMPromptActivity.this, "Menu", Toast.LENGTH_SHORT).show();
			   return false;
			   } else if(keyCode == KeyEvent.KEYCODE_HOME) {
			   //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
			 //  Toast.makeText(DMPromptActivity.this, "Home", Toast.LENGTH_SHORT).show();
			   return false;
			   }
		return super.onKeyDown(keyCode, event);
	}
	   
	// 拦截/屏蔽系统Home键
	   @Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
      this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}
	   
}
