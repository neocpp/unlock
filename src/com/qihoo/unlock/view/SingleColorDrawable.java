package com.qihoo.unlock.view;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.utils.IncrementAnimationUtil.NotifyData;

public class SingleColorDrawable extends Drawable implements Observer {

	private Rect mRect;
	private Paint mPaint;
	private int mColor;

	public SingleColorDrawable() {
		mPaint = new Paint();
		mColor = IncrementAnimationUtil.getInstance().getColor()[0];
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRect == null) {
			mRect = getBounds();
		}

		mPaint.setColor(mColor);

		canvas.drawRect(mRect, mPaint);

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

		mColor = ((NotifyData) data).color[0];
		invalidateSelf();
	}

}
