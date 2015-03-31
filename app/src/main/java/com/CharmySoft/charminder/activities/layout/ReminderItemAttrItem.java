/*
**  Class ReminderItemAttrItem
**  src/com/CharmySoft/charminder/activities/layout/ReminderItemAttrItem.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;

public class ReminderItemAttrItem extends LinearLayout {
	public ReminderItemAttrItem(Context context) {
		super(context);
		setOrientation(HORIZONTAL);
		setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		LayoutInflater.from(getContext()).inflate(R.layout.fragment_reminder_item_expansion_item,
				this, true);
	}

	public void setIcon(Drawable drawable){
		((ImageView) findViewById(R.id.expansion_item_icon)).setImageDrawable(drawable);
	}
	public void setText(CharSequence text){
		((TextView) findViewById(R.id.expansion_item_text)).setText(text);
	}
	
}
