/**
 * 
 */
package com.tamaaan.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * @author Administrator
 *
 */
public class Temperament extends SurfaceView {

	/**
	 * @param context
	 */
	public Temperament(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public Temperament(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public Temperament(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public float pitaTemperament() {
		int rnd = (int) (Math.random()*10);
		float frequency;
		switch (rnd) {
		case 0:
			frequency = 220;	break;
		case 1:
			frequency = 246;	break;
		case 2:
			frequency = 278;	break;
		case 3:
			frequency = 293;	break;
		case 4:
			frequency = 330;	break;
		case 5:
			frequency = 369;	break;
		case 6:
			frequency = 415;	break;
		case 7:
			frequency = 440;	break;
		default:
			frequency = (float) (440 * Math.pow(2, ((1.0 / 12.0) * 20 * Math.random())));
			break;
		}
		return frequency;
	}

	public float ionicTemperament() {
		int rnd = (int) (Math.random()*10);
		float frequency = 0;
		switch (rnd) {
		case 0:
			frequency = 220;	// 1/1
			break;
		case 1:
			frequency = 247;	// 8/9
			break;
		case 2:
			frequency = 275;	// 5/4
			break;
		case 3:
			frequency = 293; 	// 4/3
			break;
		case 4:
			frequency = 330;	// 3/2
			break;
		case 5:
			frequency = 366;	// 5/3
			break;
		case 6:
			frequency = 369;	// 9/5
			break;
		case 7:
			frequency = 440;	// 2/1
			break;
		case 8:
			frequency = 247 * 2;
			break;
		case 9:
			frequency = 275 * 2;
			break;
		case 10:
			frequency = 293 * 2;
			break;
	
		default:
			break;
		}
		return frequency;
	}
	
	public float ionicTemperament01() {
		int rnd = (int) (Math.random()*10);
		float frequency = 0;
		switch (rnd) {
		case 0:
			frequency = 440;	// 1/1
			break;
		case 1:
			frequency = 247 * 2;	// 8/9
			break;
		case 2:
			frequency = 275 * 2;	// 5/4
			break;
		case 3:
			frequency = 293 * 2; 	// 4/3
			break;
		case 4:
			frequency = 330 * 2;	// 3/2
			break;
		case 5:
			frequency = 366 * 2;	// 5/3
			break;
		case 6:
			frequency = 369 * 2;	// 9/5
			break;
		case 7:
			frequency = 880;	// 2/1
			break;
		case 8:
			frequency = 247 * 2*2;
			break;
		case 9:
			frequency = 275 * 2*2;
			break;
		case 10:
			frequency = 293 * 2*2;
			break;
	
		default:
			break;
		}
		return frequency;
	}

	public float ionicTemperament02() {
		int rnd = (int) (Math.random()*10);
		float frequency = 0;
		switch (rnd) {
		case 0:
			frequency = 880;	// 1/1
			break;
		case 1:
			frequency = 247 * 2*2;	// 8/9
			break;
		case 2:
			frequency = 275 * 2*2;	// 5/4
			break;
		case 3:
			frequency = 293 * 2*2; 	// 4/3
			break;
		case 4:
			frequency = 330 * 2*2;	// 3/2
			break;
		case 5:
			frequency = 366 * 2*2;	// 5/3
			break;
		case 6:
			frequency = 369 * 2*2;	// 9/5
			break;
		case 7:
			frequency = 880 * 2;	// 2/1
			break;
		case 8:
			frequency = 247 * 2*2*2;
			break;
		case 9:
			frequency = 275 * 2*2*2;
			break;
		case 10:
			frequency = 293 * 2*2*2;
			break;
	
		default:
			break;
		}
		return frequency;
	}

}