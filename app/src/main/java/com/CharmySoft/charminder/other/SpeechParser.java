/**
 **  Class SpeechParser
 **  src/com/CharmySoft/charminder/other/SpeechParser.java
 **/
package com.CharmySoft.charminder.other;

import java.util.ArrayList;
import java.util.Calendar;

public class SpeechParser {
	ArrayList<String> mSpeechText;
	int iLang;
	public String sTimePhrase;

	public SpeechParser(ArrayList<String> speechText, int language) {
		mSpeechText = speechText;
		iLang = language;
	}

	static public class ParseResult {
		public String sTimePhrase;
		public Calendar mCalendar;
		public int iHit;
	}

	public SpeechParser(ArrayList<String> speechText) {
		this(speechText, 0);
	}

	public void setLanguage(int language) {
		iLang = language;
	}

	public void setSpeechText(ArrayList<String> speechText) {
		mSpeechText = speechText;
	}

	public ParseResult parse() {
		ParseResult[] parseResult = new ParseResult[mSpeechText.size()];
		for (int i = 0; i < mSpeechText.size(); i++) {
			switch (iLang) {
			case 0:
				parseResult[i] = parseChinese(mSpeechText.get(i));
				break;
			case 1:
				parseResult[i] = parseEnglish(mSpeechText.get(i));
				break;
			}
		}
		// Compare results and get the most reliable one
		int maxHit = 0;
		int maxMember = 0;
		for (int i = 0; i <= (parseResult.length + 1) / 2 - 1; i++) {
			for (int j = 0; j < parseResult.length; j++) {
				if (parseResult[i].sTimePhrase
						.compareTo(parseResult[j].sTimePhrase) == 0) {
					parseResult[i].iHit++;
					if (parseResult[i].iHit > maxHit) {
						maxHit = parseResult[i].iHit;
						maxMember = i;
					}
				}
			}
		}
		return parseResult[maxMember];
	}

	private static boolean arrayContain(char[] array, char element) {
		for (int i = 0; i < array.length; i++) {
			if (element == array[i]) {
				return true;
			}
		}
		return false;
	}

