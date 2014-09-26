package com.pujoy.charminder.views;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowDialogWithStars;
import com.pujoy.charminder.data.Reminder;
import com.pujoy.charminder.helper.Helper;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;
import com.pujoy.charminder.other.Log;
import com.pujoy.charminder.other.SpeechParser;
import com.pujoy.charminder.other.SpeechParser.ParseResult;

public class Timer4 extends WindowDialogWithStars implements OnClickListener {
	private ImageView mTitleIcon;
	private TextView mTitle;
	private ImageView mMicBackground;
	private ImageView mMicIcon;
	private View mVolume;
	private TextView mSpeechPrompt;
	private ImageView mTimeIcon;
	private TextView mTimeText;
	private ImageView mContentIcon;
	private TextView mSpeechContent;
	private ImageView mEditIcon;
	private TextView mEditText;
	private ImageView mRerecordIcon;
	private TextView mRerecordText;
	private SpeechRecognizer mSpeechRecognizer;
	private ParseResult mParseResult;
	private String sSpeechInput;
	private int iOldErrorInfo;

	protected void onInitialize() {
		super.onInitialize();
		mTitleIcon = new ImageView(G.context);
		mTitleIcon.setImageResource(R.drawable.audio_reminder);
		mTitleIcon.setLayoutParams(new LayoutParams((int) dpToPx(54),
				(int) dpToPx(54)));
		mTitleIcon.setX(mLayoutParams.getWidth() / 2 - dpToPx(54) / 2);

		addToMainView(mTitleIcon);

		mTitle = new TextView(G.context);
		mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mTitle.setGravity(Gravity.CENTER);
		mTitle.setTextColor(C.COLOR_LIGHTBLUE);
		// mTitle.setText(G.context.getString(R.string.title_timer4));
		mTitle.setLayoutParams(new LayoutParams((int) dpToPx(72),
				(int) dpToPx(48)));
		mTitle.setX(mLayoutParams.getWidth() / 2 - dpToPx(72) / 2);

		addToMainView(mTitle);

		mMicBackground = new ImageView(G.context);
		mMicBackground.setLayoutParams(new LayoutParams(
				(int) (dpToPx(128) * 0.8), (int) (dpToPx(128) * 0.8)));
		mMicBackground
				.setX((float) (mLayoutParams.getWidth() / 2 - dpToPx(128) * 0.8 / 2));
		mMicBackground
				.setY((float) (mLayoutParams.getHeight() / 2 - dpToPx(128) * 0.8 / 2)
						- dpToPx(16));
		mMicBackground.setOnClickListener(this);
		addToMainView(mMicBackground);

		mVolume = new View(G.context);
		mVolume.setBackgroundColor(Color.rgb(237, 28, 36));;
		mMicBackground
				.setImageResource(R.drawable.speech_recognizer_background);
		mVolume.setLayoutParams(new LayoutParams((int) (dpToPx(28.8f) * 0.8),
				(int) (dpToPx(46.8f) * 0.8)));
		mVolume.setX((float) (mMicBackground.getX() + dpToPx(49.6f) * 0.8));
		mVolume.setY((float) (mMicBackground.getY() + dpToPx(34.2f) * 0.8));
		mVolume.setVisibility(View.GONE);
		addToMainView(mVolume);

		mMicIcon = new ImageView(G.context);
		mMicIcon.setImageResource(R.drawable.speech_recognizer);
		mMicIcon.setLayoutParams(new LayoutParams((int) (dpToPx(128) * 0.8),
				(int) (dpToPx(128) * 0.8)));
		mMicIcon.setX((float) (mLayoutParams.getWidth() / 2 - dpToPx(128) * 0.8 / 2));
		mMicIcon.setY((float) (mLayoutParams.getHeight() / 2 - dpToPx(128) * 0.8 / 2)
				- dpToPx(16));
		addToMainView(mMicIcon);

		mSpeechPrompt = new TextView(G.context);
		mSpeechPrompt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mSpeechPrompt.setGravity(Gravity.CENTER);
		mSpeechPrompt.setTextColor(C.COLOR_DARKBLUE);
		mSpeechPrompt.setLayoutParams(new LayoutParams((int) dpToPx(128),
				(int) dpToPx(26)));
		mSpeechPrompt.setX(mLayoutParams.getWidth() / 2 - dpToPx(128) / 2);
		mSpeechPrompt
				.setY((float) (mMicIcon.getY() + dpToPx(128) * 0.8 + dpToPx(8)));
		addToMainView(mSpeechPrompt);

		mTimeText = new TextView(G.context);
		mTimeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mTimeText.setTextColor(C.COLOR_DARKBLUE);
		mTimeText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		mTimeText.setX((int) dpToPx(40));
		mTimeText.setY(mLayoutParams.getWidth() / 2 * (1 - CENTER_PROPORTION)
				+ dpToPx(8));
		mTimeText.setLayoutParams(new LayoutParams(
				(int) (dpToPx(256 - 8) - mTimeText.getX()), (int) dpToPx(26)));
		mTimeText.setVisibility(View.GONE);
		addToMainView(mTimeText);

		mTimeIcon = new ImageView(G.context);
		mTimeIcon.setImageResource(R.drawable.time);
		mTimeIcon.setLayoutParams(new LayoutParams((int) dpToPx(24),
				(int) dpToPx(24)));
		mTimeIcon.setX(dpToPx(12));
		mTimeIcon.setY(mTimeText.getY());
		mTimeIcon.setVisibility(View.GONE);
		addToMainView(mTimeIcon);

		mSpeechContent = new TextView(G.context);
		mSpeechContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mSpeechContent.setTextColor(C.COLOR_DARKBLUE);
		mSpeechContent.setX((int) dpToPx(40));
		mSpeechContent.setY(mLayoutParams.getWidth() / 2
				* (1 - CENTER_PROPORTION) + dpToPx(32 + 4));
		mSpeechContent.setLayoutParams(new LayoutParams(
				(int) (dpToPx(256 - 8) - mTimeText.getX()), (int) dpToPx(68)));
		mSpeechContent.setVisibility(View.GONE);
		addToMainView(mSpeechContent);

		mContentIcon = new ImageView(G.context);
		mContentIcon.setImageResource(R.drawable.content);
		mContentIcon.setLayoutParams(new LayoutParams((int) dpToPx(24),
				(int) dpToPx(24)));
		mContentIcon.setX(dpToPx(12));
		mContentIcon.setY(mSpeechContent.getY());
		mContentIcon.setVisibility(View.GONE);
		addToMainView(mContentIcon);

		OnTouchListener editButtonTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mEditIcon.setImageResource(R.drawable.sr_edit_button_a);
					break;
				case MotionEvent.ACTION_UP:
					mEditIcon.setImageResource(R.drawable.sr_edit_button);
					break;
				}
				return false;
			}

		};

		OnClickListener editButtonOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				startEditing();
			}

		};

		OnTouchListener rerecordButtonTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mRerecordIcon
							.setImageResource(R.drawable.sr_record_button_a);
					break;
				case MotionEvent.ACTION_UP:
					mRerecordIcon.setImageResource(R.drawable.sr_record_button);
					break;
				}
				return false;
			}

		};

		OnClickListener rerecordButtonOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearResult();
				startRecording();
			}

		};

		mEditIcon = new ImageView(G.context);
		mEditIcon.setImageResource(R.drawable.sr_edit_button);
		mEditIcon.setLayoutParams(new LayoutParams((int) dpToPx(48),
				(int) dpToPx(48)));
		mEditIcon.setX(dpToPx(22));
		mEditIcon.setY(mLayoutParams.getHeight() - mLayoutParams.getWidth() / 2
				* (1 - CENTER_PROPORTION) - dpToPx(54));
		mEditIcon.setVisibility(View.GONE);
		mEditIcon.setOnTouchListener(editButtonTouchListener);
		mEditIcon.setOnClickListener(editButtonOnClickListener);
		addToMainView(mEditIcon);

		mRerecordIcon = new ImageView(G.context);
		mRerecordIcon.setImageResource(R.drawable.sr_record_button);
		mRerecordIcon.setLayoutParams(new LayoutParams((int) dpToPx(48),
				(int) dpToPx(48)));
		mRerecordIcon.setX(mLayoutParams.getWidth() / 2 - dpToPx(16)
				+ dpToPx(22));
		mRerecordIcon.setY(mLayoutParams.getHeight() - mLayoutParams.getWidth()
				/ 2 * (1 - CENTER_PROPORTION) - dpToPx(54));
		mRerecordIcon.setVisibility(View.GONE);
		mRerecordIcon.setOnTouchListener(rerecordButtonTouchListener);
		mRerecordIcon.setOnClickListener(rerecordButtonOnClickListener);
		addToMainView(mRerecordIcon);

		mEditText = new TextView(G.context);
		mEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mEditText.setTextColor(C.COLOR_DARKBLUE);
		mEditText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		mEditText.setX(mEditIcon.getX() + dpToPx(52));
		mEditText.setY(mEditIcon.getY());
		mEditText.setText(G.context
				.getString(R.string.speech_recognition_edit_button_text));
		mEditText.setLayoutParams(new LayoutParams((int) (dpToPx(58)),
				(int) dpToPx(48)));
		mEditText.setVisibility(View.GONE);
		mEditText.setOnTouchListener(editButtonTouchListener);
		mEditText.setOnClickListener(editButtonOnClickListener);
		addToMainView(mEditText);

		mRerecordText = new TextView(G.context);
		mRerecordText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mRerecordText.setTextColor(C.COLOR_DARKBLUE);
		mRerecordText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		mRerecordText.setX(mRerecordIcon.getX() + dpToPx(52));
		mRerecordText.setY(mRerecordIcon.getY());
		mRerecordText.setText(G.context
				.getString(R.string.speech_recognition_rerecord_button_text));
		mRerecordText.setLayoutParams(new LayoutParams((int) (dpToPx(58)),
				(int) dpToPx(48)));
		mRerecordText.setVisibility(View.GONE);
		mRerecordText.setOnTouchListener(rerecordButtonTouchListener);
		mRerecordText.setOnClickListener(rerecordButtonOnClickListener);
		addToMainView(mRerecordText);

		mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(G.context
				.getApplicationContext());
		mSpeechRecognizer.setRecognitionListener(new speechListener());
	}

	protected void onCreate() {
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						- dpToPx(4), -dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
		aTitleIconY.start();

		ValueAnimator aTitleY = ObjectAnimator.ofFloat(mTitle, "y",
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(32), dpToPx(32));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();

		startRecording();
	}

	private void onStartOfSpeechRecognizing() {
		mMicBackground
				.setImageResource(R.drawable.speech_recognizer_background_on_speaking);
		mVolume.setVisibility(View.VISIBLE);
		mSpeechPrompt.setText(G.context
				.getString(R.string.speech_recognition_speak_now));
		mParseResult = null;
	}

	private void onEndOfSpeechRecognizing() {
		mMicBackground
				.setImageResource(R.drawable.speech_recognizer_background);
		mVolume.setVisibility(View.GONE);
		mSpeechPrompt.setText(G.context
				.getString(R.string.speech_recognition_speak_end));
		mParseResult = null;
	}

	private void displayResult(String speechText, ParseResult result) {
		mSpeechContent.setVisibility(View.VISIBLE);
		mContentIcon.setVisibility(View.VISIBLE);
		mTimeIcon.setVisibility(View.VISIBLE);
		mTimeText.setVisibility(View.VISIBLE);
		mEditIcon.setVisibility(View.VISIBLE);
		mRerecordIcon.setVisibility(View.VISIBLE);
		mEditText.setVisibility(View.VISIBLE);
		mRerecordText.setVisibility(View.VISIBLE);
		mMicIcon.setVisibility(View.GONE);
		mMicBackground.setVisibility(View.GONE);
		mSpeechPrompt.setVisibility(View.GONE);
		mTimeText.setText(result.sTimePhrase);
		mSpeechContent.setText(speechText);
	}

	private void clearResult() {
		mSpeechContent.setVisibility(View.GONE);
		mContentIcon.setVisibility(View.GONE);
		mTimeIcon.setVisibility(View.GONE);
		mTimeText.setVisibility(View.GONE);
		mRerecordIcon.setVisibility(View.GONE);
		mEditIcon.setVisibility(View.GONE);
		mEditText.setVisibility(View.GONE);
		mRerecordText.setVisibility(View.GONE);
		mMicIcon.setVisibility(View.VISIBLE);
		mSpeechPrompt.setVisibility(View.VISIBLE);
		mMicBackground.setVisibility(View.VISIBLE);
	}

	class speechListener implements RecognitionListener {
		public void onReadyForSpeech(Bundle params) {
			onStartOfSpeechRecognizing();
		}

		public void onBeginningOfSpeech() {
			onStartOfSpeechRecognizing();
		}

		public void onRmsChanged(float rmsdB) {
			if (rmsdB > 10)
				rmsdB = 10;
			if (rmsdB < 0)
				rmsdB = 0;
			mVolume.setPivotY(0);
			mVolume.setScaleY((10 - rmsdB) / 10);
		}

		public void onBufferReceived(byte[] buffer) {
			// According to the web, this is never called on JellyBean and above
			// So I won't bother to save the audio buffer
		}

		public void onEndOfSpeech() {
			onEndOfSpeechRecognizing();
		}

		public void onError(int error) {
			onEndOfSpeechRecognizing();
			String errorInfo = null;
			switch (error) {
			case 1:
				errorInfo = (G.context
						.getString(R.string.speech_recognition_error1));
				break;
			case 2:
				errorInfo = (G.context
						.getString(R.string.speech_recognition_error2));
				break;
			case 3:
				errorInfo = (G.context
						.getString(R.string.speech_recognition_error3));
				break;
			case 4:
				errorInfo = (G.context
						.getString(R.string.speech_recognition_error4));
				break;
			case 5:
			case 6:
			case 7:
				break;
			case 8:
				if (iOldErrorInfo == 8)
					errorInfo = (G.context
							.getString(R.string.speech_recognition_error8));
				break;
			case 9:
				errorInfo = (G.context
						.getString(R.string.speech_recognition_error9));
				break;
			default:
				errorInfo = (G.context.getString(
						R.string.speech_recognition_error_unknown,
						String.valueOf(error)));
				break;
			}
			if (errorInfo != null)
				Helper.pushText(errorInfo);
			iOldErrorInfo = error;
		}

		public void onResults(Bundle results) {
			onEndOfSpeechRecognizing();
			ArrayList<String> data = results
					.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			try {
				SpeechParser sp = new SpeechParser(data, G.getLanguage());
				mParseResult = sp.parse();
				sSpeechInput = data.get(0);
				displayResult(sSpeechInput, mParseResult);
			} catch (Exception e) {
				Log.w(e.getMessage());
			} finally {

			}
		}

		public void onPartialResults(Bundle partialResults) {
			// Didn't make it support partial results so ignore this.
		}

		public void onEvent(int eventType, Bundle params) {
		}
	}

	protected void onRemove() {
		mSpeechRecognizer.stopListening();
		
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				-dpToPx(4),
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						- dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
		aTitleIconY.start();

		ValueAnimator aTitleY = ObjectAnimator.ofFloat(mTitle, "y", dpToPx(32),
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(32));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();
		
	}

	@Override
	public void onClick(View v) {
		if (v == mMicBackground) {
			startRecording();
		}

	}

	public void startRecording() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				"com.pujoy.charminder");
		mSpeechRecognizer.startListening(intent);
	}

	@Override
	protected void onUpdateLayout() {
		mLayoutParams.setWidth((int) dpToPx(256));
		mLayoutParams.setHeight((int) dpToPx(320));
		mLayoutParams.x = (getScreenWidth() - mLayoutParams.getWidth()) / 2;
		mLayoutParams.y = (getScreenHeight() - mLayoutParams.getHeight()) / 2;
		mLayoutParams.alpha = 0.95f;
	}

	@Override
	protected void onOk() {
		if(mParseResult == null){
			Helper.pushText(G.context.getResources().getString(R.string.speech_recognition_no_input));
			return;
		}
		addReminder(true);
	}
	
	protected void addReminder(boolean pushBubble) {
		Reminder newReminder = new Reminder(4);
		newReminder.mTimeToRemind = mParseResult.mCalendar;
		newReminder.sNote = sSpeechInput;
		newReminder.sTimePhrase = mParseResult.sTimePhrase;
		newReminder.iPriority = iPriority;
		G.reminders.add(newReminder, pushBubble);
		
	}
	
	@Override
	protected void onCancel() {
		// TODO Auto-generated method stub

	}

}
