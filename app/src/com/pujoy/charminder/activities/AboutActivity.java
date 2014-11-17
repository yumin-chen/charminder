/*
**  Class AboutActivity
**  src/com/pujoy/charminder/activities/AboutActivity.java
*/
package com.pujoy.charminder.activities;


import com.pujoy.charminder.R;
import com.pujoy.charminder.base.ActivityBase;
import com.pujoy.charminder.helper.Helper;
import com.pujoy.charminder.other.Log;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity extends ActivityBase implements OnClickListener{
	int iVersionCode = 0;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        PackageInfo pInfo;
        try {
        	pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        	iVersionCode = pInfo.versionCode;
        	((TextView) findViewById(R.id.about_version)).setText("V" + pInfo.versionName);
		} catch (NameNotFoundException e) {
			Log.w(e.getMessage());
			e.printStackTrace();
		}
        
       	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_website:
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pujoy.com"));
			startActivity(browserIntent);
			break;
		}
		case R.id.about_check_for_update:
		{
			Helper.pushText(getString(R.string.about_update_already_latest));
			break;
		}
		}
		
	}

}
