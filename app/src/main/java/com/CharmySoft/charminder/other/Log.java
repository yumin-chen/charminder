/*
**  Class Log
**  src/com/CharmySoft/charminder/other/Log.java
*/
package com.CharmySoft.charminder.other;

import android.content.Context;

import java.io.FileOutputStream;

public class Log {
	public static void w(String log) {
		android.util.Log.d("DEBUG_Charmy", log);
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
