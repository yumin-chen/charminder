package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FiveStars extends LinearLayout {
	int iNum;
	public FiveStars(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.IntegerStyleable, 0, 0);
		iNum = array.getInteger(R.styleable.IntegerStyleable_number, 0);
		array.recycle();
	}
	
	public FiveStars(Context context){
		super(context);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		LayoutInflater.from(getContext()).inflate(
				R.layout.fragment_settings_five_stars, this, true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(
				R.layout.fragment_settings_five_stars, this, true);
		update();
	}
	
	public void setPriority(int num){
		iNum = num;
		update();
	}

	private void update() {
		ImageView rlStar1 = (ImageView) findViewById(R.id.settings_five_stars_star1);
		rlStar1.setImageResource(iNum > 0 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar2 = (ImageView) findViewById(R.id.settings_five_stars_star2);
		rlStar2.setImageResource(iNum > 1 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar3 = (ImageView) findViewById(R.id.settings_five_stars_star3);
		rlStar3.setImageResource(iNum > 2 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar4 = (ImageView) findViewById(R.id.settings_five_stars_star4);
		rlStar4.setImageResource(iNum > 3 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar5 = (ImageView) findViewById(R.id.settings_five_stars_star5);
		rlStar5.setImageResource(iNum > 4 ? R.drawable.star1 : R.drawable.star0);
	}
}
