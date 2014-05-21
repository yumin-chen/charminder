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
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
	public static ArrayList<Reminder> reminderList = new ArrayList<Reminder>();
	static float fScaleY;
	static float fScaleX;
	static float fScale;
	static float fCircleScale;
	static boolean bCircleVisible;
	static boolean bBubbleVisible;
	static boolean bFloatingWindowRunning;
	static ImageView ivFloating;
	static ImageView ivCircle;
	static ImageView[] ivCircleItems;
	static ImageView ivBubble;
	static TextView tvBubble;
	static WindowManager wm; 
	static WindowManager.LayoutParams wmParamsC;
	static WindowManager.LayoutParams wmParamsI;
	static WindowManager.LayoutParams wmParamsB;
	static WindowManager.LayoutParams wmParamsBt;
	static WindowManager.LayoutParams[] wmParamsCItems;
	static DisplayMetrics metrics;
	
	private static final int NUM_CIRCLE_ITEMS = 6;
	private static final int REMINDING_PROCESS = 1;
	
	   private Handler mHandler = new Handler() {
	        @Override
			public void handleMessage(Message msg) {
	            if (msg.what == REMINDING_PROCESS){
        			for(int i=0; i<reminderList.size(); i++){
        				System.out.println(reminderList.get(i).time_to_remind.compareTo(Calendar.getInstance()));
        				if(reminderList.get(i).validity && reminderList.get(i).time_to_remind.compareTo(Calendar.getInstance()) <= 0){
            				reminderList.get(i).Notify(getApplicationContext().getResources());
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
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        pScreenSize.x = metrics.widthPixels;
        pScreenSize.y = metrics.heightPixels;
        fScaleY = pScreenSize.y / 960f;
        fScaleY = fScaleY > 1? 1: fScaleY == 0? 1: fScaleY;
        fScaleX = pScreenSize.x / 680f;
        fScaleX = fScaleX > 1? 1: fScaleX == 0? 1: fScaleX;
        fScale = fScaleX > fScaleY? fScaleY: fScaleX;
        
        
        mHandler.sendEmptyMessageDelayed(REMINDING_PROCESS, 1000);
        if(wm == null) wm =(WindowManager)getApplicationContext().getSystemService("window");
        
        
		View.OnTouchListener mainKeyTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(android.graphics.Color.rgb(32, 170, 170));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
					break;
				}
				return false;
    	    }
		};
        
        final ImageView ivTick = (ImageView)findViewById(R.id.floatingwindow_tick);
        ivTick.setImageResource(bFloatingWindowRunning? R.drawable.tick_1: R.drawable.tick_0);
        View.OnClickListener startRunningListener = new View.OnClickListener() {
			
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
		};
        ivTick.setOnClickListener(startRunningListener);
        RelativeLayout running_layout = (RelativeLayout)findViewById(R.id.running_layout);
        running_layout.setOnTouchListener(mainKeyTouchListener);
        running_layout.setOnClickListener(startRunningListener);
        RelativeLayout reminderlist_layout = (RelativeLayout)findViewById(R.id.reminderlist_layout);
        reminderlist_layout.setOnTouchListener(mainKeyTouchListener);
        reminderlist_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			GoToActivity("RemindersList");	
			}
        });
        RelativeLayout settings_layout = (RelativeLayout)findViewById(R.id.settings_layout);
        settings_layout.setOnTouchListener(mainKeyTouchListener);
        settings_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			GoToActivity("Settings");	
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
    					RemoveTheCircle();

    				}
    				else{
    					CreateTheCircle();
    				}

    			}
    		});
        }
        
        
        //Create ivCircleItems Objects
        if(ivCircleItems == null){
        	ivCircleItems = new ImageView[NUM_CIRCLE_ITEMS];
        	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
        		ivCircleItems[i] = new ImageView(this);
        	}
        	ivCircleItems[0].setImageResource(R.drawable.timer1_icon);
        	ivCircleItems[1].setImageResource(R.drawable.timer2_icon);
        	ivCircleItems[2].setImageResource(R.drawable.timer3_icon);
        	ivCircleItems[3].setImageResource(R.drawable.timer4_icon);
        	ivCircleItems[4].setImageResource(R.drawable.settings);
        	ivCircleItems[5].setImageResource(R.drawable.reminderlist);
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
        					RemoveTheCircle();
    						return true;
    					}
    					// If Mouse Is Inside The Center Area
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							352 * fCircleScale, 352 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("MainActivity");
        					RemoveTheCircle();
    						return true;
    						
    					}
    					// If Mouse Is Inside The First Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							168 * fCircleScale, 92 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer1");
        					RemoveTheCircle();
    						return true;
    					}
    					
    					// If Mouse Is Inside The Second Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							470 * fCircleScale, 64 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer2");
        					RemoveTheCircle();
    						return true;
    					}
    					
    					// If Mouse Is Inside The Third Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							674 * fCircleScale, 320 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer3");
        					RemoveTheCircle();
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
        					RemoveTheCircle();
    						return true;
    					}
    					
    					// If Mouse Is Inside The Sixth Icon
    					if (IsPointInsideRect(motionEvent.getX(), motionEvent.getY(),
    							18 * fCircleScale, 378 * fCircleScale,
    							256 * fCircleScale, 256 * fCircleScale)){
    						GoToActivity("Timer6");
        					RemoveTheCircle();
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
        wmParamsC.width = (int)((pScreenSize.x > pScreenSize.y? pScreenSize.y: pScreenSize.x) * 0.9);
        wmParamsC.width = wmParamsC.width > 960? 960: wmParamsC.width;
        wmParamsC.height = wmParamsC.width;  
        wmParamsC.x = (pScreenSize.x - wmParamsC.width)/2;
        wmParamsC.y = (pScreenSize.y - wmParamsC.height)/2;
        
        		
        fCircleScale = wmParamsC.width/960f;
        
        //Get Header Y Position
        View vHeader = (View)findViewById(R.id.head_p);

        wmParamsI.type = 2002;   
        wmParamsI.format = 1; 
        wmParamsI.flags = 40;  
        wmParamsI.width = (int)(96 * fScale);
        wmParamsI.height = (int)(96 * fScale);  
        wmParamsI.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsI.x = (int)(pScreenSize.x - wmParamsI.width);
        wmParamsI.y = (int)(vHeader.getHeight() - wmParamsI.height);      
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
        wmParamsB.x = wmParamsB.x < 0? 0: wmParamsB.x;
        wmParamsB.y = wmParamsI.y - wmParamsB.height;  
        wmParamsB.y = wmParamsB.y < 0? 0: wmParamsB.y;
     
        wmParamsBt.type = 2002;   
        wmParamsBt.format = 1; 
        wmParamsBt.flags = 40;  
        wmParamsBt.width = wmParamsB.width - (int)(80 * fScale);
        wmParamsBt.height = wmParamsB.height - (int)(64 * fScale);  
        wmParamsBt.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsBt.x = wmParamsB.x + (int)(40 * fScale);
        wmParamsBt.y = wmParamsB.y + (int)(40 * fScale);   
        
        
     bBubbleVisible = true;
     tvBubble.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18-BubbleText.length()/12);
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
    	reminderList.add(reminderToAdd);
    }
    
    private void CreateTheCircle(){
    	wmParamsCItems = new WindowManager.LayoutParams[NUM_CIRCLE_ITEMS];
    	int max =(pScreenSize.x > pScreenSize.y? pScreenSize.y: pScreenSize.x);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		wmParamsCItems[i] = new WindowManager.LayoutParams();
    		wmParamsCItems[i].type = 2002;   
    		wmParamsCItems[i].format = 1; 
    		wmParamsCItems[i].flags = 40;  
    		wmParamsCItems[i].gravity = Gravity.LEFT | Gravity.TOP;
    		wmParamsCItems[i].width = (int)(0.4 * metrics.densityDpi);
    		wmParamsCItems[i].height = wmParamsCItems[i].width;  
    		wmParamsCItems[i].x = (int)(max/2 + max*0.6/2 * Math.sin(((360/NUM_CIRCLE_ITEMS) * i)
    				* Math.PI / 180.0) - wmParamsCItems[i].width/2);
    		wmParamsCItems[i].y = (int)(max/2 + max*0.6/2 * Math.cos(((360/NUM_CIRCLE_ITEMS) * i) 
    				* Math.PI / 180.0) - wmParamsCItems[i].height/2);
    		wm.addView(ivCircleItems[i], wmParamsCItems[i]);
    		;
    	}
    	
    	
    }
    
    private void RemoveTheCircle(){
    	
    }

}
    

