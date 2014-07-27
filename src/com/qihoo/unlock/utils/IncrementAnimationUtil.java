package com.qihoo.unlock.utils;

import java.util.Observable;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

public class IncrementAnimationUtil extends Observable {
	private static volatile IncrementAnimationUtil instance = null;
	private ColorHandler mHandler;
	private int[] mColor;
	private int mLevel;
	private static int LEVEL_COUNT = 10;

	public int[] getColor() {
		return mColor;
	}

	private static int[] startColors = { Color.parseColor("#6154DB"),
			Color.parseColor("#4788F7"), Color.parseColor("#54A3D4"),
			Color.parseColor("#1DA0B0"), Color.parseColor("#49A990"),
			Color.parseColor("#81CB63"), Color.parseColor("#E7B34D"),
			Color.parseColor("#E4904B"), Color.parseColor("#D06C45"),
			Color.parseColor("#DA563B") };

	private static int[] endColors = { Color.parseColor("#9144FB"),
			Color.parseColor("#56A8D5"), Color.parseColor("#49D0C9"),
			Color.parseColor("#55D7A4"), Color.parseColor("#8EEA92"),
			Color.parseColor("#DAD75F"), Color.parseColor("#DFD962"),
			Color.parseColor("#E1C463"), Color.parseColor("#E9A057"),
			Color.parseColor("#F5A758") };

	public static IncrementAnimationUtil getInstance() {
		if (instance == null) {
			synchronized (IncrementAnimationUtil.class) {
				if (instance == null) {
					instance = new IncrementAnimationUtil();
				}
			}
		}
		return instance;
	}

	public IncrementAnimationUtil() {
		mColor = new int[] { startColors[0], endColors[0] };
		mLevel = 0;
		mHandler = new ColorHandler();
	}

	public void setChangeLevel(int idx) {
		idx = idx / 10;
		if (idx < 0) {
			mLevel = 0;
		} else if (idx > LEVEL_COUNT) {
			mLevel = LEVEL_COUNT - 1;
		} else {
			mLevel = idx;
		}
	}

	public void startAnimation() {
		mHandler.start();
	}

	public void endAnimation() {
		mHandler.end();
	}

	class ColorHandler extends Handler {
		private int curLevel;
		private int[] srcColor = new int[2];
		private int[] dstColor = new int[2];

		int totalStep = 10;
		int curStep = 0;
		long interval = 25; // million second

		private boolean isDone;

		void start() {
			curLevel = 0;
			curStep = 0;
			isDone = false;
			removeMessages(0);
			sendEmptyMessage(0);
		}

		void end() {
			removeMessages(0);
		}

		@Override
		public void handleMessage(Message msg) {
			if (curLevel < mLevel - 1) {
				if (curStep == 0) {
					srcColor[0] = startColors[curLevel];
					srcColor[1] = endColors[curLevel];
					dstColor[0] = startColors[curLevel + 1];
					dstColor[1] = endColors[curLevel + 1];
				}

				if (curStep < totalStep) {
					mColor[0] = getColor(srcColor[0], dstColor[0], curStep);
					mColor[1] = getColor(srcColor[1], dstColor[1], curStep);

					if (curStep == totalStep - 1) {
						curLevel++;
						curStep = 0;
					} else {
						curStep++;
					}
				}

				sendEmptyMessageDelayed(0, interval);
			} else {
				mColor[0] = startColors[curLevel];
				mColor[1] = endColors[curLevel];
				isDone = true;
			}

			setChanged();

			long p = (long) ((curLevel + curStep / (float) totalStep)
					/ LEVEL_COUNT * 100);
			notifyObservers(new NotifyData(mColor, p, isDone));

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

	static public class NotifyData {
		public int[] color;
		public long percentage;
		public boolean isDone;

		public NotifyData(int[] c, long p, boolean b) {
			color = c;
			percentage = p;
			isDone = b;
		}
	}

}
