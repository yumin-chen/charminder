package com.pujoy.charminder.base;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;
import static com.pujoy.charminder.MainActivity.con;

public class ViewBase {
	static WindowManager wm; 
	private static DisplayMetrics metrics;
	private static boolean bOldRotation = isLandscape();
	public ViewBase(){
		if (metrics == null){
		metrics = new DisplayMetrics();
		((Activity) con).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		bOldRotation = isLandscape();
		}
		if(wm == null) wm =(WindowManager)con.getApplicationContext().getSystemService("window");
	}
	public static float dpToPx(float dp){
		return dp *(metrics.densityDpi / 160f);
    }
    public static boolean isPointInsideRect(float PointX, float PointY, 
    		float RectX, float RectY, float RectWidth, float RectHeight){
    	return (PointX >= RectX) && (PointX < RectX + RectWidth) 
    			&& (PointY >= RectY) && (PointY < RectY + RectHeight) ;
    }
    public static boolean isRotated(){
    	return(bOldRotation != isLandscape());
    }
    public static boolean isLandscape(){
    	final int rotation = ((WindowManager) con.getSystemService(Context.WINDOW_SERVICE)).
				getDefaultDisplay().getRotation();
    	return(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270);
    }
    
    public static int getScreenWidth(){
    	if (isRotated()) return metrics.heightPixels;
    	return metrics.widthPixels;
    }
    
    public static int getScreenHeight(){
    	if (isRotated()) return metrics.widthPixels;
    	return metrics.heightPixels;
    }
}
