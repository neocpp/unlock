package com.qihoo.unlock.view;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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
	private Paint mCornerPaint;
	private float startAngle;
	private float sweepAngle;
	private static float BAR_WIDTH = 45;
	private static float CORNER_RADIUS = BAR_WIDTH / 2;
	private static float PADDING = 76;
	private float mRadius;
	private float[] mCenter;
	private Bitmap mBitmap;
	private int mAlpha = 180;
	private MyHandler mHandler;
	RectF rectf;

	public ProgressCircle(Bitmap bitmap) {
		mCircleBarPaint = new Paint();
		mCircleBarPaint.setStyle(Style.STROKE);
		mCircleBarPaint.setStrokeWidth(BAR_WIDTH);
		mCircleBarPaint.setAntiAlias(true);
		mCircleBarPaint.setAlpha(mAlpha);

		mCornerPaint = new Paint();
		mCornerPaint.setStyle(Style.FILL);
		mCornerPaint.setAntiAlias(true);
		mCornerPaint.setAlpha(mAlpha);

		startAngle = -90;
		sweepAngle = 300;

		mBitmap = bitmap;
		mHandler = new MyHandler();
	}

	public void reset() {
		startAngle = -90;
	}

	@Override
	public void draw(Canvas canvas) {
		if (mRect == null) {
			mRect = getBounds();
			LinearGradient linearGradient = new LinearGradient(mRect.left, mRect.top, mRect.left, mRect.bottom,
					new int[] { Color.parseColor("#FFFFFF"), Color.parseColor("#AAAAAA") }, null, Shader.TileMode.CLAMP);
			mCircleBarPaint.setShader(linearGradient);
			mCornerPaint.setShader(linearGradient);

			mRectF = new RectF(mRect.left + PADDING, mRect.top + PADDING, mRect.right - PADDING, mRect.bottom - PADDING);

			mRadius = getRadius(mRectF);
			mCenter = getCenter(mRectF);
			rectf = new RectF();
		}

		canvas.drawBitmap(mBitmap, null, mRect, null);

		canvas.drawArc(mRectF, startAngle, sweepAngle, false, mCircleBarPaint);
		float[] center = getCirclePoint(mCenter, mRadius, startAngle);
		rectf.set(center[0] - CORNER_RADIUS, center[1] - CORNER_RADIUS, center[0] + CORNER_RADIUS, center[1]
				+ CORNER_RADIUS);
		canvas.drawArc(rectf, startAngle + 180, 184, true, mCornerPaint);
		// mCornerPaint.setXfermode(new
		// PorterDuffXfermode(PorterDuff.Mode.SRC));
		// canvas.drawCircle(center[0], center[1], BAR_WIDTH / 2, mCornerPaint);
		// mCornerPaint.setXfermode(null);

		center = getCirclePoint(mCenter, mRadius, startAngle + sweepAngle);
		rectf.set(center[0] - CORNER_RADIUS, center[1] - CORNER_RADIUS, center[0] + CORNER_RADIUS, center[1]
				+ CORNER_RADIUS);
		// canvas.drawCircle(center[0], center[1], BAR_WIDTH / 2, mCornerPaint);
		canvas.drawArc(rectf, startAngle + sweepAngle - 2, 182, true, mCornerPaint);

	}

	private float getRadius(RectF rectf) {
		return (rectf.right - rectf.left) / 2f;
	}

	private float[] getCenter(RectF rectf) {
		float[] result = new float[2];
		result[0] = rectf.left + (rectf.right - rectf.left) / 2;
		result[1] = rectf.top + (rectf.bottom - rectf.top) / 2;
		return result;
	}

	private float[] getCirclePoint(float[] center, float radius, float angle) {
		angle = (float) (angle * Math.PI / 180);
		float diffy = (float) (radius * Math.sin(angle));
		float diffx = (float) (radius * Math.cos(angle));
		return new float[] { center[0] + diffx, center[1] + diffy };
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

		@Override
		public void handleMessage(Message msg) {
			if (curStep < STEP) {
				startAngle = oldStartAngle + interpolator.getInterpolation(curStep / STEP) * angle;
				curStep++;
				sendEmptyMessageDelayed(0, INTERVAL);
			} else {
				startAngle = oldStartAngle + angle;
			}
			invalidateSelf();
		}
	}
}
