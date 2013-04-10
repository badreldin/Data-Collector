package ben.android.datacollector;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class AccelerometerReader implements SensorEventListener {
	
	private float ax;
	private float ay;
	private float az;
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
			ax=event.values[0];
			ay=event.values[1];
			az=(float) (event.values[2] - 9.8);
		}
	}
	
	public float getAx() {
		return ax;
	}

	public float getAy() {
		return ay;
	}

	public float getAz() {
		return az;
	}	

}
