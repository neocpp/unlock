package com.qihoo.unlock.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class MyColorBackground extends Drawable {
	private static volatile MyColorBackground instance = null;
	private Rect mRect;
	private LinearGradient mLinearGradient;
	private Paint mPaint;
	private int mColor;
	private int mLevel = 0;
	private ColorHandler mHandler;
	public OnColorChangeListener mListener;

	public interface OnColorChangeListener {
		public void onColorChanged(int color);
	}

	private int[] colorGroup = { Color.parseColor("#FF0000"),
			Color.parseColor("#FF7F00"), Color.parseColor("#FFFF00"),
			Color.parseColor("#00FF00"), Color.parseColor("#00FFFF"),
			Color.parseColor("#0000FF"), Color.parseColor("#8B00FF") };

	private MyColorBackground() {
		mPaint = new Paint();
		mHandler = new ColorHandler();
	}

	public static MyColorBackground getInstance() {
		if (instance == null) {
			synchronized (MyColorBackground.class) {
				if (instance == null) {
					instance = new MyColorBackground();
				}
			}
		}
		return instance;
	}

	public int getColor() {
		return mColor;
	}

	@Override
	public void draw(Canvas canvas) {
		mRect = getBounds();

		if (mLinearGradient == null) {
			mLinearGradient = new LinearGradient(mRect.left, mRect.top,
					mRect.left, mRect.bottom, new int[] {
							Color.parseColor("#FFFFFF"),
							Color.parseColor("#888888") }, null,
					Shader.TileMode.CLAMP);
		}
		mPaint.setShader(mLinearGradient);
		mPaint.setColorFilter(new PorterDuffColorFilter(mColor,
				PorterDuff.Mode.MULTIPLY));

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

	public void setChangeLevel(int idx) {
		if (idx < 0) {
			mLevel = 0;
		} else if (idx > colorGroup.length) {
			mLevel = colorGroup.length - 1;
		} else {
			mLevel = idx;
		}
	}

	public void startAnimation() {
		mHandler.start();
	}

	class ColorHandler extends Handler {
		private int curLevel;
		private int startColor;
		private int endColor;

		int totalStep = 20;
		int curStep = 0;
		long interval = 30; // million second

		void start() {
			curLevel = 0;
			curStep = 0;
			removeMessages(0);
			sendEmptyMessage(0);
		}

		@Override
		public void handleMessage(Message msg) {
			if (curLevel < mLevel - 1) {
				if (curStep == 0) {
					startColor = colorGroup[curLevel];
					endColor = colorGroup[curLevel + 1];
				}

				if (curStep < totalStep) {
					mColor = getColor(startColor, endColor, curStep);

					if (curStep == totalStep - 1) {
						curLevel++;
						curStep = 0;
					} else {
						curStep++;
					}
				}

				sendEmptyMessageDelayed(0, interval);
			} else {
				mColor = colorGroup[curLevel];
			}
			invalidateSelf();
			if (mListener != null) {
				mListener.onColorChanged(mColor);
			}
		}

		int getColor(int color1, int color2, int step) {
			int red1 = Color.red(color1);
			int blue1 = Color.blue(color1);
			int green1 = Color.green(color1);
			int red2 = Color.red(color2);
			int blue2 = Color.blue(color2);
			int green2 = Color.green(color2);
			int red = red1 + (int) ((red2 - red1) * (step / (float) totalStep));
			int blue = blue1
					+ (int) ((blue2 - blue1) * (step / (float) totalStep));
			int green = green1
					+ (int) ((green2 - green1) * (step / (float) totalStep));

			return Color.rgb(red, green, blue);
		}
	}

}
