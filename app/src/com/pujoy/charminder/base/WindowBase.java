package com.pujoy.charminder.base;

import com.pujoy.charminder.Log;

public abstract class WindowBase extends ViewBase{
	public boolean bInitialized;
	public boolean bCreated;
	public boolean bViewAdded;
	protected WindowLayoutParams mLayoutParams;
	
	protected abstract void onInitialize(); 
	protected abstract void onCreate(); 
	protected abstract void onRemove(); 
	protected abstract void onUpdateLayout(); 
	
	public WindowBase(){
		if(bInitialized){
			return;
		}
		if(mLayoutParams == null) mLayoutParams = new WindowLayoutParams();
		onInitialize();
		bInitialized = true;
	}

	public void create(){
		if (bCreated){
			remove();
		}
		onUpdateLayout();
		onCreate();
		bCreated = true;
	}
	
	public void remove(){
		if (!bCreated){
			return;
		}
		onRemove();
		bCreated = false;
	}
	
	protected void addView(android.view.View view, WindowLayoutParams params){
		if (bViewAdded){
			return;
		}
		
		try {
			if(isRotated()){
				mWindowManager.addView(view, new WindowLayoutParams(params)); 
			}else{
				mWindowManager.addView(view, params); 
			}
			bViewAdded = true;
		}catch(android.view.WindowManager.BadTokenException e){
			Log.w(e.getMessage());
		}catch(Exception e){
			Log.w(e.getMessage());
		}finally{
		}
	}
	
	protected void removeView(android.view.View view){
		if (!bViewAdded){
			return;
		}
		mWindowManager.removeView(view);
		bViewAdded = false;
	}
	
	protected void updateViewLayout(android.view.View view, WindowLayoutParams params){
		if (!bCreated){
			return;
		}

		if (!bViewAdded){
			return;
		}
		if(isRotated()){
			mWindowManager.updateViewLayout(view, new WindowLayoutParams(params)); 
		}else{
			mWindowManager.updateViewLayout(view, params); 
		}
	}
	
	public boolean isCreated(){
		return bCreated;
	}
	

}
