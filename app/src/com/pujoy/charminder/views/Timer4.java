package com.pujoy.charminder.views;

import static com.pujoy.charminder.MainActivity.con;

import java.util.ArrayList;
import java.util.Calendar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.SpeechParser;
import com.pujoy.charminder.SpeechParser.ParseResult;
import com.pujoy.charminder.base.FloatingDialogWithStars;

public class Timer4 extends FloatingDialogWithStars implements OnClickListener {
	private ImageView ivTitleIcon;
	private TextView tvTitle;
	private ImageView ivMicBackground; 
	private ImageView ivMicIcon; 
	private ImageView ivVolume; 
	private TextView tvSpeechPrompt;
	private TextView tvTime;
	private TextView tvSpeechText;
	private SpeechRecognizer mSpeechRecognizer;   
	
	private int iOldErrorInfo;
    
	protected void onInitialize(){
		super.onInitialize();
		ivTitleIcon = new ImageView(con);
    	ivTitleIcon.setImageResource(R.drawable.audio_reminder);
    	ivTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(54), (int)dpToPx(54)));
    	ivTitleIcon.setX(layoutParams.getWidth()/2-dpToPx(54)/2);
    	
    	addToMainView(ivTitleIcon);
    	
    	tvTitle = new TextView(con);
    	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvTitle.setText(con.getString(R.string.title_timer4));
    	tvTitle.setLayoutParams(new LayoutParams((int)dpToPx(72), (int)dpToPx(48)));
    	tvTitle.setX(layoutParams.getWidth()/2-dpToPx(72)/2);
    	
    	addToMainView(tvTitle);
    	
    	ivMicBackground = new ImageView(con);
    	ivMicBackground.setLayoutParams(new LayoutParams((int)(dpToPx(128)*0.8), (int)(dpToPx(128)*0.8)));
    	ivMicBackground.setX((float) (layoutParams.getWidth()/2-dpToPx(128)*0.8/2));
    	ivMicBackground.setY((float) (layoutParams.getHeight()/2-dpToPx(128)*0.8/2)-dpToPx(16));
    	ivMicBackground.setOnClickListener(this);
    	addToMainView(ivMicBackground);

    	
    	ivVolume = new ImageView(con);
    	ivVolume.setImageResource(R.drawable.volume);
    	ivMicBackground.setImageResource(R.drawable.speech_recognizer_background);
    	ivVolume.setLayoutParams(new LayoutParams((int)(dpToPx(28.8f)*0.8), (int)(dpToPx(46.8f)*0.8)));
    	ivVolume.setX((float) (ivMicBackground.getX()+dpToPx(49.6f)*0.8));
    	ivVolume.setY((float) (ivMicBackground.getY()+dpToPx(34.2f)*0.8));
    	ivVolume.setVisibility(View.GONE);
    	addToMainView(ivVolume);
    	
    	ivMicIcon = new ImageView(con);
    	ivMicIcon.setImageResource(R.drawable.speech_recognizer);
    	ivMicIcon.setLayoutParams(new LayoutParams((int)(dpToPx(128)*0.8), (int)(dpToPx(128)*0.8)));
    	ivMicIcon.setX((float) (layoutParams.getWidth()/2-dpToPx(128)*0.8/2));
    	ivMicIcon.setY((float) (layoutParams.getHeight()/2-dpToPx(128)*0.8/2)-dpToPx(16));
    	addToMainView(ivMicIcon);
    	
    	tvSpeechPrompt = new TextView(con);
    	tvSpeechPrompt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    	tvSpeechPrompt.setGravity(Gravity.CENTER);
    	tvSpeechPrompt.setTextColor(Constants.COLOR_DARKBLUE);
    	tvSpeechPrompt.setLayoutParams(new LayoutParams((int)dpToPx(128), (int)dpToPx(26)));
    	tvSpeechPrompt.setX(layoutParams.getWidth()/2-dpToPx(128)/2);
    	tvSpeechPrompt.setY((float) (ivMicIcon.getY()+dpToPx(128)*0.8+dpToPx(8)));
    	addToMainView(tvSpeechPrompt);
    	
    	mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(con.getApplicationContext());
    	mSpeechRecognizer.setRecognitionListener(new speechListener());  
    	
	}

	protected void onCreate() {
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				layoutParams.getHeight()/2 - layoutParams.getWidth()/2 - dpToPx(4) , - dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
		aTitleIconY.start();
		
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(32), dpToPx(32));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();
		
		startRecording();
	}
	
	class speechListener implements RecognitionListener          
	   {
	            private static final String TAG = "cym";
				public void onReadyForSpeech(Bundle params)
	            {
					onBeginningOfSpeech();
	            }
	            public void onBeginningOfSpeech()
	            {
	            	ivMicBackground.setImageResource(R.drawable.speech_recognizer_background_on_speaking);
	            	ivVolume.setVisibility(View.VISIBLE);
	            	tvSpeechPrompt.setText(con.getString(R.string.speech_recognition_speak_now));
	            }
	            public void onRmsChanged(float rmsdB)
	            {
	            	if(rmsdB > 10)
	            		rmsdB = 10;
	            	if(rmsdB <0)
	            		rmsdB = 0;
	            	ivVolume.setPivotY(0);
	            	ivVolume.setScaleY((10-rmsdB)/10);
	            }
	            public void onBufferReceived(byte[] buffer)
	            {
	            	//According to the web, this is never called on JellyBean and above
	            	//So I won't bother to save the audio buffer
	            }
	            public void onEndOfSpeech()
	            {
	            	ivMicBackground.setImageResource(R.drawable.speech_recognizer_background);
	            	ivVolume.setVisibility(View.GONE);
	            	tvSpeechPrompt.setText(con.getString(R.string.speech_recognition_speak_end));
	            }
	            public void onError(int error)
	            {
	            	onEndOfSpeech();
	            	String errorInfo = null;
	            	switch(error){
	            	case 1:
	            		errorInfo = (con.getString(R.string.speech_recognition_error1));
	            		break;
	            	case 2:
	            		errorInfo = (con.getString(R.string.speech_recognition_error2));
	            		break;
	            	case 3:
	            		errorInfo = (con.getString(R.string.speech_recognition_error3));
	            		break;
	            	case 4:
	            		errorInfo = (con.getString(R.string.speech_recognition_error4));
	            		break;
	            	case 5:
	            	case 6:
	            	case 7:
	            		break;
	            	case 8:
	            		if(iOldErrorInfo == 8)
	            			errorInfo = (con.getString(R.string.speech_recognition_error8));
	            		break;
	            	case 9:
	            		errorInfo = (con.getString(R.string.speech_recognition_error9));
	            		break;
	            	default:
	            		errorInfo = (con.getString(R.string.speech_recognition_error_unknown,
	            				String.valueOf(error)));
	            		break;
	            	}
	            	if(errorInfo != null)
	            		Constants.pushText(errorInfo);
	            	iOldErrorInfo = error;
	            }
	            public void onResults(Bundle results)                   
	            {
	            	onEndOfSpeech();
                    String str = new String();
                    Log.d(TAG, "onResults " + results);
                    ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    for (int i = 0; i < data.size(); i++)
                    {
                    	Log.d(TAG, "result " + data.get(i));
                    	//String temp = SpeechParser.parseChinese(data.get(i));
                    	//Log.d(TAG, SpeechParser.parseChinese(data.get(i)));
                        str += data.get(i);
                    }
                    
                    Constants.pushText(str);        
	            }
	            public void onPartialResults(Bundle partialResults)
	            {
	            	//Didn't make it support partial results so ignore this.
	            }
	            public void onEvent(int eventType, Bundle params)
	            {
	                     Log.d(TAG, "onEvent " + eventType);
	            }
	   }


	protected void onRemove() {
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
			- dpToPx(4), layoutParams.getHeight()/2 - layoutParams.getWidth()/2 - dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
		aTitleIconY.start();
		
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				dpToPx(32), layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(32));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();

        mSpeechRecognizer.stopListening();
	}
	
	@Override
	public void onClick(View v) {
		if(v == ivMicBackground){
			startRecording();
		}
		
	}
	
	public void startRecording(){
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);    
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1); 
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.pujoy.charminder");
        mSpeechRecognizer.startListening(intent);
	}

	@Override
	protected void onUpdateLayout() {
    	layoutParams.setWidth((int) dpToPx(256));
    	layoutParams.setHeight((int) dpToPx(320));
        layoutParams.x = (getScreenWidth()-layoutParams.getWidth())/2;
        layoutParams.y = (getScreenHeight()-layoutParams.getHeight())/2;   
        layoutParams.alpha = 0.95f;
	}


	@Override
	protected void onOk() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void onCancel() {
		// TODO Auto-generated method stub
		
	}


}
