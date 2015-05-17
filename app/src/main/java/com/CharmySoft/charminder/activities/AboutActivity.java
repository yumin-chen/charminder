/*
**  Class AboutActivity
**  src/com/CharmySoft/charminder/activities/AboutActivity.java
*/
package com.CharmySoft.charminder.activities;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.helper.Helper;
import com.CharmySoft.charminder.other.Log;

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
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.CharmySoft.com"));
			startActivity(browserIntent);
			break;
		}
		case R.id.about_rate:
		{
            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (Exception e) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                } catch (Exception ex) {
                }
            }
			break;
		}
		}
		
	}

}
