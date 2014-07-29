package com.qihoo.unlock.view;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.utils.IncrementAnimationUtil.NotifyData;

public class SingleColorDrawable extends Drawable implements Observer {

	private Rect mRect;
	private Paint mPaint;
	private int mColor;
	private boolean needProcess;

	public SingleColorDrawable(int i, boolean b) {
		mPaint = new Paint();
		mColor = IncrementAnimationUtil.getInstance().getColor()[i];
		needProcess = b;
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRect == null) {
			mRect = getBounds();
		}

		mPaint.setColor(mColor);
		canvas.drawRect(mRect, mPaint);

		if (needProcess) {
			mPaint.setColor(Color.parseColor("#55FFFFFF"));
			canvas.drawRect(mRect, mPaint);
		}

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
