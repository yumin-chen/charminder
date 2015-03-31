/*
**  Class ViewBase
**  src/com/CharmySoft/charminder/base/ViewBase.java
*/
package com.CharmySoft.charminder.base;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

import com.CharmySoft.charminder.other.G;

public class ViewBase {
	static WindowManager mWindowManager;
	private static DisplayMetrics mMetrics;
	private static boolean bOldRotation = isLandscape();

	public ViewBase() {
		if (mMetrics == null) {
			mMetrics = new DisplayMetrics();
			((Activity) G.context).getWindowManager().getDefaultDisplay()
					.getMetrics(mMetrics);
			bOldRotation = isLandscape();
		}
		if (mWindowManager == null)
			mWindowManager = (WindowManager) G.context.getApplicationContext()
					.getSystemService("window");
	}

	public static float dpToPx(float dp) {
		return dp * (mMetrics.densityDpi / 160f);
	}
	
	public static float pxToDp(float px) {
		return px / (mMetrics.densityDpi / 160f);
	}

	public static boolean isPointInsideRect(float pointX, float pointY,
			float rectX, float rectY, float rectWidth, float rectHeight) {
		return (pointX >= rectX) && (pointX < rectX + rectWidth)
				&& (pointY >= rectY) && (pointY < rectY + rectHeight);
	}

	public static boolean isPointInsideCircle(int pointX, int pointY,
			int circleCenterX, int circleCenterY, int radius) {
		int dx = Math.abs(circleCenterX - pointX);
		int dy = Math.abs(circleCenterY - pointY);
		if (dx + dy <= radius)
			return true;
		if (dx > radius)
			return false;
		if (dy > radius)
			return false;
		if ((dx ^ 2) + (dy ^ 2) <= radius)
			return true;
		return false;
	}

	public static boolean isRotated() {
		return (bOldRotation != isLandscape());
	}

	public static boolean isLandscape() {
		final int rotation = ((WindowManager) G.context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getRotation();
		return (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270);
	}

	public static int getScreenWidth() {
		if (isRotated())
			return mMetrics.heightPixels;
		return mMetrics.widthPixels;
	}

	public static int getScreenHeight() {
		if (isRotated())
			return mMetrics.widthPixels;
		return mMetrics.heightPixels;
	}
}
