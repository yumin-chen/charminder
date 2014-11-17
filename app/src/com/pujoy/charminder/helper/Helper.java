/*
**  Class Helper
**  src/com/pujoy/charminder/helper/Helper.java
*/
package com.pujoy.charminder.helper;

import com.pujoy.charminder.views.FloatingText;

public class Helper {
	public static FloatingText mFloatingText;

	public static void pushText(String text) {
		pushText(text, 5);
	}

	public static void pushText(String text, int time) {
		if (mFloatingText != null) {
			if (mFloatingText.isCreated())
				mFloatingText.remove();
			mFloatingText = null;
		}
		mFloatingText = new FloatingText();
		mFloatingText.setText(text);
		mFloatingText.iTimer = time;
		mFloatingText.create();
	}
}
