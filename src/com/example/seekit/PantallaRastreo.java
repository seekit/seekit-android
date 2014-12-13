package com.example.seekit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	
	JSONObject json;
	
	String parentActivity = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_rastreo);
		
		JSONObject obj;
		try {
			obj = new JSONObject(getIntent().getStringExtra(
					"json"));
			json = obj;
		} catch (JSONException e) {
			Log.d("Pantalla Rastreo","ACA NO DEBOE STAR ");
			e.printStackTrace();
		}
		
		 ImagenesListeners();
	}

	private void ImagenesListeners() {
		imageButtonShare = (ImageButton) findViewById(R.id.imageButtonShare); 
		imageButtonShare.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View view) {
 
				Log.d("pantalla Rastreo", "Share");
				Intent intent = new Intent(PantallaRastreo.this, PantallaCompartir.class);
				try {
					
					Log.d("Pantala Rastreo", json.toString());
					// pongo solo el usuario, el resto no lo quiero ya q lo voy a
					// recuperar cada vez q entre.
					
						intent.putExtra("json", json.toString());
					

				} catch (Exception e) {
					Log.d("Pantalla Rastreo", "ACA NUNCA DEBO ESTAR");
				}
				intent.putExtra("identificador",getIntent().getStringExtra(
						"identificador") );
				intent.putExtra("nombreTri",getIntent().getStringExtra(
						"nombreTri") );
				intent.putExtra("img",getIntent().getStringExtra(
						"img") );
				intent.putExtra("idTri",getIntent().getStringExtra(
						"idTri") );
				startActivity(intent);

 
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
		if (id == R.id.action_editTri) {
			Log.d("Pantalla Rastreo", "editar Tri");
			Intent intent = new Intent(PantallaRastreo.this, EditarTri.class);
			try {
				
				Log.d("Pantala Rastreo", json.toString());
				// pongo solo el usuario, el resto no lo quiero ya q lo voy a
				// recuperar cada vez q entre.
				
					intent.putExtra("json", json.toString());
				

			} catch (Exception e) {
				Log.d("Pantalla RastreoR", "ACA NUNCA DEBO ESTAR");
			}
			intent.putExtra("identificador",getIntent().getStringExtra(
					"identificador") );
			intent.putExtra("nombreTri",getIntent().getStringExtra(
					"nombreTri") );
			intent.putExtra("img",getIntent().getStringExtra(
					"img") );
			intent.putExtra("idTri",getIntent().getStringExtra(
					"idTri") );
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
