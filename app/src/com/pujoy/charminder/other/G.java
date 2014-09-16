package com.pujoy.charminder.other;

import android.content.Context;
import android.content.Intent;

import com.pujoy.charminder.R;
import com.pujoy.charminder.data.Settings;
import com.pujoy.charminder.helper.PopDialog;
import com.pujoy.charminder.helper.PopDialog.FunctionWrapper;
import com.pujoy.charminder.views.Charmy;

//Global variables
public class G{
	public static Charmy mCharmy;
	public static TimerThread mTimerThread;
	public static Settings settings;
	public static Context context;

	public static int getLanguage() {
		return context.getResources().getString(R.string.floating_icon_name)
				.equals("Charmy") ? 0 : 1;
	}

	public static void goToActivity(Class<?> cls) {
		context.startActivity(new Intent(context, cls));
		// Intent intent = new Intent(context, BringToFront.class);
		// BringToFront.sName = cls.getName();
		// context.startActivity(intent);

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
							mCharmy.PushBubble(G.context.getResources()
									.getString(R.string.b_exit));
						}

					}
				}, new FunctionWrapper() {
					@Override
					public void function() {
						if (mCharmy != null && mCharmy.isCreated()) {
							mCharmy.remove();
							mCharmy.PushBubble(G.context.getResources()
									.getString(R.string.b_exit_completely));
						}
						mTimerThread.destroy();
						mTimerThread = null;
					}
				});
		dialog.setOkText(G.context.getResources().getString(R.string.yes));
		dialog.setCancelText(G.context.getResources().getString(R.string.no));
		dialog.create();
	}
}
