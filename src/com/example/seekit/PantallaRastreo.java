package com.example.seekit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class PantallaRastreo extends Activity {
	ImageButton imageButtonShare;
	ImageButton imageButtonLost;
	ImageButton imageButtonMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_rastreo);
		
		 ImagenesListeners();
	}

	private void ImagenesListeners() {
		imageButtonShare = (ImageButton) findViewById(R.id.imageButtonShare); 
		imageButtonShare.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View view) {
 
			   Toast.makeText(PantallaRastreo.this,
				"ImageButton is clicked!", Toast.LENGTH_SHORT).show();
 
			}
 
		});
		imageButtonLost = (ImageButton) findViewById(R.id.imageButtonLost); 
		imageButtonLost.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View view) {
 
			   Toast.makeText(PantallaRastreo.this,
				"ZAUIWAUI", Toast.LENGTH_SHORT).show();
 
			}
 
		});
		imageButtonMap = (ImageButton) findViewById(R.id.imageButtonMap); 
		imageButtonMap.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(PantallaRastreo.this, Maps.class);
				startActivity(intent);
			}
 
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pantalla_rastreo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
