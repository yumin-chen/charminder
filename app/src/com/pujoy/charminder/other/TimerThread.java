package com.pujoy.charminder.other;

import java.util.Calendar;

import com.pujoy.charminder.activities.ReminderListActivity;
import com.pujoy.charminder.helper.Helper;

import android.os.Handler;
import android.os.Message;

public class TimerThread extends Handler {
	private static boolean bCreated;

	private static final int REMINDING_PROCESS = 1;
	private static final int REMINDING_WAITING_TIME = 1000;
	private static final int MOVING_ICON_PROCESS = 2;
	private static final int MOVING_ICON_WAITING_TIME = 1;

	public TimerThread() {
		if (bCreated)
			return;
		sendEmptyMessageDelayed(REMINDING_PROCESS, REMINDING_WAITING_TIME);
		bCreated = true;
	}

	public void destroy() {
		bCreated = false;
	}

	public void moveIconToCorner() {
		if (G.mCharmy != null && G.mCharmy.MoveToCorner()) {
			sendEmptyMessageDelayed(MOVING_ICON_PROCESS,
					MOVING_ICON_WAITING_TIME);
		}
	}

	@Override
	public void handleMessage(Message msg) {
		if (!bCreated)
			return;
		if (msg.what == REMINDING_PROCESS) {
			for(int i=0; i<G.reminders.size(); i++){
				if(G.reminders.get(i).bValidity && 
						G.reminders.get(i).mTimeToRemind.compareTo(Calendar.getInstance()) <= 0){
					G.reminders.get(i).Notify();
					if(G.reminders.get(i).bValidity == false && G.settings.bAutoDeleteExpiredReminder){
						G.reminders.remove(i);
    					i--;
    				}
					G.reminders.save();
    				break;
    			}
    		}	
			if (G.mCharmy.bCreated) {
				if (G.mCharmy.mBubble.bCreated) {
					G.mCharmy.mBubble.iTimer++;
					if (G.mCharmy.mBubble.iTimer > G.settings.iBubbleTimeOut) {
						G.mCharmy.mBubble.remove();
					}
				}
			}
			if (G.context instanceof ReminderListActivity){
				ReminderListActivity.updateTime();
			}
			if (Helper.mFloatingText != null
					&& Helper.mFloatingText.isCreated()) {
				Helper.mFloatingText.iTimer--;
				if (Helper.mFloatingText.iTimer <= 0) {
					Helper.mFloatingText.remove();
					Helper.mFloatingText = null;
				}
			}
			sendEmptyMessageDelayed(REMINDING_PROCESS, REMINDING_WAITING_TIME);
		} else if (msg.what == MOVING_ICON_PROCESS) {
			if (G.mCharmy.bCreated) {
				if (G.mCharmy.MoveToCorner()) {
					sendEmptyMessageDelayed(MOVING_ICON_PROCESS,
							MOVING_ICON_WAITING_TIME);
				}
			}
		}
	}

}
