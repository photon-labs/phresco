package com.photon.phresco.nativeapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.photon.phresco.nativeapp.R;

public class MainActivity extends Activity {

	private static Button btn;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn=(Button)findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Toast.makeText(getApplicationContext(), getString(R.string.hello), Toast.LENGTH_LONG).show();
				
			}
		});
	}
}