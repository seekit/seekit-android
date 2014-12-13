package com.example.seekit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Registro extends Activity {
	private CalendarView calendarView;
	private int yr, mon, dy;
	private Calendar selectedDate;
	// para el async methodo
	protected JSONObject resultFromWs;
	int statusCode = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		/*
		 * Calendar c = Calendar.getInstance(); yr = c.get(Calendar.YEAR); mon =
		 * c.get(Calendar.MONTH); dy = c.get(Calendar.DAY_OF_MONTH); Button
		 * datePickerButton = (Button) findViewById(R.id.date_picker_button);
		 * calendarView = (CalendarView) findViewById(R.id.calendar_view);
		 * datePickerButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { new
		 * DatePickerDialog(Registro.this, dateListener, yr, mon, dy) .show();
		 * Log.d("asdasd", "onclick"); } })
		 */;

		/*
		 * calendarView.setOnDateChangeListener(new OnDateChangeListener() {
		 * 
		 * @Override public void onSelectedDayChange(CalendarView view, int
		 * year, int month, int dayOfMonth) {
		 * Log.d("asdasd","onselecteddychange"); Toast.makeText( Registro.this,
		 * "Selected date is " + (month + 1) + "-" + dayOfMonth + "-" + year,
		 * Toast.LENGTH_SHORT).show(); } });
		 */
	}

	/*
	 * private DatePickerDialog.OnDateSetListener dateListener = new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * public void onDateSet(DatePicker view, int year, int monthOfYear, int
	 * dayOfMonth) {
	 * 
	 * selectedDate = Calendar.getInstance(); yr = year; mon = monthOfYear; dy =
	 * dayOfMonth; selectedDate.set(yr, mon, dy);
	 * calendarView.setDate(selectedDate.getTimeInMillis()); Log.d("asdasd", yr
	 * + "" + mon + dy); } };
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
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

	// en caso de click en aceptar, hay que registrar.
	public void cancelar(View view) {
		Intent itn = new Intent(Registro.this, Login.class);

		startActivity(itn);

	}

	// en caso de click en aceptar, hay que registrar.
	public void registrar(View view) {

		EditText eIngresarNombre = (EditText) findViewById(R.id.ingresarNombre);
		String nombre = eIngresarNombre.getText().toString();

		EditText eIngresarApellido = (EditText) findViewById(R.id.ingresarApellido);
		String apellido = eIngresarApellido.getText().toString();

		EditText eIngresarMail = (EditText) findViewById(R.id.ingresarMail);
		String mail = eIngresarMail.getText().toString();

		EditText eIngresarContrasena = (EditText) findViewById(R.id.ingresarContrasena);
		String contrasena = eIngresarContrasena.getText().toString();

		// EditText eIngresarFecha = (EditText)
		// findViewById(R.id.ingresarFecha);
		// String fechaNacimiento = eIngresarFecha.getText().toString();

		if (TextUtils.isEmpty(nombre)) {
			eIngresarNombre.setError("Por favor, introduzca su nombre");
			return;
		}
		if (TextUtils.isEmpty(apellido)) {
			eIngresarApellido.setError("Por favor, introduzca su apellido");
			return;
		}
		if (TextUtils.isEmpty(mail)) {
			eIngresarMail.setError("Por favor, introduzca su mail");
			return;
		} else {
			if (!isEmailValid(mail)) {
				eIngresarMail.setError("Formato invalido");
				return;
			}
		}
		if (TextUtils.isEmpty(contrasena)) {
			eIngresarContrasena.setError("Introduzca una contraseña valida");
			return;
		}
		if (isNetworkAvailable()) {

			GetRegisterTask getRegisterTask = new GetRegisterTask();
			getRegisterTask.execute();
		} else {
			Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG)
					.show();
		}

	}

	// para chequear q realmente sea un mail
	public boolean isEmailValid(String email) {
		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else
			return false;
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

	private class GetRegisterTask extends AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {
			JSONObject jsonResponse = null;
			try {

				EditText eIngresarNombre = (EditText) findViewById(R.id.ingresarNombre);
				String nombre = eIngresarNombre.getText().toString();

				EditText eIngresarApellido = (EditText) findViewById(R.id.ingresarApellido);
				String apellido = eIngresarApellido.getText().toString();

				EditText eIngresarMail = (EditText) findViewById(R.id.ingresarMail);
				String mail = eIngresarMail.getText().toString();

				EditText eIngresarContrasena = (EditText) findViewById(R.id.ingresarContrasena);
				String contrasena = eIngresarContrasena.getText().toString();

				HttpClient client = new DefaultHttpClient();

				HttpGet httpGet = new HttpGet(
						"http://192.168.56.1:8080/seekit/seekit/register?mail="
								+ mail + "&contrasenia=" + contrasena
								+ "&nombre=" + nombre + "&apellido=" + apellido);

				try {

					HttpResponse response = client.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					statusCode = statusLine.getStatusCode();
					StringBuilder builder = new StringBuilder();
					// Ahora ya lo registre, pero quiero que luego del registro
					// vaya al mainativity. Lo ideal es pasarle al main ya el
					// json con el usuario, por lo que una ves registrarlo debo
					// de obtenerlo.
					if (statusCode == 200) {

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

			handleResult(result);
		}

		private void handleResult(JSONObject result) {
			Log.d("el status cde es",statusCode+"");
			if (statusCode == 200) {
				Intent itn = new Intent(Registro.this, MainActivity.class);

				itn.putExtra("PARENT_NAME", "Registro");
				itn.putExtra("json", result.toString());
				Log.d("asd",result.toString());
				startActivity(itn);

			} else {
				if (statusCode == 0) {
					Toast.makeText(Registro.this,
							"Nuestros servidores estan caidos.",
							Toast.LENGTH_LONG).show();
				}
				Toast.makeText(Registro.this,
						"Por favor, seleccione otro mail.", Toast.LENGTH_LONG)
						.show();
			}

		}
	}

}
