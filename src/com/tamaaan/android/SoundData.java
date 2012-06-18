/**
 * 
 */
package com.tamaaan.android;

/**
 * @author Administrator
 *
 */
//音データを保持するクラス
public class SoundData
{
	private static int SAMPLING_RATE = 44100;
	
	public float frequency;
	public float volume;
	public float position;
	public float x;
	public float y;
	public float w;
	public int state;
	public int cr;
	public int cg;
	public int cb;
	public int radius;
	private int count;
	private float fadeIn;
	private float fadeOut;
	private float show;
	private float volMax;

	// コンストラクタ
	public SoundData(float frequency, float fadeIn, float fadeOut, int show, float volMax, float x, float y, int cr, int cg, int cb)
	{
		this.frequency = frequency;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.show = show;
		this.volMax = volMax;
		this.x = x;
		this.y = y;

		position = 0;
		volume = state = count = radius = 0;
		w = (float) ((2*Math.PI * frequency) / SAMPLING_RATE);
		this.cr = cr - 50;
		this.cg = cg - 50;
		this.cb = cb - 50;
//		cr = (int)(255 * Math.random());
//		cg = ;
//		cb = (int)(255 * Math.random());
	}

	// 更新用
	public void render()
	{
		radius +=2;
		switch(state)
		{
		// 音をフェードインさせる
		case 0:
			volume += fadeIn;
			if(volume>=volMax)
			{
				volume = volMax;
				state = 1;
			}
			break;

			// 音を流す
		case 1:
			count ++;
			if(count == show) state = 2;
			break;

			// 音をフェードアウトさせる
		case 2:
			volume -= fadeOut;
			if(volume <= 0)
			{
				state = 3;
				volume = 0;
			}
			break;
		}
	}
}