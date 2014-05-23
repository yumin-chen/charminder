package com.pujoy.charminder;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	static eSettings settings;
	public static ArrayList<Reminder> reminderList = new ArrayList<Reminder>();
	static float fScaleY;
	static float fScaleX;
	static float fScale;
	static boolean bCircleVisible;
	static boolean bBubbleVisible;
	static boolean bFloatingWindowRunning;
	static ImageView ivFloating;
	static RelativeLayout vCircle;
	static ImageView[] ivCircleItems;
	static ImageView ivCircleBg;
	static TextView tvCircleDescription;
	static int iOldCircleCurrent;
	static ImageView ivBubble;
	static TextView tvBubble;
	static WindowManager wm; 
	static TextView tvCircleItemDescription;
	static WindowManager.LayoutParams wmParamsI;
	static WindowManager.LayoutParams wmParamsB;
	static WindowManager.LayoutParams wmParamsBt;
	static WindowManager.LayoutParams wmParamsC;
	static DisplayMetrics metrics;

	private static final int NUM_CIRCLE_ITEMS = 6;
	private static final int REMINDING_PROCESS = 1;

	   private Handler mHandler = new Handler() {
	        @Override
			public void handleMessage(Message msg) {
	            if (msg.what == REMINDING_PROCESS){
        			for(int i=0; i<reminderList.size(); i++){
        				if(reminderList.get(i).validity && reminderList.get(i).time_to_remind.compareTo(Calendar.getInstance()) <= 0){
            				reminderList.get(i).Notify(getApplicationContext().getResources());
            				break;
            			}
        				if(reminderList.get(i).validity == false && settings.autoDeleteExpiredReminder){
        					DeleteReminder(i);
        					i--;
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
        
        settings = new eSettings();
        
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
        
        final ImageView ivEnabled = (ImageView)findViewById(R.id.floatingwindow_switch);
        ivEnabled.setImageResource(bFloatingWindowRunning? R.drawable.switch_1: R.drawable.switch_0);
        View.OnClickListener startRunningListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bFloatingWindowRunning){
					wm.removeView(ivFloating); 
					RemoveTheCircle();

					if (bBubbleVisible){
							wm.removeView(ivBubble);
							wm.removeView(tvBubble);
							bBubbleVisible = false;
					}
					
					ivEnabled.setImageResource(R.drawable.switch_0);
					bFloatingWindowRunning = false;
				}
				else{
					CreateFloatingWindow();
					ivEnabled.setImageResource(R.drawable.switch_1);
					bFloatingWindowRunning = true;
				}

			}
		};
		ivEnabled.setOnClickListener(startRunningListener);
        RelativeLayout running_layout = (RelativeLayout)findViewById(R.id.running_layout);
        running_layout.setOnTouchListener(mainKeyTouchListener);
        running_layout.setOnClickListener(startRunningListener);
    	
        // Create ivFloating Object
        if (ivFloating == null){
        	ivFloating = new ImageView(this);
            ivFloating.setImageResource(R.drawable.floating_icon);
            ivFloating.setOnTouchListener(new View.OnTouchListener() {
    				@Override
            	    public boolean onTouch(View view, MotionEvent motionEvent) {
    					switch(motionEvent.getAction()){
    					case MotionEvent.ACTION_DOWN:
    						CreateTheCircle();
    						return true;
    					case MotionEvent.ACTION_MOVE:
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
    						for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    							if(IsPointInsideRect(motionEvent.getRawX(),motionEvent.getRawY(),
    									wmParamsC.x+ivCircleItems[i].getX() - dpToPx(8),
    									wmParamsC.y+ivCircleItems[i].getY() - dpToPx(8),
    									ivCircleItems[i].getWidth() +  dpToPx(16),
    									ivCircleItems[i].getHeight() +  dpToPx(16))){
    								switch(i){
    								case 0:
        								tvCircleDescription.setText(getString(R.string.title_timer1));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.timer1_icon_a);
        								iOldCircleCurrent = 1;
        								break;
    								case 1:
        								tvCircleDescription.setText(getString(R.string.title_timer2));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.timer2_icon_a);
        								iOldCircleCurrent = 2;
        								break;
    								case 2:
        								tvCircleDescription.setText(getString(R.string.title_timer3));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.timer3_icon_a);
        								iOldCircleCurrent = 3;
        								break;
    								case 3:
        								tvCircleDescription.setText(getString(R.string.title_timer4));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.timer4_icon_a);
        								iOldCircleCurrent = 4;
        								break;
    								case 4:
        								tvCircleDescription.setText(getString(R.string.settings));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.settings_a);
        								iOldCircleCurrent = 5;
        								break;
    								case 5:
        								tvCircleDescription.setText(getString(R.string.reminder_list));
        								UpdateOldCircleCurrent();
        								ivCircleItems[i].setImageResource(R.drawable.reminderlist_a);
        								iOldCircleCurrent = 6;
        								break;
    								}
    						    	tvCircleDescription.setBackgroundColor(android.graphics.Color.argb(192, 48, 78, 98));
    								break;
    							}else{
    								if (i==NUM_CIRCLE_ITEMS-1){
    									tvCircleDescription.setText("");
    									tvCircleDescription.setBackgroundColor(android.graphics.Color.argb(
    											0, 48, 78, 98));
    									UpdateOldCircleCurrent();
    								}
    							
    							}
    						}
    						return true;
    					case MotionEvent.ACTION_UP:
    						RemoveTheCircle();
    						for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    							if(IsPointInsideRect(motionEvent.getRawX(),motionEvent.getRawY(),
    									wmParamsC.x+ivCircleItems[i].getX() - dpToPx(8),
    									wmParamsC.y+ivCircleItems[i].getY() - dpToPx(8),
    									ivCircleItems[i].getWidth() +  dpToPx(16),
    									ivCircleItems[i].getHeight() +  dpToPx(16))){
    								switch(i){
    								case 0:
        								GoToActivity(Timer1.class);
        								break;
    								case 1:
        								GoToActivity(Timer2.class);
        								break;
    								case 2:
        								GoToActivity(Timer3.class);
        								break;
    								case 3:
        								GoToActivity(Timer4.class);
        								break;
    								case 4:
        								GoToActivity(Settings.class);
        								break;
    								case 5:
        								GoToActivity(RemindersList.class);
        								break;
    								}
    								break;
    							}else{
    								if (i==NUM_CIRCLE_ITEMS-1)
    									tvCircleDescription.setText("");
    							}
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
        
      //Get Screen Resolution And Calculate Scale
        CalculateMetrics();
        
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CalculateMetrics();
        if(bBubbleVisible){
        	wm.updateViewLayout(ivBubble, wmParamsB);
        	wm.updateViewLayout(tvBubble, wmParamsBt);
		}
        if(bCircleVisible){
        	wm.updateViewLayout(vCircle, wmParamsC);
		}

    }
    private void CreateFloatingWindow(){
    	//Get Header Position
        View vHeader = (View)findViewById(R.id.head_p);

        wmParamsI.type = 2002;   
        wmParamsI.format = 1; 
        wmParamsI.flags = 40;  
        wmParamsI.width = (int)(0.3 * metrics.densityDpi);
        wmParamsI.height = (int)(0.3 * metrics.densityDpi);  
        wmParamsI.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsI.x = (int)(metrics.widthPixels - wmParamsI.width);
        wmParamsI.y = (int)(vHeader.getHeight() - wmParamsI.height);    
        
        
        wm.addView(ivFloating, wmParamsI); 
        PushFloatingBubble(getString(R.string.bubble_on_created));
        
    }
    
    public final static void PushFloatingBubble(CharSequence BubbleText){
    	if (bBubbleVisible){
    		wm.removeView(ivBubble);
    	    wm.removeView(tvBubble);
    	} 
    	
     bBubbleVisible = true;
     tvBubble.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18-BubbleText.length()/12);
     tvBubble.setText(BubbleText);
     
     UpdatePositionParams();
     wm.addView(ivBubble, wmParamsB);
     wm.addView(tvBubble, wmParamsBt);
     
    }
    
    public boolean IsPointInsideRect(float PointX, float PointY, 
    		float RectX, float RectY, float RectWidth, float RectHeight){
    	return (PointX >= RectX) && (PointX < RectX + RectWidth) 
    			&& (PointY >= RectY) && (PointY < RectY + RectHeight) ;

    }
    
    public void GoToActivity(Class<?> cls){
    	Intent intent = new Intent(this, cls);
		//intent.setComponent(new ComponentName("com.pujoy.charminder","com.pujoy.charminder."+ActivityName));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		intent = new Intent(this, BringToFront.class);
		BringToFront.Open(cls.getName());
		startActivity(intent);
	}

    public final static void AddReminder(Reminder reminderToAdd){
    	reminderList.add(reminderToAdd);
    }
    public final static void DeleteReminder(int index){
    	reminderList.remove(index);
    }
    
    private void CreateTheCircle(){
    	if (bCircleVisible)
    		return;
    	
    	CalculateMetrics();
    	int max =(metrics.widthPixels > metrics.heightPixels? metrics.heightPixels: metrics.widthPixels);
    	//vCircle = getLayoutInflater().inflate(R.layout.the_circle, rl);
        //Create ivCircleItems Objects
        if(vCircle == null){
        	vCircle = new RelativeLayout(getApplicationContext());
        	ivCircleBg = new ImageView(this);
        	ivCircleItems = new ImageView[NUM_CIRCLE_ITEMS];
        	tvCircleDescription = new TextView(this);
        	RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
        			(int)dpToPx(128), (int)dpToPx(128));
        	textParams.leftMargin = (wmParamsC.width - (int)dpToPx(128))/2;
        	textParams.topMargin = (wmParamsC.height - (int)dpToPx(128))/2;
        	tvCircleDescription.setGravity(Gravity.CENTER);
        	tvCircleDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        	tvCircleDescription.setTextColor(android.graphics.Color.rgb(228, 242, 254));
        	vCircle.addView(tvCircleDescription, textParams);
        	ivCircleBg.setImageResource(R.drawable.circle_bg);
        	vCircle.addView(ivCircleBg);
        	RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[NUM_CIRCLE_ITEMS]; 
        	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
        		ivCircleItems[i] = new ImageView(this);
        		params[i] = new RelativeLayout.LayoutParams((int) dpToPx(96), (int) dpToPx(96));
        		params[i].leftMargin = 0;
        		params[i].topMargin = 0;
        	}
        	ivCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
        	ivCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
        	ivCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
        	ivCircleItems[3].setImageResource(R.drawable.timer4_icon_c);
        	ivCircleItems[4].setImageResource(R.drawable.settings);
        	ivCircleItems[5].setImageResource(R.drawable.reminderlist);
        	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
            	vCircle.addView(ivCircleItems[i], params[i]);
        	}
        	
        }
    	wm.addView(vCircle, wmParamsC);

    	tvCircleDescription.setText("");
    	tvCircleDescription.setBackgroundColor(android.graphics.Color.argb(0, 48, 78, 98));
    	Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
    	ivCircleBg.startAnimation(animation);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		float tox = (float) (wmParamsC.width/2 + max*0.55/2 * Math.sin(((360/NUM_CIRCLE_ITEMS) * i)
    				* Math.PI / 180.0) -  dpToPx(96)/2);
    		float toy = (float) (wmParamsC.height/2 + max*0.55/2 * Math.cos(((360/NUM_CIRCLE_ITEMS) * i)
    				* Math.PI / 180.0) -  dpToPx(96)/2);
    		float fromx = (float) (wmParamsC.width/2 + max*0.55/2 * Math.sin(((360/NUM_CIRCLE_ITEMS) * i)
    				* Math.PI / 180.0)/4 -  dpToPx(96)/2);
    		float fromy = (float) (wmParamsC.height/2 + max*0.55/2 * Math.cos(((360/NUM_CIRCLE_ITEMS) * i)
    				* Math.PI / 180.0)/4 -  dpToPx(96)/2);
    		ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(ivCircleItems[i], "y", fromy, toy);
    		aCircleItemsY.setDuration(200);
    		ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(ivCircleItems[i], "x", fromx, tox);
    		aCircleItemsX.setDuration(200);
    		aCircleItemsY.start();
    		aCircleItemsX.start();    
    	}

			
    	bCircleVisible = true;
    };
    
    private void RemoveTheCircle(){
    	if(!bCircleVisible)
    		return;
    	wm.removeView(vCircle);
    	bCircleVisible = false;
    	
    }
    
    public static float dpToPx(float dp){
    	return dp *(metrics.densityDpi / 160f);
    }
    
    private void UpdateOldCircleCurrent(){
    	switch(iOldCircleCurrent){
		case 1:
			ivCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
		case 2:
			ivCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
		case 3:
			ivCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
		case 4:
			ivCircleItems[3].setImageResource(R.drawable.timer4_icon_c);
		case 5:
			ivCircleItems[4].setImageResource(R.drawable.settings);
		case 6:
			ivCircleItems[5].setImageResource(R.drawable.reminderlist);
		}
		iOldCircleCurrent = 0;
    }
    private void CalculateMetrics(){
    	metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        fScaleY = metrics.heightPixels / 960f;
        fScaleY = fScaleY > 1? 1: fScaleY == 0? 1: fScaleY;
        fScaleX = metrics.widthPixels / 680f;
        fScaleX = fScaleX > 1? 1: fScaleX == 0? 1: fScaleX;
        fScale = fScaleX > fScaleY? fScaleY: fScaleX;
        
        
        if(wmParamsI == null) wmParamsI = new WindowManager.LayoutParams();
        if(wmParamsB == null) wmParamsB = new WindowManager.LayoutParams();
        if(wmParamsBt == null) wmParamsBt = new WindowManager.LayoutParams();
        if(wmParamsC == null) wmParamsC = new WindowManager.LayoutParams();
        
        UpdatePositionParams();
        

        
    }
    private static void UpdatePositionParams(){
        
    	int max =(metrics.widthPixels > metrics.heightPixels? metrics.heightPixels: metrics.widthPixels);
    	wmParamsC.type = 2002;   
    	wmParamsC.format = 1; 
    	wmParamsC.flags = 40;  
    	wmParamsC.width = (int)(max*0.8);
    	wmParamsC.height = (int)(max*0.8);
        wmParamsC.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsC.x = (metrics.widthPixels-wmParamsC.width)/2;
        wmParamsC.y = (metrics.heightPixels-wmParamsC.height)/2;   
        

        wmParamsB.type = 2002;   
        wmParamsB.format = 1; 
        wmParamsB.flags = 40;  
        wmParamsB.width = (int)(640 * fScale);
        wmParamsB.height = (int)(340 * fScale);  
        wmParamsB.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsB.x = wmParamsI.x - wmParamsB.width;
        wmParamsB.x = wmParamsB.x < 0? 0:
        	wmParamsB.x + wmParamsB.width> metrics.widthPixels?
        			metrics.widthPixels - wmParamsB.width: wmParamsB.x;
        wmParamsB.y = wmParamsI.y - wmParamsB.height;  
        wmParamsB.y = wmParamsB.y < 0? 0:
        	wmParamsB.y + wmParamsB.height> metrics.heightPixels? 
        			metrics.heightPixels - wmParamsB.height: wmParamsB.y;
     
        wmParamsBt.type = 2002;   
        wmParamsBt.format = 1; 
        wmParamsBt.flags = 40;  
        wmParamsBt.width = wmParamsB.width - (int)(80 * fScale);
        wmParamsBt.height = wmParamsB.height - (int)(64 * fScale);  
        wmParamsBt.gravity = Gravity.LEFT | Gravity.TOP;
        wmParamsBt.x = wmParamsB.x + (int)(40 * fScale);
        wmParamsBt.y = wmParamsB.y + (int)(40 * fScale);  
    }
}
    

