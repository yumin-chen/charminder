package com.pujoy.charminder;

import java.io.FileOutputStream;

import static com.pujoy.charminder.MainActivity.con;
import android.content.Context;

public class Log {
	FileOutputStream f;
	public Log(String sLog)
	{
		try {
			f = con.openFileOutput("log", Context.MODE_PRIVATE | Context.MODE_APPEND);
			f.write(sLog.getBytes());
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
