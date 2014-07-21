package com.qihoo.unlock.utils;

import android.view.animation.Interpolator;

public class DampingInterpolater implements Interpolator {

	private double w1;
	private double w2;

	public DampingInterpolater(double DR, double NF) {
		w1 = -DR * NF;
		w2 = NF * Math.sqrt(1 - DR * DR);
	}

	@Override
	public float getInterpolation(float t) {
		t *= 25;
		return (float) (1 - Math.exp(w1 * t) * Math.cos(w2 * t));
	}
}
