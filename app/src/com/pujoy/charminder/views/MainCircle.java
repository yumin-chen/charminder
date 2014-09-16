package com.pujoy.charminder.views;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowBase;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainCircle extends WindowBase {
	RelativeLayout mMainView;
	ImageView mBackground;
	TextView mCircleDescription;
	public ImageView[] mCircleItems;
	FloatingText mItemDescription;
	private int iOldHover;
	public int iHoveringItem;
	private static final int ICON_WIDTH = 80;

	@Override
	protected void onInitialize() {
		mMainView = new RelativeLayout(G.context);
		mBackground = new ImageView(G.context);
		mBackground.setImageResource(R.drawable.circle_bg);
		mCircleDescription = new TextView(G.context);
		RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
				(int) dpToPx(96), (int) dpToPx(96));
		onUpdateLayout();
		textParams.leftMargin = (mLayoutParams.getWidth() - (int) dpToPx(96)) / 2;
		textParams.topMargin = (mLayoutParams.getHeight() - (int) dpToPx(96)) / 2;
		mCircleDescription.setGravity(Gravity.CENTER);
		mCircleDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
				16 + G.getLanguage() * 5);
		mCircleDescription.setTextColor(C.COLOR_LIGHTBLUE);
		mMainView.addView(mCircleDescription, textParams);
		mBackground.setLayoutParams(new LayoutParams(mLayoutParams.getWidth(),
				mLayoutParams.getHeight()));
		mMainView.addView(mBackground);

		TypedArray drawble = G.context.getResources().obtainTypedArray(
				R.array.main_menu_icons);
		mCircleItems = new ImageView[G.settings.mCircleSection.length];
		RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[G.settings.mCircleSection.length];
		for (int i = 0; i < G.settings.mCircleSection.length; i++) {
			mCircleItems[i] = new ImageView(G.context);
			params[i] = new RelativeLayout.LayoutParams(
					(int) dpToPx(ICON_WIDTH), (int) dpToPx(ICON_WIDTH));
			params[i].leftMargin = 0;
			params[i].topMargin = 0;
			mCircleItems[i].setImageDrawable(drawble
					.getDrawable(G.settings.mCircleSection[i]));
			mMainView.addView(mCircleItems[i], params[i]);
		}
		drawble.recycle();

	}

	@Override
	protected void onCreate() {
		addView(mMainView, mLayoutParams);
		mCircleDescription.setText("");
		mCircleDescription.setBackgroundColor(C.COLOR_TRANSPARENT);
		Animation animation = AnimationUtils.loadAnimation(G.context,
				R.anim.zoom);
		mBackground.startAnimation(animation);
		for (int i = 0; i < mCircleItems.length; i++) {
			//Each x from center = radius * sin(a)
			//Each y from center = radius * cos(a)
			float tox = (float) (mLayoutParams.getWidth()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.sin(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) - dpToPx(ICON_WIDTH) / 2);
			float toy = (float) (mLayoutParams.getHeight()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.cos(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) - dpToPx(ICON_WIDTH) / 2);
			float fromx = (float) (mLayoutParams.getWidth()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.sin(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) / 4 - dpToPx(ICON_WIDTH) / 2);
			float fromy = (float) (mLayoutParams.getHeight()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.cos(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) / 4 - dpToPx(ICON_WIDTH) / 2);
			ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(
					mCircleItems[i], "y", fromy, toy);
			aCircleItemsY.setDuration(200);
			ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(
					mCircleItems[i], "x", fromx, tox);
			aCircleItemsX.setDuration(200);
			ValueAnimator aCircleItemsAlpha = ObjectAnimator.ofFloat(
					mCircleItems[i], "alpha", 0, 1.5f);
			aCircleItemsAlpha.setDuration(200);
			aCircleItemsY.start();
			aCircleItemsX.start();
			aCircleItemsAlpha.start();
		}

	}

	@Override
	protected void onRemove() {
		removeView(mMainView);
		if (mItemDescription != null) {
			mItemDescription.remove();
			mItemDescription = null;
			iHoveringItem = 0;
		}
	}

	@Override
	protected void onUpdateLayout() {
		mLayoutParams.setWidth((int) dpToPx(240));
		mLayoutParams.setHeight((int) dpToPx(240));
		mLayoutParams.setX((getScreenWidth() - mLayoutParams.getWidth()) / 2);
		mLayoutParams.setY((getScreenHeight() - mLayoutParams.getHeight()) / 2);
	}

	public int getX() {
		return mLayoutParams.getX();
	}

	public int getY() {
		return mLayoutParams.getY();
	}

	public void Hover(int i) {
		if (iHoveringItem != i + 1) {
			iHoveringItem = i + 1;
		} else {
			return;
		}
		TypedArray drawble = G.context.getResources().obtainTypedArray(
				R.array.main_menu_icons_hovered);
		String[] names = G.context.getResources().getStringArray(
				R.array.main_menu_names);
		String[] namesOnCircle = G.context.getResources().getStringArray(
				R.array.main_menu_names_on_circle);
		String[] descriptions = G.context.getResources().getStringArray(
				R.array.main_menu_descriptions);
		mCircleDescription.setText(namesOnCircle[G.settings.mCircleSection[i]]);
		updateOldHoverItem();
		mItemDescription = new FloatingText();
		mItemDescription.setText(String.format(
				descriptions[G.settings.mCircleSection[i]],
				names[G.settings.mCircleSection[i]]));
		mItemDescription.create();
		mCircleItems[i].setImageDrawable(drawble
				.getDrawable(G.settings.mCircleSection[i]));
		drawble.recycle();
		iOldHover = i + 1;
	}

	public void updateOldHoverItem() {
		if (mItemDescription != null) {
			mItemDescription.remove();
			mItemDescription = null;
		}

		if (iOldHover > 0) {
			int i = iOldHover - 1;
			TypedArray drawble = G.context.getResources().obtainTypedArray(
					R.array.main_menu_icons);
			mCircleItems[i].setImageDrawable(drawble
					.getDrawable(G.settings.mCircleSection[i]));
			drawble.recycle();
		}
		iOldHover = 0;
	}

	public boolean isPointInsideItem(float pointX, float pointY, int item) {
		final int RADIUS_COMPLEMENT = 8;
		return isPointInsideCircle(
				(int) pointX,
				(int) pointY,
				(int) (getX() + mCircleItems[item].getX() + mCircleItems[item]
						.getWidth() / 2),
				(int) (getY() + mCircleItems[item].getY() + mCircleItems[item]
						.getHeight() / 2),
				(int) (mCircleItems[item].getWidth() / 2 + dpToPx(RADIUS_COMPLEMENT)));
	}

}
