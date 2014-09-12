package com.pujoy.charminder.base;

import com.pujoy.charminder.Log;

public abstract class FloatingBase extends ViewBase{
	public boolean bInitialized;
	public boolean bCreated;
	public boolean bViewAdded;
	protected FloatingLayoutParams layoutParams;
	
	protected abstract void onInitialize(); 
	protected abstract void onCreate(); 
	protected abstract void onRemove(); 
	protected abstract void onUpdateLayout(); 
	
	public FloatingBase(){
		if(bInitialized){
			return;
		}
		if(layoutParams == null) layoutParams = new FloatingLayoutParams();
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
	
	protected void addView(android.view.View view, FloatingLayoutParams params){
		if (bViewAdded){
			return;
		}
		
		try {
			if(isRotated()){
				wm.addView(view, new FloatingLayoutParams(params)); 
			}else{
				wm.addView(view, params); 
			}
			bViewAdded = true;
		}catch(android.view.WindowManager.BadTokenException e){
			new Log(e.getMessage());
		}catch(Exception e){
			new Log(e.getMessage());
		}finally{
		}
	}
	
	protected void removeView(android.view.View view){
		if (!bViewAdded){
			return;
		}
		wm.removeView(view);
		bViewAdded = false;
	}
	
	protected void updateViewLayout(android.view.View view, FloatingLayoutParams params){
		if (!bCreated){
			return;
		}

		if (!bViewAdded){
			return;
		}
		if(isRotated()){
			wm.updateViewLayout(view, new FloatingLayoutParams(params)); 
		}else{
			wm.updateViewLayout(view, params); 
		}
	}
	
	public boolean isCreated(){
		return bCreated;
	}
	

}
