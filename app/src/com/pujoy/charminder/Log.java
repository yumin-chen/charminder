package com.pujoy.charminder;

import java.io.FileOutputStream;

import static com.pujoy.charminder.MainActivity.con;
import android.content.Context;

public class Log {
	public static void w(String log)
	{
		try {
			FileOutputStream f;
			f = con.openFileOutput("log", Context.MODE_PRIVATE | Context.MODE_APPEND);
			f.write(log.getBytes());
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
