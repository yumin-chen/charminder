package com.pujoy.charminder;

import android.os.Handler;
import android.os.Message;
import static com.pujoy.charminder.MainActivity.charmy;

public class TimerThread extends Handler{
	private static final int REMINDING_PROCESS = 1;
	private static final int REMINDING_WAITING_TIME = 1000;
	private static final int MOVING_ICON_PROCESS = 2;
	private static final int MOVING_ICON_WAITING_TIME = 1;
	private static boolean bCreated;
	
	public TimerThread(){
		if (bCreated)
			return;
		sendEmptyMessageDelayed(REMINDING_PROCESS, REMINDING_WAITING_TIME);
		bCreated = true;
	}
	
	public void moveIconToCorner(){
		if(charmy != null && charmy.MoveToCorner()){
			sendEmptyMessageDelayed(MOVING_ICON_PROCESS, MOVING_ICON_WAITING_TIME);
		}
	}
	
	@Override
	public void handleMessage(Message msg) {
		if (!bCreated)
			return;
        if (msg.what == REMINDING_PROCESS){
        	if(charmy.bCreated){
        		if(charmy.bubble.bCreated){
    				charmy.bubble.iTimer ++;
    				if(charmy.bubble.iTimer > 5){
    					charmy.bubble.remove();
    				}
    			}	
        	}
        	if(Constants.floatingText != null && Constants.floatingText.isCreated()){
        		Constants.floatingText.iTimer --;
        		if(Constants.floatingText.iTimer <= 0){
        			Constants.floatingText.remove();
        			Constants.floatingText = null;
        		}
        	}
        	sendEmptyMessageDelayed(REMINDING_PROCESS, REMINDING_WAITING_TIME);
        } else if(msg.what == MOVING_ICON_PROCESS){
        	if(charmy.bCreated){
        		if(charmy.MoveToCorner()){
        			sendEmptyMessageDelayed(MOVING_ICON_PROCESS, MOVING_ICON_WAITING_TIME);
        		}
        	}
        }
    }

}
