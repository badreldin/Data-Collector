package ben.android.datacollector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TagActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ben.android.tag.TaggedCollection";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		Button ok = (Button)findViewById(R.id.ok2);
		
	}
	public void taggedCollection(View view) {    
		Intent intent = new Intent(this, TaggedCollectionActivity.class);
		EditText editText = (EditText) findViewById(R.id.tagText);
		String tag = editText.getText().toString();
		
		Intent intent2 = getIntent();
		String ipAddress = intent2.getStringExtra(AccelerometerActivity.EXTRA_MESSAGE2);
		String [] tagValues = {ipAddress,tag};
		intent.putExtra(EXTRA_MESSAGE, tagValues);
		startActivity(intent);
	
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}
	
	

}
