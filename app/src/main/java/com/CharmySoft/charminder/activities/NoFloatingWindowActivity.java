/*
**  Class NoFloatingWindowActivity
**  src/com/CharmySoft/charminder/activities/NoFloatingWindowActivity.java
*/
package com.CharmySoft.charminder.activities;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.other.G;

public class NoFloatingWindowActivity extends ActivityBase  implements OnClickListener {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_floating_window);
        AlertDialog.Builder diaglog = new AlertDialog.Builder(this);
		diaglog.setTitle(R.string.no_floating_window_activity);
	    diaglog.setMessage(R.string.no_floating_window_activity_content);
	    diaglog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) { 
	        }
	     });
	    diaglog.show();
        String s = Build.VERSION.RELEASE.substring(0, 
        		Build.VERSION.RELEASE.indexOf(".", 
        				Build.VERSION.RELEASE.indexOf(".") + 1));
        if (Float.valueOf(s) < 4.2){
        	((RelativeLayout) findViewById(R.id.no_floating_window_activity_step2)).setVisibility(View.GONE);
        }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.no_floating_window_button:
			try{
				Intent intent = new Intent();
				intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
		        intent.setData(Uri.fromParts("package", "com.CharmySoft.charminder", null));
		        startActivity(intent);
			} catch (ActivityNotFoundException ex) { 
			    Intent i = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
			    i.addCategory(Intent.CATEGORY_DEFAULT);
			    startActivity(i);
			}
			break;
		case R.id.no_floating_window_finish:
			ImageView ivSwitch = (ImageView) findViewById(R.id.no_floating_window_finish_switch);
			ivSwitch.setImageResource(R.drawable.switch_1);
			G.settings.bSkipFloatingWindowCheck = true;
			G.settings.save();
			finish();
			G.goToActivity(MainActivity.class);
			break;
		}

	}

}
