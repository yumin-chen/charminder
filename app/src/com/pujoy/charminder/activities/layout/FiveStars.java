package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FiveStars extends LinearLayout {
	public FiveStars(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		LayoutInflater.from(context).inflate(
				R.layout.fragment_settings_five_stars, this, true);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.IntegerStyleable, 0, 0);
		int num = array.getInteger(R.styleable.IntegerStyleable_number, 0);

		ImageView rlStar1 = (ImageView) findViewById(R.id.settings_five_stars_star1);
		rlStar1.setImageResource(num > 0 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar2 = (ImageView) findViewById(R.id.settings_five_stars_star2);
		rlStar2.setImageResource(num > 1 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar3 = (ImageView) findViewById(R.id.settings_five_stars_star3);
		rlStar3.setImageResource(num > 2 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar4 = (ImageView) findViewById(R.id.settings_five_stars_star4);
		rlStar4.setImageResource(num > 3 ? R.drawable.star1 : R.drawable.star0);
		ImageView rlStar5 = (ImageView) findViewById(R.id.settings_five_stars_star5);
		rlStar5.setImageResource(num > 4 ? R.drawable.star1 : R.drawable.star0);

		array.recycle();
	}
}
