/**
 * 
 */
package com.tamaaan.android;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * @author o-ta
 *
 */
public class MainView extends Temperament implements SurfaceHolder.Callback, Runnable
{
	private static int SAMPLING_RATE = 44100;
	private static int BUFFER = 1024;

	private AudioTrack track;
	private short samples[];
	private ArrayList<SoundData> sContainer;

	private static double e = 0.8;
	private static double d = 0.99;
	private float gx;
	private float gy;
	private Paint paint;
	private SurfaceHolder holder;
	private Thread thread;
	private ArrayList<Circle> container;
	private int width;
	private int height;

	private int counter;

	public MainView(Context context)
	{
		super(context);
		holder = null;
		thread = null;
		samples  = new short[BUFFER];
		sContainer = new ArrayList<SoundData>();
		container = new ArrayList<Circle>();
		paint = new Paint();
		paint.setAntiAlias(true);
		gx = gy = 0;

		int minBufferSize = AudioTrack.getMinBufferSize(SAMPLING_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
		track = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLING_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);
		track.play();

		// getHolder()メソッドでSurfaceHolderを取得。さらにコールバックを登録
		getHolder().addCallback(this);
	}

	//SurfaceView生成時に呼び出される
	public void surfaceCreated(SurfaceHolder holder)
	{
		this.holder = holder;
		thread = new Thread(this);
	}

