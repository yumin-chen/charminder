package com.pujoy.charminder;

import java.io.Console;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	Point pScreenSize = new Point();
	float fScaleY;
	float fScaleX;
	float fScale;
	float fCircleScale;
	boolean bCircleVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Screen Resolution And Calculate Scale
        getWindowManager().getDefaultDisplay().getSize(pScreenSize);
        fScaleY = pScreenSize.y > 960? 1: pScreenSize.y / 960;
        fScaleX = pScreenSize.x > 680? 1: pScreenSize.x / 680;
        fScale = fScaleX > fScaleY? fScaleY: fScaleX;
        CreateFloatingWindow();
    }

    private void CreateFloatingWindow(){
        final ImageView ivFloating = new ImageView(this);
        final ImageView ivCircle = new ImageView(this);
        ivCircle.setImageResource(R.drawable.circle);
        final WindowManager.LayoutParams wmParamsC = new WindowManager.LayoutParams();
        wmParamsC.type = 2002;   
        wmParamsC.format = 1; 
        wmParamsC.flags = 40;  
        wmParamsC.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsC.width = (int)((pScreenSize.x > pScreenSize.y? pScreenSize.y: pScreenSize.x) * fScale *0.8);
        wmParamsC.width = wmParamsC.width > 960? 960: wmParamsC.width;
        wmParamsC.height = wmParamsC.width;  
        wmParamsC.x = (pScreenSize.x - wmParamsC.width)/2;
        wmParamsC.y = (pScreenSize.y - wmParamsC.height)/2;
        		
        		
        fCircleScale = wmParamsC.width/960f;
        
        final WindowManager wm=(WindowManager)getApplicationContext().getSystemService("window"); 
        final WindowManager.LayoutParams wmParamsI = new WindowManager.LayoutParams();
        wmParamsI.type = 2002;   
        wmParamsI.format = 1; 
        wmParamsI.flags = 40;  
        wmParamsI.width = (int)(128 * fScale);
        wmParamsI.height = (int)(128 * fScale);  
        wmParamsI.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsI.x = (int)(pScreenSize.x - 128 * fScale);
        wmParamsI.y = (int)(pScreenSize.y - 128 * fScale);
        ivFloating.setImageResource(R.drawable.floating_icon);
        ivFloating.setOnTouchListener(new View.OnTouchListener() {
				@Override
        	    public boolean onTouch(View view, MotionEvent motionEvent) {
					if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
				        wmParamsI.x = (int)(motionEvent.getRawX() - wmParamsI.width/2);
				        wmParamsI.y = (int)(motionEvent.getRawY() - wmParamsI.height/2);
						wm.updateViewLayout(ivFloating, wmParamsI);	
						return true;
					}
    		        return false;
        	    }
        });
        ivFloating.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bCircleVisible){
					wm.removeView(ivCircle); 
					bCircleVisible = false;
				}
				else{
					wm.addView(ivCircle, wmParamsC); 
					bCircleVisible = true;
				}

			}
		});
        
        wm.addView(ivFloating, wmParamsI); 
        
        ivCircle.setOnTouchListener(new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View view, MotionEvent motionEvent) {
				if(motionEvent.getAction() == MotionEvent.ACTION_UP){
					// If Mouse Is Inside The Close Area
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							796 * fCircleScale, 46 * fCircleScale,
							96 * fCircleScale, 96 * fCircleScale)){
						wm.removeView(ivCircle); 
						bCircleVisible = false;
					}
					return true;
				}
		        return false;
    	    }
    });
        
    }
    
    public boolean IsPointInsideRect(float PointX, float PointY, 
    		float RectX, float RectY, float RectWidth, float RectHeight){
    	return (PointX >= RectX) && (PointX < RectX + RectWidth) 
    			&& (PointY >= RectY) && (PointY < RectY + RectHeight) ;

    }
}
