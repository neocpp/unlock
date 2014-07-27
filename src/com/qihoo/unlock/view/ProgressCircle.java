package com.qihoo.unlock.view;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Interpolator;

import com.qihoo.unlock.utils.DampingInterpolater;
import com.qihoo.unlock.utils.IncrementAnimationUtil.NotifyData;

public class ProgressCircle extends Drawable implements Observer {
	private static String TAG = "ProgressCircle";
	private Rect mRect;
	private RectF mRectF;
	private Paint mCircleBarPaint;
	private float startAngle;
	private float sweepAngle;
	private static float BAR_WIDTH = 45;
	private static float PADDING = 76;
	private Bitmap mBitmap;
	private int mAlpha = 180;
	private MyHandler mHandler;

	public ProgressCircle(Bitmap bitmap) {
		mCircleBarPaint = new Paint();
		mCircleBarPaint.setStyle(Style.STROKE);
		mCircleBarPaint.setStrokeWidth(BAR_WIDTH);
		mCircleBarPaint.setAntiAlias(true);
		mCircleBarPaint.setAlpha(mAlpha);
		mCircleBarPaint.setStrokeCap(Cap.ROUND);

		startAngle = -90;
		sweepAngle = 300;

		mBitmap = bitmap;
		mHandler = new MyHandler();
	}

	public void reset() {
		startAngle = -90;
		mHandler.endAnimation();
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRect == null) {
			mRect = getBounds();
			LinearGradient linearGradient = new LinearGradient(mRect.left,
					mRect.top, mRect.left, mRect.bottom, new int[] {
							Color.parseColor("#FFFFFF"),
							Color.parseColor("#AAAAAA") }, null,
					Shader.TileMode.CLAMP);
			mCircleBarPaint.setShader(linearGradient);

			mRectF = new RectF(mRect.left + PADDING, mRect.top + PADDING,
					mRect.right - PADDING, mRect.bottom - PADDING);

		}

		canvas.drawBitmap(mBitmap, null, mRect, null);

		canvas.drawArc(mRectF, startAngle, sweepAngle, false, mCircleBarPaint);

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
		NotifyData notifyData = (NotifyData) data;
		sweepAngle = 360 * notifyData.percentage / 100;
		invalidateSelf();
		// Log.e(TAG, "percentage: " + notifyData.percentage);
		if (notifyData.isDone) {
			mHandler.startDropAnimation();
		}
	}

	class MyHandler extends Handler {
		private long INTERVAL = 50;
		private float STEP = 80;

		private Interpolator interpolator = new DampingInterpolater(0.2, 1);
		private float angle = 0;
		private float curStep = 0;
		private float oldStartAngle;

		void startDropAnimation() {
			removeMessages(0);
			angle = 180 - sweepAngle / 2;
			curStep = 1;
			oldStartAngle = startAngle;
			sendEmptyMessage(0);
		}
		
		void endAnimation(){
			removeMessages(0);
		}

		@Override
		public void handleMessage(Message msg) {
			if (curStep < STEP) {
				startAngle = oldStartAngle
						+ interpolator.getInterpolation(curStep / STEP) * angle;
				curStep++;
				sendEmptyMessageDelayed(0, INTERVAL);
			} else {
				startAngle = oldStartAngle + angle;
			}
			invalidateSelf();
		}
	}
}
