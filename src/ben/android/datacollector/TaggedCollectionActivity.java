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
import android.widget.EditText;
	import android.content.Context;
	import android.content.Intent;
	import android.hardware.Sensor;
	import android.hardware.SensorManager;
	import android.os.Handler;
import android.os.SystemClock;


	public class TaggedCollectionActivity extends Activity {
		public final static String EXTRA_MESSAGE1 = "ben.android.TaggedCollection.STOPTAG";
		public final static String EXTRA_MESSAGE2 = "ben.android.TaggedCollection.LOG";
		private Handler mHandler;
		private SensorManager mSensorManager ;
		private Sensor mSensor ;
		private AccelerometerReader accelerometerReader;
		String AccelerometerData = "";
		static String ipAddress = "";
		Timestamp timeStamp;
		static boolean startCollecting = false;
		static boolean startTag = false;
		static String tag = "";
		static String [] tagValues;

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
						if (startTag)
						{
					        
							dataOutputStream.writeUTF("Tag: " + tag + " Accelerometer data: " + 
						    AccelerometerData	+ "  Time Stamp: " + timeStamp);
						}
						else 
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

				} // end if

				mHandler.postAtTime(this, millisElapsed + 500); //post in 0.5 seconds


			}
		};

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tagged_collection);
			
			Intent intent = getIntent();
			tagValues =intent.getStringArrayExtra(TagActivity.EXTRA_MESSAGE);
			ipAddress = tagValues [0];
			tag = tagValues[1];
			
			mSensorManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE );
			mSensor = mSensorManager.getDefaultSensor (Sensor.TYPE_ACCELEROMETER );

			if( mSensor == null )
			{

				super.onDestroy();
			}
			accelerometerReader = new AccelerometerReader();     
			mSensorManager.registerListener (accelerometerReader , mSensor , SensorManager.SENSOR_DELAY_NORMAL);
			
			Button stopTag = (Button)findViewById(R.id.stopTag);
			Button stopCollecting2 = (Button)findViewById(R.id.stopCollecting2);
		
			
			startCollecting = true;
			startTag = true;
			mHandler = new Handler();
			mHandler.removeCallbacks(mUpdateTimeTask);
			mHandler.postDelayed(mUpdateTimeTask, 100);
			
			
		}


	public void stopTag(View view) {
		startTag = false;
		 Intent intent = new Intent(this, AccelerometerActivity.class);
		intent.putExtra(EXTRA_MESSAGE1,false);
		startActivity(intent);
		 
		
	}
	
		
			public void stopCollecting2(View view) {
				
				startCollecting = false;
				 Intent intent = new Intent(this, LogCollectionActivity.class);
				intent.putExtra(EXTRA_MESSAGE2, true);
				startActivity(intent);
				 
				
			}

}
