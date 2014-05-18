package com.pujoy.charminder;

import java.io.Console;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	Point pScreenSize = new Point();
	static float fScaleY;
	static float fScaleX;
	static float fScale;
	static float fCircleScale;
	static boolean bCircleVisible;
	static boolean bBubbleVisible;
	static boolean bFloatingWindowRunning;
	static ImageView ivFloating;
	static ImageView ivCircle;
	static ImageView ivBubble;
	static WindowManager wm; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Screen Resolution And Calculate Scale
        getWindowManager().getDefaultDisplay().getSize(pScreenSize);
        fScaleY = pScreenSize.y > 960? 1: pScreenSize.y / 960;
        fScaleX = pScreenSize.x > 680? 1: pScreenSize.x / 680;
        fScale = fScaleX > fScaleY? fScaleY: fScaleX;
        
        wm =(WindowManager)getApplicationContext().getSystemService("window");
        
        final ImageView ivTick = (ImageView)findViewById(R.id.floatingwindow_tick);
        ivTick.setImageResource(bFloatingWindowRunning? R.drawable.tick_1: R.drawable.tick_0);
        ivTick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bFloatingWindowRunning){
					if(ivFloating != null){
						wm.removeView(ivFloating); 
					}
					if(ivCircle != null){
						if (bCircleVisible){
							wm.removeView(ivCircle); 
							bCircleVisible = false;
						}
						
					}
					if(ivBubble != null){
						if (bBubbleVisible){
							wm.removeView(ivBubble); 
							bBubbleVisible = false;
						}
					}
					
					ivTick.setImageResource(R.drawable.tick_0);
					bFloatingWindowRunning = false;
				}
				else{
					CreateFloatingWindow();
					ivTick.setImageResource(R.drawable.tick_1);
					bFloatingWindowRunning = true;
				}

			}
		});
        
        
    }

    private void CreateFloatingWindow(){
    	ivFloating = new ImageView(this);
    	ivCircle = new ImageView(this);
        ivCircle.setImageResource(R.drawable.circle_ui);
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
        
        //Get Header Y Position
        View vHeader = (View)findViewById(R.id.header);

        final WindowManager.LayoutParams wmParamsI = new WindowManager.LayoutParams();
        wmParamsI.type = 2002;   
        wmParamsI.format = 1; 
        wmParamsI.flags = 40;  
        wmParamsI.width = (int)(128 * fScale);
        wmParamsI.height = (int)(128 * fScale);  
        wmParamsI.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsI.x = (int)(pScreenSize.x - 128 * fScale);
        wmParamsI.y = (int)(vHeader.getY() - 128 * fScale);
        
        
        final WindowManager.LayoutParams wmParamsB = new WindowManager.LayoutParams();
        wmParamsB.type = 2002;   
        wmParamsB.format = 1; 
        wmParamsB.flags = 40;  
        wmParamsB.width = (int)(640 * fScale);
        wmParamsB.height = (int)(340 * fScale);  
        wmParamsB.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsB.x = wmParamsI.x - wmParamsB.width;
        wmParamsB.y = wmParamsI.y - wmParamsB.height;
        
        
        ivFloating.setImageResource(R.drawable.floating_icon);
        ivFloating.setOnTouchListener(new View.OnTouchListener() {
				@Override
        	    public boolean onTouch(View view, MotionEvent motionEvent) {
					if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
				        wmParamsI.x = (int)(motionEvent.getRawX() - wmParamsI.width/2);
				        wmParamsI.y = (int)(motionEvent.getRawY() - wmParamsI.height/2);
						wm.updateViewLayout(ivFloating, wmParamsI);	
						if(bBubbleVisible){
							wmParamsB.x = wmParamsI.x - wmParamsB.width;
					        wmParamsB.y = wmParamsI.y - wmParamsB.height;
					        wm.updateViewLayout(ivBubble, wmParamsB);	
						}
						return true;
					}
    		        return false;
        	    }
        });
        ivFloating.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ivBubble != null){
					if (bBubbleVisible){
						wm.removeView(ivBubble); 
						bBubbleVisible = false;
					}
				}
				
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
					// If Mouse Is Inside The Closing Area
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							780 * fCircleScale, 30 * fCircleScale,
							128 * fCircleScale, 128 * fCircleScale)){
						wm.removeView(ivCircle); 
						bCircleVisible = false;
					}
					// If Mouse Is Inside The Center Area
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							352 * fCircleScale, 352 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("MainActivity");
						return true;
						
					}
					// If Mouse Is Inside The First Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							168 * fCircleScale, 92 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer1");
						return true;
					}
					
					// If Mouse Is Inside The Second Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							470 * fCircleScale, 64 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer2");
						return true;
					}
					
					// If Mouse Is Inside The Third Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							674 * fCircleScale, 320 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer3");
						return true;
					}
					
					// If Mouse Is Inside The Forth Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							546 * fCircleScale, 622 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer4");
						return true;
					}
					
					// If Mouse Is Inside The Fifth Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							222 * fCircleScale, 634 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer5");
						return true;
					}
					
					// If Mouse Is Inside The Sixth Icon
					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
							18 * fCircleScale, 378 * fCircleScale,
							256 * fCircleScale, 256 * fCircleScale)){
						GoToActivity("Timer6");
						return true;
					}
					
					return true;
				}
		        return false;
    	    }
    });
        
     ivBubble = new ImageView(this);
     ivBubble.setImageResource(R.drawable.bubble);
     ivBubble.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bBubbleVisible){
					wm.removeView(ivBubble); 
					bBubbleVisible = false;
				}

			}
		});
     
     bBubbleVisible = true;
     wm.addView(ivBubble, wmParamsB); 
    }
    
    public boolean IsPointInsideRect(float PointX, float PointY, 
    		float RectX, float RectY, float RectWidth, float RectHeight){
    	return (PointX >= RectX) && (PointX < RectX + RectWidth) 
    			&& (PointY >= RectY) && (PointY < RectY + RectHeight) ;

    }
    
    public void GoToActivity(String ActivityName){
    	Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setComponent(new ComponentName("com.pujoy.charminder","com.pujoy.charminder."+ActivityName));
		intent.setPackage("com.pujoy.charminder");
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}



}
    

