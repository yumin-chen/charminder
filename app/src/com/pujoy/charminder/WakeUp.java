package com.pujoy.charminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class WakeUp extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        View mainView = getLayoutInflater().inflate(R.layout.empty, null);
        mainView.post(new Runnable() 
        {
            @Override
            public void run() 
            {     
            	finish();
            }
        } );
        setContentView(mainView);   
	}

}
