package ben.android.datacollector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DataCollector extends Activity {
	public final static String EXTRA_MESSAGE = "ben.android.accelerometer.IP";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_collector);
		
		Button ok = (Button)findViewById(R.id.ok);
		Button cancel = (Button)findViewById(R.id.cancel);
	}
	public void sendIP(View view) {    
		Intent intent = new Intent(this, LogCollectionActivity.class);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		String ipAddress = editText1.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, ipAddress);
		startActivity(intent);
		}
	public void deleteIP(View view) {    
		EditText editText2 = (EditText) findViewById(R.id.editText1);
		editText2.setText("");
		
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_collector, menu);
		return true;
	}

}
