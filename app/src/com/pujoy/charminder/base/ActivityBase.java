/*
**  Class ActivityBase
**  src/com/pujoy/charminder/base/ActivityBase.java
*/
package com.pujoy.charminder.base;

import com.pujoy.charminder.other.G;

import android.app.Activity;
import android.os.Bundle;

public class ActivityBase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		G.context = this;
		super.onCreate(savedInstanceState);
	}
}
