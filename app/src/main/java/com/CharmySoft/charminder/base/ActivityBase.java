/*
**  Class ActivityBase
**  src/com/CharmySoft/charminder/base/ActivityBase.java
*/
package com.CharmySoft.charminder.base;

import android.app.Activity;
import android.os.Bundle;

import com.CharmySoft.charminder.other.G;

public class ActivityBase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		G.context = this;
		super.onCreate(savedInstanceState);
	}
}