	//SurfaceView変更時に呼び出される
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if(thread != null )
		{
			this.width  = width;
			this.height = height;
			thread.start();
		}
	}

	//SurfaceView破棄時に呼び出される
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		thread = null;
		track.stop();
		track.release();
	}

	//スレッドによるSurfaceView更新処理
	public void run()
	{
		SoundData sData;
		int sSize;

		while (thread != null)
		{
			//描画処理
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.argb(255, 255, 255, 255));
			int size = container.size();

			// 音を生成＆書き込み
			for(int i = 0; i < BUFFER; i++ )
			{
				float totalWav = 0.0f;
				sSize = sContainer.size();
				while(sSize > 0)
				{
					sData = sContainer.get(sSize-1);
					sData.position =  (float) ((sData.position + sData.w)%(2*Math.PI));

					float eachWav;
					if(sData.position < Math.PI) eachWav = (float) (1 - 2*sData.position/Math.PI);
					else eachWav = (float) (-1 + 2 * (sData.position - Math.PI)/Math.PI);

					totalWav += eachWav * 0.4 * sData.volume;
					sSize--;
				}

				// データを範囲内に丸める
				totalWav = (totalWav > -0.9)?(totalWav):(-0.9f);
				totalWav = (totalWav < 0.9)?(totalWav):(0.9f);
				samples[i] =  (short) (Short.MAX_VALUE * totalWav);
			}
			track.write(samples, 0, samples.length);

			// 音データの更新＆円の描画
			sSize = sContainer.size();
			while(sSize > 0)
			{
				sData = sContainer.get(sSize-1);
				paint.setColor(Color.argb((int)(255*sData.volume), sData.cr, sData.cg, sData.cb));
				canvas.drawCircle(sData.x, sData.y, sData.radius, paint);
				sData.render();
				if(sData.state == 3) sContainer.remove(sData);
				sSize--;
			}

			// 簡易衝突計算
			for(int i=0 ; i<size ; i++)
			{
				for(int j=0 ; j<size ; j++)
				{
					if(j<=i) continue;

					Circle a = container.get(i);
					Circle b = container.get(j);
					float ab_x = b.x - a.x;
					float ab_y = b.y - a.y;
					float tr = a.radius + b.radius;

					if(ab_x*ab_x + ab_y*ab_y < tr*tr)
					{

						float len = (float)Math.sqrt(ab_x*ab_x + ab_y*ab_y);
						float distance = (a.radius + b.radius) - len;
						if(len>0) len = 1 / len;
						ab_x *= len;
						ab_y *= len;

						distance /= 2.0;
						a.x -= ab_x * distance;
						a.y -= ab_y * distance;
						b.x += ab_x * distance;
						b.y += ab_y * distance;

						// 衝突後の速度を計算
						float ma = (float) ((b.m / (a.m + b.m))*(1 + e)* ((b.dx - a.dx)*ab_x + (b.dy - a.dy)*ab_y));
						float mb = (float) ((a.m / (a.m + b.m))*(1 + e)* ((a.dx - b.dx)*ab_x + (a.dy - b.dy)*ab_y));
						a.dx += ma*ab_x;
						a.dy += ma*ab_y;
						b.dx += mb*ab_x;
						b.dy += mb*ab_y;

						// ---------------------------------------------
						// 衝突時に音
						//						float frequency = (float) (440 * Math.pow(2, ((1.0 / 12.0) * 20 * Math.random())));
						if (counter%3 == 0) {
							sContainer.add(new SoundData(ionicTemperament(),0.4f, 0.01f, 2, 0.8f, a.x, a.y, a.cr, a.cg, a.cb));
						} else if (counter%2 == 0) {
							sContainer.add(new SoundData(ionicTemperament01(),0.4f, 0.01f, 2, 0.8f, a.x, a.y, a.cr, a.cg, a.cb));		
						} else if (counter%4 == 0) {
							sContainer.add(new SoundData(ionicTemperament02(),0.4f, 0.01f, 2, 0.8f, a.x, a.y, a.cr, a.cg, a.cb));
						} else if (counter%5 == 0) {
							sContainer.add(new SoundData(ionicTemperament(),0.4f, 0.01f, 2, 0.8f, a.x, a.y, a.cr, a.cg, a.cb));	
						}
						counter++;

						// ---------------------------------------------
					}
				}
			}

			// 計算をボールに反映
			for(int i = 0 ; i < size ; i++)
			{
				Circle a = container.get(i);
				a.dx *= d;
				a.dy *= d;
				a.dx += gx;
				a.dy += gy;
				a.x += a.dx;
				a.y += a.dy;

				if(a.x < a.radius)
				{
					a.x = a.radius;
					a.dx *= -1;
				}
				if(a.y < a.radius)
				{
					a.y = a.radius;
					a.dy *= -1;
				}
				if(a.x > width-a.radius)
				{
					a.x = width-a.radius;
					a.dx *= -1;
				}
				if(a.y > height-a.radius)
				{
					a.y = height-a.radius;
					a.dy *= -1;
				}
				paint.setColor(Color.argb(255, a.cr, a.cg, a.cb));
				canvas.drawCircle(a.x, a.y, a.radius, paint);
			}
			holder.unlockCanvasAndPost(canvas);
		}
	}

	// クリック時のイベント
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float x = event.getX();
			float y = event.getY();
			float dx = (float)(Math.random()*10-5);
			float dy = (float)(Math.random()*10-5);
			float ran = (float)Math.random();
			float r = ran*20+5;
			float m = ran*10+10;
			int cr = (int)(255 * Math.random());
			int cb = (int)(255 * Math.random());
			int cg = (int)(255 * Math.random());

			//			float frequency = (float) (440 * Math.pow(2, ((1.0 / 12.0) * 20 * Math.random())));
			if (counter%3 == 0) {
				sContainer.add(new SoundData(ionicTemperament(),0.4f, 0.01f, 2, 0.8f, event.getX(), event.getY(), cr, cg, cb));
			} else if (counter%2 == 0) {
				sContainer.add(new SoundData(ionicTemperament01(),0.4f, 0.01f, 2, 0.8f, event.getX(), event.getY(), cr, cg, cb));		
			} else if (counter%4 == 0) {
				sContainer.add(new SoundData(ionicTemperament(),0.4f, 0.01f, 2, 0.8f, event.getX(), event.getY(), cr, cg, cb));
			} else if (counter%5 == 0) {
				sContainer.add(new SoundData(ionicTemperament01(),0.4f, 0.01f, 2, 0.8f, event.getX(), event.getY(), cr, cg, cb));	
			}

			container.add(new Circle(r, x, y, dx, dy, m, cr, cg, cb));
		}
		return true;
	}

	// 加速度の更新
	public void setAcce(float gx, float gy)
	{
		this.gx = gx;
		this.gy = gy;
	}
}