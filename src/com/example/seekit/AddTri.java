package com.example.seekit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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

public class AddTri extends Activity {

	protected JSONObject resultFromWs;
	public JSONObject nuevoTri;
	int statusCode = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_tri, menu);
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

	public void agregarTri(View view) {
		EditText eEditNombe = (EditText) findViewById(R.id.editNombeAddTri);
		String nombre = eEditNombe.getText().toString();

		EditText eEditMAC1 = (EditText) findViewById(R.id.editMAC1);
		String editMAC1 = eEditMAC1.getText().toString();

		EditText eEditMAC2 = (EditText) findViewById(R.id.editMAC2);
		String editMAC2 = eEditMAC2.getText().toString();

		EditText eEditMAC3 = (EditText) findViewById(R.id.editMAC3);
		String editMAC3 = eEditMAC3.getText().toString();

		EditText eEditMAC4 = (EditText) findViewById(R.id.editMAC4);
		String editMAC4 = eEditMAC4.getText().toString();

		EditText eEditMAC5 = (EditText) findViewById(R.id.editMAC5);
		String editMAC5 = eEditMAC5.getText().toString();

		EditText eEditMAC6 = (EditText) findViewById(R.id.editMAC6);
		String editMAC6 = eEditMAC6.getText().toString();

		EditText eEditMAC7 = (EditText) findViewById(R.id.editMAC7);
		String editMAC7 = eEditMAC7.getText().toString();

		// EditText eEditDescripcionAddTri = (EditText)
		// findViewById(R.id.editDescripcionAddTri);
		// String descripcion = eEditDescripcionAddTri.getText().toString();

		EditText eEditViewFotoAddTri = (EditText) findViewById(R.id.editViewFotoAddTri);
		String foto = eEditViewFotoAddTri.getText().toString();

		if (TextUtils.isEmpty(nombre)) {
			eEditNombe.setError("Por favor, introduzca un nombre");
			return;
		} else {
			if (!isInputTextValid(nombre)) {
				eEditNombe.setError("Formato invalido");
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

			GetAddTriTask getAddTriTask = new GetAddTriTask();
			getAddTriTask.execute();
		} else {
			Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG)
					.show();
		}

	}

	private class GetAddTriTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {

			try {
				
				JSONObject obj = new JSONObject(getIntent().getStringExtra(
						"json"));
				Log.d("addTri",obj.toString());
				

				EditText eEditNombe = (EditText) findViewById(R.id.editNombeAddTri);
				String nombre = eEditNombe.getText().toString();

				EditText eEditMAC1 = (EditText) findViewById(R.id.editMAC1);
				String editMAC1 = eEditMAC1.getText().toString();

				EditText eEditMAC2 = (EditText) findViewById(R.id.editMAC2);
				String editMAC2 = eEditMAC2.getText().toString();

				EditText eEditMAC3 = (EditText) findViewById(R.id.editMAC3);
				String editMAC3 = eEditMAC3.getText().toString();

				EditText eEditMAC4 = (EditText) findViewById(R.id.editMAC4);
				String editMAC4 = eEditMAC4.getText().toString();

				EditText eEditMAC5 = (EditText) findViewById(R.id.editMAC5);
				String editMAC5 = eEditMAC5.getText().toString();

				EditText eEditMAC6 = (EditText) findViewById(R.id.editMAC6);
				String editMAC6 = eEditMAC6.getText().toString();

				EditText eEditMAC7 = (EditText) findViewById(R.id.editMAC7);
				String editMAC7 = eEditMAC7.getText().toString();

				String identificador = editMAC1.concat(editMAC2)
						.concat(editMAC3).concat(editMAC4).concat(editMAC5)
						.concat(editMAC6).concat(editMAC7);

				/*
				 * EditText eEditDescripcionAddTri = (EditText)
				 * findViewById(R.id.editDescripcionAddTri); String descripcion
				 * = eEditDescripcionAddTri.getText() .toString();
				 */

				/*
				 * @RequestMapping(value = "/addTri") public ResponseEntity<Tri>
				 * getAddTri(
				 * 
				 * @RequestParam(value = "idUsuario", required = false) String
				 * idUsuario,
				 * 
				 * @RequestParam(value = "identificador", required = false)
				 * String identificador,
				 * 
				 * @RequestParam(value = "nombre", required = false) String
				 * nombre,
				 * 
				 * @RequestParam(value = "foto", required = false) String foto)
				 */

				HttpClient client = new DefaultHttpClient();
				Log.d("VEAMOS Q ESTOY HACIENDO",
						"http://192.168.56.1:8080/seekit/seekit/addTri?idUsuario="
								+ obj.getString(
										"idUsuario") + "&identificador="
								+ identificador + "&nombre=" + nombre
								+ "&foto=" + null);
				
				HttpGet httpGet = new HttpGet(
						"http://192.168.56.1:8080/seekit/seekit/addTri?idUsuario="
								+ obj.getString(
										"idUsuario") + "&identificador="
								+ identificador + "&nombre=" + nombre
								+ "&foto=" + null);

				try {

					HttpResponse response = client.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					statusCode = statusLine.getStatusCode();

				} catch (Exception e) {

					e.printStackTrace();

				}

			} catch (Exception e) {
				Log.d("PORQUE NO ANDA4", "Unsuccessful HTTP Response Code:");
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {

			handleResult();
		}

		private void handleResult() {
			if (statusCode == 200) {
				Intent intent = new Intent(AddTri.this, MainActivity.class);
				intent.putExtra("PARENT_NAME", "addTri");
				intent.putExtra("json", getIntent().getStringExtra("json"));
				startActivity(intent);

			} else {
				if (statusCode == 0) {
					Toast.makeText(AddTri.this,
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

	public boolean isInputTextValid(String inputText) {
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
