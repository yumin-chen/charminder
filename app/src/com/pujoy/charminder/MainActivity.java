package com.pujoy.charminder;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	public static ArrayList<Reminder> ReminderList = new ArrayList<Reminder>();
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
	static TextView tvBubble;
	static WindowManager wm; 
	static WindowManager.LayoutParams wmParamsC;
	static WindowManager.LayoutParams wmParamsI;
	static WindowManager.LayoutParams wmParamsB;
	static WindowManager.LayoutParams wmParamsBt;
	
	private static final int REMINDING_PROCESS = 1;
	
	   private Handler mHandler = new Handler() {
	        @Override
			public void handleMessage(Message msg) {
	            if (msg.what == REMINDING_PROCESS){
        			for(int i=0; i<ReminderList.size(); i++){
        				System.out.println(ReminderList.get(i).time_to_remind.compareTo(Calendar.getInstance()));
        				if(ReminderList.get(i).validity && ReminderList.get(i).time_to_remind.compareTo(Calendar.getInstance()) <= 0){
            				ReminderList.get(i).Notify(getApplicationContext().getResources());
            				break;
            			}
            		}	
	            	mHandler.sendEmptyMessageDelayed(REMINDING_PROCESS, 1000);
	            }
	        }
	 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Screen Resolution And Calculate Scale
        getWindowManager().getDefaultDisplay().getSize(pScreenSize);
        fScaleY = pScreenSize.y > 960? 1: pScreenSize.y / 960;
        fScaleX = pScreenSize.x > 680? 1: pScreenSize.x / 680;
        fScale = fScaleX > fScaleY? fScaleY: fScaleX;
        
        mHandler.sendEmptyMessageDelayed(REMINDING_PROCESS, 1000);
        if(wm == null) wm =(WindowManager)getApplicationContext().getSystemService("window");
        
        final ImageView ivTick = (ImageView)findViewById(R.id.floatingwindow_tick);
        ivTick.setImageResource(bFloatingWindowRunning? R.drawable.tick_1: R.drawable.tick_0);
        ivTick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bFloatingWindowRunning){
					wm.removeView(ivFloating); 
					if (bCircleVisible){
							wm.removeView(ivCircle); 
							bCircleVisible = false;
					}

					if (bBubbleVisible){
							wm.removeView(ivBubble);
							wm.removeView(tvBubble);
							bBubbleVisible = false;
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
        
        if(wmParamsC == null) wmParamsC = new WindowManager.LayoutParams();
        if(wmParamsI == null) wmParamsI = new WindowManager.LayoutParams();
        if(wmParamsB == null) wmParamsB = new WindowManager.LayoutParams();
        if(wmParamsBt == null) wmParamsBt = new WindowManager.LayoutParams();
        
        
        
    	
        // Create ivFloating Object
        if (ivFloating == null){
        	ivFloating = new ImageView(this);
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
    					        wmParamsBt.x = wmParamsB.x + (int)(40 * fScale);
    					        wmParamsBt.y = wmParamsB.y + (int)(40 * fScale);  
    					        wm.updateViewLayout(ivBubble, wmParamsB);	
    					        wm.updateViewLayout(tvBubble, wmParamsBt);	
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
    						wm.removeView(tvBubble);
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
        }
        
        //Create ivCircle Object
        if(ivCircle == null){
        	ivCircle = new ImageView(this);
            ivCircle.setImageResource(R.drawable.circle_ui);
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
    						return true;
    					}
    					// If Mouse Is Inside The Center Area
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							352 * fCircleScale, 352 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("MainActivity");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    						
    					}
    					// If Mouse Is Inside The First Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							168 * fCircleScale, 92 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer1");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					// If Mouse Is Inside The Second Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							470 * fCircleScale, 64 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer2");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					// If Mouse Is Inside The Third Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							674 * fCircleScale, 320 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer3");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					// If Mouse Is Inside The Forth Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							546 * fCircleScale, 622 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer4");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					// If Mouse Is Inside The Fifth Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							222 * fCircleScale, 634 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer5");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					// If Mouse Is Inside The Sixth Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							18 * fCircleScale, 378 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer6");
    						wm.removeView(ivCircle); 
    						bCircleVisible = false;
    						return true;
    					}
    					
    					return true;
    				}
    		        return false;
        	    }
            });
            
        }
        
        // Create Bubble Object
        if(ivBubble == null){
        	View.OnClickListener bubbleListener = new View.OnClickListener() {
       			
       			@Override
       			public void onClick(View v) {
       				if(bBubbleVisible){
       					wm.removeView(ivBubble); 
       					wm.removeView(tvBubble); 
       					bBubbleVisible = false;
       				}

       			}
       		};
            
            ivBubble = new ImageView(this);
            ivBubble.setImageResource(R.drawable.bubble);
            ivBubble.setOnClickListener(bubbleListener);
            
            tvBubble = new TextView(this);
            tvBubble.setOnClickListener(bubbleListener);
            tvBubble.setTextColor(android.graphics.Color.rgb(228, 242, 254));
            tvBubble.setGravity(Gravity.CENTER);
        }
        
    }

    private void CreateFloatingWindow(){

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

        wmParamsI.type = 2002;   
        wmParamsI.format = 1; 
        wmParamsI.flags = 40;  
        wmParamsI.width = (int)(128 * fScale);
        wmParamsI.height = (int)(128 * fScale);  
        wmParamsI.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsI.x = (int)(pScreenSize.x - 128 * fScale);
        wmParamsI.y = (int)(vHeader.getY() - 128 * fScale);      
        wm.addView(ivFloating, wmParamsI); 
        
        
        PushFloatingBubble(getString(R.string.bubble_on_created));
        
    }
    
    public final static void PushFloatingBubble(CharSequence BubbleText){
    	if (bBubbleVisible){
    		wm.removeView(ivBubble);
    	    wm.removeView(tvBubble);
    	}
        wmParamsB.type = 2002;   
        wmParamsB.format = 1; 
        wmParamsB.flags = 40;  
        wmParamsB.width = (int)(640 * fScale);
        wmParamsB.height = (int)(340 * fScale);  
        wmParamsB.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsB.x = wmParamsI.x - wmParamsB.width;
        wmParamsB.y = wmParamsI.y - wmParamsB.height;   
     
        wmParamsBt.type = 2002;   
        wmParamsBt.format = 1; 
        wmParamsBt.flags = 40;  
        wmParamsBt.width = wmParamsB.width - (int)(80 * fScale);
        wmParamsBt.height = wmParamsB.height - (int)(64 * fScale);  
        wmParamsBt.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsBt.x = wmParamsB.x + (int)(40 * fScale);
        wmParamsBt.y = wmParamsB.y + (int)(40 * fScale);   
        
        
     bBubbleVisible = true;
     tvBubble.setTextSize((18-BubbleText.length()/12)*fScale);
     tvBubble.setText(BubbleText);
     

     wm.addView(ivBubble, wmParamsB);
     wm.addView(tvBubble, wmParamsBt);
     
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
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

    public final static void AddReminder(Reminder reminderToAdd){
    	ReminderList.add(reminderToAdd);
    }
    
    

}
    

