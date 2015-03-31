/*
**  Class WakeUpScreen
**  src/com/CharmySoft/charminder/activities/WakeUpScreen.java
*/
package com.CharmySoft.charminder.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

//This activity is only used for waking up the screen when it's off.
//The background is set to transparent (using Theme.Translucent.NoTitleBar)
//No content should be written here in this activity
public class WakeUpScreen extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        RelativeLayout layout = new RelativeLayout(this);
        layout.post(new Runnable() 
        {
            @Override
            public void run() 
            {     
            	finish();
            }
        } );
        setContentView(layout);   
	}

}
