package com.qihoo.unlock.view;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.utils.IncrementAnimationUtil.NotifyData;

public class MyColorBackground extends Drawable implements Observer {

	private Rect mRect;
	private LinearGradient mLinearGradient;
	private Paint mPaint;
	private int[] mColor;

	public MyColorBackground() {
		mPaint = new Paint();
		mColor = IncrementAnimationUtil.getInstance().getColor();
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRect == null) {
			mRect = getBounds();
		}
		mLinearGradient = new LinearGradient(mRect.left, mRect.top, mRect.left, mRect.bottom, new int[] { mColor[0],
				mColor[0], mColor[1] }, new float[] { 0.0f, 0.4f, 1.0f }, Shader.TileMode.CLAMP);

		mPaint.setShader(mLinearGradient);

		canvas.drawRect(mRect, mPaint);

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable observable, Object data) {
		NotifyData notifyData = (NotifyData) data;
		mColor = notifyData.color;
		invalidateSelf();
	}
}
