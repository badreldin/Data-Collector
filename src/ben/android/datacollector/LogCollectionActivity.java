package ben.android.datacollector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LogCollectionActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ben.android.accelerometer.STARTLOG";
	private CheckBox checkBox1;
    private String ipAddress= "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_collection);
		
		Intent intent = getIntent();
		ipAddress = intent.getStringExtra(DataCollector.EXTRA_MESSAGE);
		
	    checkBox1 = (CheckBox) findViewById(R.id.accelerometer);
	    Button start = (Button)findViewById(R.id.startLog);
		
		}
	
	public void startLog(View view) {    
		
		if (checkBox1.isChecked()) {       
		 Intent intent = new Intent(this, AccelerometerActivity.class);
		intent.putExtra(EXTRA_MESSAGE, ipAddress);
		startActivity(intent); 
		checkBox1.setChecked(false);         
		
		}
		}
public void backToMain(View view) {    
		      
		 Intent intent = new Intent(this, DataCollector.class);
		intent.putExtra(EXTRA_MESSAGE, ipAddress);
		startActivity(intent);    
		
		
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_collection, menu);
		return true;
	}

}
