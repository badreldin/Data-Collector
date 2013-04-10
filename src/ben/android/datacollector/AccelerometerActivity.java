package ben.android.datacollector;

import java.io.DataOutputStream; 
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;


public class AccelerometerActivity extends Activity {
	public final static String EXTRA_MESSAGE1 = "ben.android.accelerometer.LOG";
	public final static String EXTRA_MESSAGE2 = "ben.android.accelerometer.TAG";
	private Handler mHandler;
	private SensorManager mSensorManager ;
	private Sensor mSensor ;
	private AccelerometerReader accelerometerReader;
	String AccelerometerData = "";
	static String ipAddress = "";
	Timestamp timeStamp;
	static boolean startCollecting = false;

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			
			long millisElapsed = SystemClock.uptimeMillis();
			AccelerometerData = "Ax:" + accelerometerReader.getAx() + " " + 
					"Ay:" + accelerometerReader.getAy() + " " + "Az:" + accelerometerReader.getAz();
			java.util.Date date= new java.util.Date();
			timeStamp = new Timestamp(date.getTime());
			if (startCollecting){
				Socket socket = null;
				DataOutputStream dataOutputStream = null;
				
				try {
					socket = new Socket(ipAddress , 4444);
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					
					dataOutputStream.writeUTF("  Accelerometer data: " + AccelerometerData
							+ "  Time Stamp: " + timeStamp);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					if (socket != null){
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					if (dataOutputStream != null){
						try {
							dataOutputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 


				} // end finally

				mHandler.postAtTime(this, millisElapsed + 500); //post in 0.5 seconds

			} // end if


		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		
		Intent intent = getIntent();
		ipAddress = intent.getStringExtra(LogCollectionActivity.EXTRA_MESSAGE);
		
		mSensorManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE );
		mSensor = mSensorManager.getDefaultSensor (Sensor.TYPE_ACCELEROMETER );

		if( mSensor == null )
		{

			super.onDestroy();
		}
		accelerometerReader = new AccelerometerReader();     
		mSensorManager.registerListener (accelerometerReader , mSensor , SensorManager.SENSOR_DELAY_NORMAL);
		
		Button buttonTag = (Button)findViewById(R.id.startTag);
		Button buttonStop = (Button)findViewById(R.id.stopCollecting);
	
		
		startCollecting = true;
		
		mHandler = new Handler();
		mHandler.removeCallbacks(mUpdateTimeTask);
		mHandler.postDelayed(mUpdateTimeTask, 100);
		
		
	}
	
/*	@Override
	public void onPause() {
	    super.onPause(); 
	    startCollecting = false;
	}

	@Override
	public void onResume()
	{
	   super.onResume();
	startCollecting = true;	
	 
	}
	*/
	public void startTag(View view) {
		
		startCollecting = false;
		 Intent intent = new Intent(this, TagActivity.class);
		intent.putExtra(EXTRA_MESSAGE2, ipAddress);
		startActivity(intent);
		 
		
	}
	
		
			public void stopCollecting(View view) {
				startCollecting = false;
				 Intent intent = new Intent(this, LogCollectionActivity.class);
				intent.putExtra(EXTRA_MESSAGE1, ipAddress);
				startActivity(intent);
				 
				
			}
			
}
