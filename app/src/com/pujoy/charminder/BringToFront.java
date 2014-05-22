package com.pujoy.charminder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class BringToFront extends Activity {
	static String s;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent("android.intent.action.MAIN");
		intent.setComponent(new ComponentName(getPackageName(), s));
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
		intent = new Intent();
		intent.setComponent(new ComponentName("com.pujoy.charminder",s));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		getApplicationContext().startActivity(intent);
		startActivity(intent);
        finish();
        //setContentView(R.layout.timer4);
        
        
	}
	static void Open(String app){
		s=app;
	}

}
