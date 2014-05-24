package com.pujoy.charminder;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Scene {

	static DisplayMetrics metrics;
	public void Create(Context c){
		metrics = new DisplayMetrics();
		((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}
	public static float dpToPx(float dp){
		return MainActivity.dpToPx(dp);
    }
    public boolean IsPointInsideRect(float PointX, float PointY, 
    		float RectX, float RectY, float RectWidth, float RectHeight){
    	return (PointX >= RectX) && (PointX < RectX + RectWidth) 
    			&& (PointY >= RectY) && (PointY < RectY + RectHeight) ;

    }
}