	public static ParseResult parseChinese(String speechText) {
		ParseResult r = new ParseResult();
		Calendar cal = Calendar.getInstance();
		final char[] arabicNumbers = new char[] { '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9' };
		final char[] chineseNumbers = new char[] { '零', '一', '二', '三', '四',
				'五', '六', '七', '八', '九' };
		final char CHINESE_TEN = '十';
		boolean isTimeSet = false;
		boolean isDateSet = false;
		String timePhrase_time = new String();
		String timePhrase_am_pm = new String();
		String timePhrase_date = new String();
		String timePhrase_week = new String();
		String timePhrase_prefix = new String();
		int index = speechText.indexOf(CHINESE_TEN);
		while (index != -1) {
			boolean afterNumber = false;
			boolean beforeNumber = false;
			if (index >= 1) {
				afterNumber = (arrayContain(chineseNumbers,
						speechText.charAt(index - 1)));
			}
			if (index + 1 < speechText.length()) {
				beforeNumber = (arrayContain(chineseNumbers,
						speechText.charAt(index + 1)));
			}
			speechText = speechText.replaceFirst(String.valueOf(CHINESE_TEN),
					afterNumber ? beforeNumber ? "" : "0" : beforeNumber ? "1"
							: "10");

			index = speechText.indexOf(CHINESE_TEN);
		}
		for (int i = 0; i < arabicNumbers.length; i++) {
			speechText = speechText
					.replace(chineseNumbers[i], arabicNumbers[i]);
		}

		// Replace synonyms
		speechText = speechText.replace("下个星期", "下周");
		speechText = speechText.replace("星期天", "星期0");
		speechText = speechText.replace("星期日", "星期0");
		speechText = speechText.replace("号", "日");
		speechText = speechText.replace("星期", "周");
		speechText = speechText.replace("点半", "点30");
		speechText = speechText.replace("半个小时", "30分钟");
		speechText = speechText.replace("半小时", "30分钟");
		speechText = speechText.replace("个小时后", "小时后");
		speechText = speechText.replace("小时之后", "小时后");
		speechText = speechText.replace("分钟之后", "分钟后");

		// Remove those extra spaces that interfere with parsing
		speechText = speechText.replace(" 点", "点");
		speechText = speechText.replace("点 ", "点");
		speechText = speechText.replace("过 ", "过");
		speechText = speechText.replace(" 年", "年");
		speechText = speechText.replace(" 月", "月");
		speechText = speechText.replace(" 日", "日");
		speechText = speechText.replace(" 周", "周");
		speechText = speechText.replace("周 ", "周");
		
		final char CHINESE_DIAN = '点';
		index = speechText.indexOf(CHINESE_DIAN);
		while (index != -1) {
			boolean afterNumber = false;
			boolean beforeNumber = false;

			if (index >= 1) {
				afterNumber = (Character.isDigit(speechText.charAt(index - 1)));
			}
			if (index + 1 < speechText.length()) {
				if (speechText.charAt(index + 1) == '过') {
					beforeNumber = (Character.isDigit(speechText
							.charAt(index + 2)));
				} else {
					beforeNumber = (Character.isDigit(speechText
							.charAt(index + 1)));
				}
			}
			if (afterNumber) {
				if (beforeNumber) {
					int start = speechText.charAt(index + 1) == '过' ? index + 2
							: index + 1;
					int i = start;
					while (i < speechText.length()
							&& Character.isDigit(speechText.charAt(i))) {
						i++;
					}
					try {
						cal.set(Calendar.MINUTE,
								Integer.valueOf(speechText.substring(start, i)));
					} catch (Exception e) {
						Log.w(e.getMessage());
					} finally {
					}
				}
				int i = index - 1;
				while (i >= 0 && Character.isDigit(speechText.charAt(i))) {
					i--;
				}
				try {
					cal.set(Calendar.HOUR,
							Integer.valueOf(speechText.substring(i + 1, index)));
					isTimeSet = true;
					timePhrase_time = cal.get(Calendar.HOUR) + ":"
							+ cal.get(Calendar.MINUTE);
				} catch (Exception e) {
					Log.w(e.getMessage());
				} finally {
				}
				break;
			}
			index = speechText.indexOf(CHINESE_DIAN, index + 1);
		}

		index = speechText.indexOf('月');
		while (index != -1) {
			boolean afterNumber = false;
			boolean beforeNumber = false;

			if (index >= 1) {
				afterNumber = (Character.isDigit(speechText.charAt(index - 1)));
			}
			if (index + 1 < speechText.length()) {
				beforeNumber = (Character.isDigit(speechText.charAt(index + 1)));
			}
			if (afterNumber) {
				if (beforeNumber) {
					int start = index + 1;
					int i = start;
					while (i < speechText.length()
							&& Character.isDigit(speechText.charAt(i))) {
						i++;
					}
					try {
						cal.set(Calendar.DAY_OF_MONTH,
								Integer.valueOf(speechText.substring(start, i)));
					} catch (Exception e) {
						Log.w(e.getMessage());
					} finally {
					}
				}
				int i = index - 1;
				while (i >= 0 && Character.isDigit(speechText.charAt(i))) {
					i--;
				}
				try {
					cal.set(Calendar.MONTH,
							Integer.valueOf(speechText.substring(i + 1, index)) - 1);
					isDateSet = true;
					timePhrase_date = (cal.get(Calendar.MONTH) + 1) + "月"
							+ cal.get(Calendar.DAY_OF_MONTH) + "日";
				} catch (Exception e) {
					Log.w(e.getMessage());
				} finally {
				}
				break;
			}
			index = speechText.indexOf('月', index + 1);
		}

		if (!isDateSet) {
			index = speechText.indexOf('日');
			while (index != -1) {
				boolean afterNumber = false;
				if (index >= 1) {
					afterNumber = (Character.isDigit(speechText
							.charAt(index - 1)));
				}
				if (afterNumber) {
					int i = index - 1;
					while (i >= 0 && Character.isDigit(speechText.charAt(i))) {
						i--;
					}
					try {
						cal.set(Calendar.DAY_OF_MONTH, Integer
								.valueOf(speechText.substring(i + 1, index)));
						isDateSet = true;
						timePhrase_date = cal.get(Calendar.DAY_OF_MONTH) + "日";
					} catch (Exception e) {
						Log.w(e.getMessage());
					} finally {
					}
					break;
				}
				index = speechText.indexOf('日', index + 1);
			}
		}

		index = speechText.indexOf('周');
		while (index != -1) {
			if (index + 1 < speechText.length()
					&& Character.isDigit(speechText.charAt(index + 1))) {
				int start = index + 1;
				int i = start;
				while (i < speechText.length()
						&& Character.isDigit(speechText.charAt(i))) {
					i++;
				}
				try {
					int j = Integer.valueOf(speechText.substring(start, i));
					cal.set(Calendar.DAY_OF_WEEK, j + 1);
					isDateSet = true;
					if (j == 0) {
						timePhrase_week = "周日";
					} else {
						timePhrase_week = "周"
								+ chineseNumbers[cal.get(Calendar.DAY_OF_WEEK) - 1];
					}
				} catch (Exception e) {
					Log.w(e.getMessage());
				} finally {
				}
				break;
			}
			index = speechText.indexOf('周', index + 1);
		}

		if (speechText.indexOf("大后天") != -1) {
			cal.add(Calendar.DAY_OF_MONTH, 3);
			timePhrase_prefix = "大后天";
		} else if (speechText.indexOf("后天") != -1) {
			cal.add(Calendar.DAY_OF_MONTH, 2);
			timePhrase_prefix = "后天";
		} else if (speechText.indexOf("明天") != -1) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			timePhrase_prefix = "明天";
		} else if (speechText.indexOf("下周") != -1) {
			cal.add(Calendar.WEEK_OF_MONTH, 1);
			timePhrase_prefix = "下周";
		} else if (speechText.indexOf("下个月") != -1) {
			cal.add(Calendar.MONTH, 1);
			timePhrase_prefix = "下个月";
		} else if (speechText.indexOf("明年") != -1) {
			cal.add(Calendar.YEAR, 1);
			timePhrase_prefix = "明年";
		} else {
			final String[] timeUnit = new String[] { "分钟", "小时", "天", "周",
					"个月", "年" };
			final int[] timeUnitField = new int[] { Calendar.MINUTE,
					Calendar.HOUR, Calendar.DAY_OF_MONTH,
					Calendar.WEEK_OF_MONTH, Calendar.MONTH, Calendar.YEAR };
			for (int i = 0; i < timeUnit.length; i++) {
				if (isTimeSet == true && i < 2)
					continue;
				index = speechText.indexOf(timeUnit[i] + "后");
				if (index >= 1
						&& Character.isDigit(speechText.charAt(index - 1))) {
					int start = index - 1;
					while (start >= 0
							&& Character.isDigit(speechText.charAt(start))) {
						start--;
					}
					try {
						int j = Integer.valueOf(speechText.substring(start + 1,
								index));
                        //noinspection ResourceType
						cal.add(timeUnitField[i], j);
						timePhrase_prefix = j + timeUnit[i] + "后";
						break;
					} catch (Exception e) {
						Log.w(e.getMessage());
					} finally {
					}

				}
			}

		}

		if (speechText.indexOf("上午") != -1) {
			timePhrase_am_pm = "上午";
			cal.set(Calendar.AM_PM, Calendar.AM);
		} else if (speechText.indexOf("下午") != -1) {
			timePhrase_am_pm = "下午";
			cal.set(Calendar.AM_PM, Calendar.PM);
		} else if (speechText.indexOf("早上") != -1) {
			timePhrase_am_pm = "早上";
			cal.set(Calendar.AM_PM, Calendar.AM);
		} else if (speechText.indexOf("晚上") != -1) {
			timePhrase_am_pm = "晚上";
			cal.set(Calendar.AM_PM, Calendar.PM);
		}

		r.mCalendar = cal;
		r.sTimePhrase = timePhrase_prefix + timePhrase_date + timePhrase_week
				+ timePhrase_am_pm + timePhrase_time;
		if (r.sTimePhrase == "") {
			r.sTimePhrase = "未能识别时间";
		}
		r.iHit = 0;
		return r;
	}

	private ParseResult parseEnglish(String speechText) {
		ParseResult r = new ParseResult();
		Calendar cal = Calendar.getInstance();
		return r;
	}

}
