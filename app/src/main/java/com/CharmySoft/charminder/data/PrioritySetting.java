/*
**  Class PrioritySetting
**  src/com/CharmySoft/charminder/data/PrioritySetting.java
*/
package com.CharmySoft.charminder.data;

public class PrioritySetting {
	public boolean bBubble;
	public boolean bNotification;
	public boolean bWakeScreen;
	public boolean bVibrate;
	public boolean bSound;

	public PrioritySetting(String settingString) {
		bBubble = settingString.charAt(0) != '0';
		bNotification = settingString.charAt(1) != '0';
		bWakeScreen = settingString.charAt(2) != '0';
		bVibrate = settingString.charAt(3) != '0';
		bSound = settingString.charAt(4) != '0';
	}

	public String generateString() {
		byte[] bytes = new byte[5];
		bytes[0] = bBubble ? (byte) '1' : (byte) '0';
		bytes[1] = bNotification ? (byte) '1' : (byte) '0';
		bytes[2] = bWakeScreen ? (byte) '1' : (byte) '0';
		bytes[3] = bVibrate ? (byte) '1' : (byte) '0';
		bytes[4] = bSound ? (byte) '1' : (byte) '0';
		return new String(bytes);
	}
}
