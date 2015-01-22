package com.example.seekit;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarTri extends Activity {
	String identificador=null;
	String nombreTri=null;
	String img=null; 
	int statusCode=-1;
	String idTri=null;
	
	JSONObject json=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_tri);

			identificador=getIntent().getStringExtra(
				"identificador");
		nombreTri=getIntent().getStringExtra(
				"nombreTri");
		img=getIntent().getStringExtra(
				"img");
		idTri=getIntent().getStringExtra(
				"idTri");
		try {
			json = new JSONObject(getIntent().getStringExtra(
					"json"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cargemos los valores
		EditText eEditNombre = (EditText) findViewById(R.id.editNombreEditarTri);
		eEditNombre.setText(nombreTri);

		EditText eEditMAC1 = (EditText) findViewById(R.id.editMAC1EditarTri);
		eEditMAC1.setText(identificador.substring(0,2));

		EditText eEditMAC2 = (EditText) findViewById(R.id.editMAC2EditarTri);
		eEditMAC2.setText(identificador.substring(2,4));

		EditText eEditMAC3 = (EditText) findViewById(R.id.editMAC3EditarTri);
		eEditMAC3.setText(identificador.substring(4,6));

		EditText eEditMAC4 = (EditText) findViewById(R.id.editMAC4EditarTri);
		eEditMAC4.setText(identificador.substring(6,8));

		EditText eEditMAC5 = (EditText) findViewById(R.id.editMAC5EditarTri);
		eEditMAC5.setText(identificador.substring(8,10));

		EditText eEditMAC6 = (EditText) findViewById(R.id.editMAC6EditarTri);
		eEditMAC6.setText(identificador.substring(10,12));

		EditText eEditMAC7 = (EditText) findViewById(R.id.editMAC7EditarTri);
		eEditMAC7.setText(identificador.substring(12,14));

		EditText eEditViewFotoAddTri = (EditText) findViewById(R.id.editViewFotoEditarTri);
		eEditViewFotoAddTri.setText(img);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_tri, menu);
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

	public void editarTri(View view) {
		EditText eEditNombre = (EditText) findViewById(R.id.editNombreEditarTri);
		String nombre = eEditNombre.getText().toString();

		EditText eEditMAC1 = (EditText) findViewById(R.id.editMAC1EditarTri);
		String editMAC1 = eEditMAC1.getText().toString();

		EditText eEditMAC2 = (EditText) findViewById(R.id.editMAC2EditarTri);
		String editMAC2 = eEditMAC2.getText().toString();

		EditText eEditMAC3 = (EditText) findViewById(R.id.editMAC3EditarTri);
		String editMAC3 = eEditMAC3.getText().toString();

		EditText eEditMAC4 = (EditText) findViewById(R.id.editMAC4EditarTri);
		String editMAC4 = eEditMAC4.getText().toString();

		EditText eEditMAC5 = (EditText) findViewById(R.id.editMAC5EditarTri);
		String editMAC5 = eEditMAC5.getText().toString();

		EditText eEditMAC6 = (EditText) findViewById(R.id.editMAC6EditarTri);
		String editMAC6 = eEditMAC6.getText().toString();

		EditText eEditMAC7 = (EditText) findViewById(R.id.editMAC7EditarTri);
		String editMAC7 = eEditMAC7.getText().toString();

		EditText eEditViewFotoAddTri = (EditText) findViewById(R.id.editViewFotoEditarTri);
		String foto = eEditViewFotoAddTri.getText().toString();
		
		if (TextUtils.isEmpty(nombre)) {
			eEditNombre.setError("Por favor, introduzca un nombre");
			return;
		} else {
			if (!isInputTextValid(nombre)) {
				eEditNombre.setError("Formato invalido");
				return;
			}
		}
		if (TextUtils.isEmpty(editMAC1)) {
			eEditMAC1.setError("!");
			return;
		}
		if (TextUtils.isEmpty(editMAC2)) {
			eEditMAC2.setError("!");
			return;
		}

		if (TextUtils.isEmpty(editMAC3)) {
			eEditMAC3.setError("!");
			return;
		}
		if (TextUtils.isEmpty(editMAC4)) {
			eEditMAC4.setError("!");
			return;
		}
		if (TextUtils.isEmpty(editMAC5)) {
			eEditMAC5.setError("!");
			return;
		}
		if (TextUtils.isEmpty(editMAC6)) {
			eEditMAC6.setError("!");
			return;
		}
		if (TextUtils.isEmpty(editMAC7)) {
			eEditMAC7.setError("!");
			return;
		}
		/*
		 * if (TextUtils.isEmpty(descripcion)) {
		 * eEditDescripcionAddTri.setError("Indtroducir breve descripcion");
		 * return; } else { if (!isInputTextValid(nombre)) {
		 * eEditNombe.setError("Formato invalido"); return; } }
		 */

		if (isNetworkAvailable()) {

			GetEditarTriTask getEditarTriTask = new GetEditarTriTask();
			getEditarTriTask.execute();
		} else {
			Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG)
					.show();
		}

	}
	private class GetEditarTriTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {
			JSONObject jsonResponse = null;
			try {
				EditText eEditNombre = (EditText) findViewById(R.id.editNombreEditarTri);
				String nombre = eEditNombre.getText().toString();

				EditText eEditMAC1 = (EditText) findViewById(R.id.editMAC1EditarTri);
				String editMAC1 = eEditMAC1.getText().toString();

				EditText eEditMAC2 = (EditText) findViewById(R.id.editMAC2EditarTri);
				String editMAC2 = eEditMAC2.getText().toString();

				EditText eEditMAC3 = (EditText) findViewById(R.id.editMAC3EditarTri);
				String editMAC3 = eEditMAC3.getText().toString();

				EditText eEditMAC4 = (EditText) findViewById(R.id.editMAC4EditarTri);
				String editMAC4 = eEditMAC4.getText().toString();

				EditText eEditMAC5 = (EditText) findViewById(R.id.editMAC5EditarTri);
				String editMAC5 = eEditMAC5.getText().toString();

				EditText eEditMAC6 = (EditText) findViewById(R.id.editMAC6EditarTri);
				String editMAC6 = eEditMAC6.getText().toString();

				EditText eEditMAC7 = (EditText) findViewById(R.id.editMAC7EditarTri);
				String editMAC7 = eEditMAC7.getText().toString();

				EditText eEditViewFotoAddTri = (EditText) findViewById(R.id.editViewFotoEditarTri);
				String foto = eEditViewFotoAddTri.getText().toString();

				HttpClient client = new DefaultHttpClient();

				String identificador = editMAC1.concat(editMAC2)
						.concat(editMAC3).concat(editMAC4).concat(editMAC5)
						.concat(editMAC6).concat(editMAC7);
				
				
				
				String url = "http://192.168.56.1:8080/seekit/seekit/editarTri?idTri="
						+ idTri
						+ "&nombre="
						+ nombre
						+ "&identificador="
						+ identificador
						+ "&foto="
						+ img;

				Log.d("editar usuario",url);
				HttpGet httpGet = new HttpGet(url);
				
				try {

					HttpResponse response = client.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					statusCode = statusLine.getStatusCode();
					StringBuilder builder = new StringBuilder();

					if (statusCode == 200) {
						Log.d("editar usuario","es OK");						
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(content));
						String line;

						while ((line = reader.readLine()) != null) {
							builder.append(line);
							jsonResponse = new JSONObject(line);

						}

					}

				} catch (Exception e) {

					e.printStackTrace();

				}

			} catch (Exception e) {
				Log.d("PORQUE NO ANDA4", "Unsuccessful HTTP Response Code:");
				e.printStackTrace();
			}
			return jsonResponse;
		}
		@Override
		protected void onPostExecute(JSONObject result) {

			handleResult();
		}

		private void handleResult() {
			if (statusCode == 200) {
				Intent intent = new Intent(EditarTri.this, MainActivity.class);
				intent.putExtra("PARENT_NAME", "EditarTri");
				intent.putExtra("json", getIntent().getStringExtra("json"));
				Log.d("EditarTri",getIntent().getStringExtra("json"));
				startActivity(intent);
				finish();

			} else {
				if (statusCode == 0) {
					Toast.makeText(EditarTri.this,
							"Nuestros servidores estan caidos.",
							Toast.LENGTH_LONG).show();
				}

			}
		}
	}
	
	
	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}

		return isAvailable;
	}

	private boolean isInputTextValid(String inputText) {

		String regExpn = "[a-zA-Z ]+$";

		CharSequence inputStr = inputText;

		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else
			return false;
	}
}
