/*
**  Class G
**  src/com/CharmySoft/charminder/other/G.java
*/
package com.CharmySoft.charminder.other;

import android.content.Context;
import android.content.Intent;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.data.ReminderList;
import com.CharmySoft.charminder.data.Settings;
import com.CharmySoft.charminder.helper.FunctionWrapper;
import com.CharmySoft.charminder.helper.PopDialog;
import com.CharmySoft.charminder.views.Charmy;

//Global variables
public class G{
	public static Charmy mCharmy;
	public static TimerThread mTimerThread;
	public static Settings settings;
	public static ReminderList reminders;
	public static Context context;

	// Return 1 if the current system language is Chinese, 0 if English.
	public static int getLanguage() {
		return context.getResources().getString(R.string.floating_icon_name)
				.equals("Charmy") ? 0 : 1;
	}

	public static void goToActivity(Class<?> cls) {
		context.startActivity(new Intent(context, cls));
	}

	public static void exit() {
		PopDialog dialog = new PopDialog(G.context.getResources().getString(
				R.string.onExit_content,
				G.context.getResources().getString(R.string.app_name)),
				G.context.getResources().getString(R.string.onExit_title),
				new FunctionWrapper() {
					@Override
					public void function() {
						if (mCharmy != null && mCharmy.isCreated()) {
							mCharmy.remove();
							mCharmy.pushBubble(G.context.getResources()
									.getString(R.string.b_exit));
						}

					}
				}, new FunctionWrapper() {
					@Override
					public void function() {
						if (mCharmy != null && mCharmy.isCreated()) {
							mCharmy.remove();
							mCharmy.pushBubble(G.context.getResources()
									.getString(R.string.b_exit_completely));
						}
						context.stopService(new Intent(context, MainService.class));
					}
				});
		dialog.setOkText(G.context.getResources().getString(R.string.yes));
		dialog.setCancelText(G.context.getResources().getString(R.string.no));
		dialog.create();
	}
}
