package com.pujoy.charminder.other;

import java.io.FileOutputStream;

import android.content.Context;

public class Log {
	public static void w(String log) {
		try {
			FileOutputStream f;
			f = G.context.openFileOutput("log", Context.MODE_PRIVATE
					| Context.MODE_APPEND);
			f.write(log.getBytes());
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
