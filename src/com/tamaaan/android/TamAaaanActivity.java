package com.tamaaan.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class TamAaaanActivity extends Activity  implements SensorEventListener
{
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private MainView view;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 画面を縦表示で固定
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		//センサーマネージャの取得
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

		//センサーの取得
		List<Sensor> list;
		list=sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (list.size()>0) accelerometer=list.get(0);
	}

	//アプリの開始
	protected void onResume()
	{
		//アプリの開始
		super.onResume();

		view = new MainView(this);
		setContentView(view);

		//センサーの処理の開始
		if (accelerometer!=null) sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
	}

	//アプリの停止
	protected void onStop()
	{
		//センサーの処理の停止
		sensorManager.unregisterListener(this);

		//アプリの停止
		super.onStop();
	}    

	//センサーリスナーの処理
	public void onSensorChanged(SensorEvent event)
	{
		//加速度の取得
		if (event.sensor==accelerometer)  view.setAcce(-event.values[0]*0.2f, event.values[1]*0.2f);
	}

	//精度変更イベントの処理
	public void onAccuracyChanged(Sensor sensor,int accuracy){}

	// 破棄の際に実行
	public void onDestroy()
	{
		super.onDestroy();
	}

}