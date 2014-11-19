package com.example.seekit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import contenedor.Tri;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lista;
	protected JSONObject resultFromWs;
	JSONObject json;
	int statusCode = -1;
	JSONArray jsonArray=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listado);
		

		
		// List<Tri> listaTris = ObtenerJson.obtenerTrisDeUsuario(idUsuario);
		// ObtenerJson.obtenerTrisDeUsuario(idUsuario);

		// obtenerTrisDeUsuario(idUsuario);
		// fin obtencion Json
		//
		String parentActivity = getIntent().getStringExtra("PARENT_NAME");
		
		if (parentActivity.equals("Login")) {
			try {

				Log.d("Actividad padre?", parentActivity);
				JSONObject obj = new JSONObject(getIntent().getStringExtra(
						"json"));
				json=obj;

				obj.getJSONObject("usuario").getString("nombre");
				JSONArray listaTris = obj.getJSONArray("listaTris");
				ArrayList<Tri> arrayListaTris = new ArrayList<Tri>();
				for (int i = 0; i < listaTris.length(); i++) {
					Tri triObj = new Tri(listaTris.getJSONObject(i).getString(
							"identificador"), listaTris.getJSONObject(i)
							.getString("nombre"), listaTris.getJSONObject(i)
							.getString("foto"), listaTris.getJSONObject(i)
							.getString("activo"), listaTris.getJSONObject(i)
							.getString("localizacion"), listaTris
							.getJSONObject(i).getString("perdido"), listaTris
							.getJSONObject(i).getString("compartido"));
					arrayListaTris.add(triObj);
					Log.d("i=", i + "");
					if (i == listaTris.length() - 1) {
						Log.d("Nest step=", "Reload Lista De Tris");
						reloadList(arrayListaTris);
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (parentActivity.equals("Registro")) {
			Log.d("MainActivity", "Parent=Registro");
			JSONObject obj;
			try {
				obj = new JSONObject(getIntent().getStringExtra(
						"json"));
				json=obj;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} else if (parentActivity.equals("addTri")) {
			Log.d("Estoy en el main, mi padre es el:","addTri");
			if (isNetworkAvailable()) {

				GetMainActivityTask getMainActivityTask = new GetMainActivityTask();
				getMainActivityTask.execute();
			} else {
				Toast.makeText(this, "Network is unavailable!",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	private class GetMainActivityTask extends
			AsyncTask<Object, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Object... params) {

			JSONObject jsonResponse = null;

			try {

				// ///////////7
				JSONObject obj = new JSONObject(getIntent().getStringExtra(
						"json"));
				json=obj;
				HttpClient client = new DefaultHttpClient();
				String id = obj.getJSONObject("usuario").getString("idUsuario");
				Log.d("viene bien1,5",
						"http://192.168.56.1:8080/seekit/seekit/getTris?idUsuario="
								+ id);

				HttpGet httpGet = new HttpGet(
						"http://192.168.56.1:8080/seekit/seekit/getTris?idUsuario="
								+ id);

				try {
					StringBuilder builder = new StringBuilder();
					HttpResponse response = client.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					statusCode = statusLine.getStatusCode();
					if (statusCode == 200) {
						Log.i("viene bien1,5", "Con el default client");
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(content));
						String line;

						while ((line = reader.readLine()) != null) {
							builder.append(line);
							try{
							
								jsonResponse = new JSONObject(line);
							}catch(JSONException ejson){
								Log.d("Exception in Main","Es un array no un objeto");
								jsonArray=new JSONArray(line);
							}

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
			// si anda bien, voy a pasar el objeto a la otra intent
			if (statusCode == 200) {
				try {
					
//					JSONObject obj = new JSONObject(getIntent().getStringExtra(
	//						"json"));
					ArrayList<Tri> arrayListaTris = new ArrayList<Tri>();
					JSONArray listaTris = null;
					if(jsonArray.equals(null)){
						JSONObject obj = result;

						obj.getJSONObject("usuario").getString("nombre");
						 listaTris = obj.getJSONArray("listaTris");
						
					}else{
						 listaTris=jsonArray;
						 
						
					}
					
					
					
					for (int i = 0; i < listaTris.length(); i++) {
						Tri triObj = new Tri(listaTris.getJSONObject(i).getString(
								"identificador"), listaTris.getJSONObject(i)
								.getString("nombre"), listaTris.getJSONObject(i)
								.getString("foto"), listaTris.getJSONObject(i)
								.getString("activo"), listaTris.getJSONObject(i)
								.getString("localizacion"), listaTris
								.getJSONObject(i).getString("perdido"), listaTris
								.getJSONObject(i).getString("compartido"));
						arrayListaTris.add(triObj);
						Log.d("i=", i + "");
						if (i == listaTris.length() - 1) {
							Log.d("Nest step=", "Reload Lista De Tris");
							reloadList(arrayListaTris);
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else if (statusCode == 0) {
				
			} else {
				
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

	public void reloadList(List<Tri> listaTris) {
		// aca estoy agregando a la lista, todos los parametros que van a venir
		// del ws
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
		for (int i = 0; i < listaTris.size(); i++) {
			datos.add(new Lista_entrada(R.drawable.im_buho, listaTris.get(i)
					.getNombre(), listaTris.get(i).getIdentificador()));
		}

		Log.d("la urlll", "DELMAINACTIVYTY");
		lista = (ListView) findViewById(R.id.ListView_listado);
		lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos) {
			@Override
			// con el onEntrada voy a insertar todos los textos
			// corerespondientes a cada entrada.
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView) view
							.findViewById(R.id.textView_superior);
					if (texto_superior_entrada != null)
						texto_superior_entrada
								.setText(((Lista_entrada) entrada)
										.get_textoEncima());

					final TextView texto_inferior_entrada = (TextView) view
							.findViewById(R.id.textView_inferior);
					if (texto_inferior_entrada != null)
						texto_inferior_entrada
								.setText(((Lista_entrada) entrada)
										.get_textoDebajo());

					ImageView imagen_entrada = (ImageView) view
							.findViewById(R.id.imageView_imagen);
					if (imagen_entrada != null) {
						imagen_entrada
								.setImageResource(((Lista_entrada) entrada)
										.get_idImagen());

						// hago que la imagen sea clickeable
						imagen_entrada
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View view) {
										// al hacer click ira a la pantalla de
										// rastreo
										Intent intent = new Intent(
												MainActivity.this,
												PantallaRastreo.class);
										String Auxiliar = texto_inferior_entrada
												.getText().toString();
										startActivity(intent);
										// Toast toast =
										// Toast.makeText(MainActivity.this,
										// Auxiliar,
										// Toast.LENGTH_LONG);
										// toast.show();
									}

								});
						// fin
					}

				}

			}
		});

		// esto es para que toda la lisa sea clicckeable, pero no queremos esto,
		// lo de por si acazooooo... acazoooooo
		/*
		 * lista.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> pariente, View view,
		 * int posicion, long id) { Log.d("Myactivity", "asdasd"); Lista_entrada
		 * elegido = (Lista_entrada) pariente .getItemAtPosition(posicion);
		 * 
		 * CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
		 * Toast toast = Toast.makeText(MainActivity.this, texto,
		 * Toast.LENGTH_LONG); toast.show(); } });
		 */

	}

	/*public void obtenerTrisDeUsuario(String idUsuario) {
		final List<Tri> listaTris = new ArrayList<Tri>();
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://192.168.56.1:8080/seekit/seekit/getTris?idUsuario="
				+ idUsuario;
		Log.d("la urlll", url);
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray arrayTris) {

				// Pull out the first event on the public timeline
				try {

					for (int i = 0; i < arrayTris.length(); i++) {

						JSONObject tri = (JSONObject) arrayTris.get(i);
						Tri triAux = new Tri(tri.getString("identificador"),
								tri.getString("nombre"), tri.getString("foto"),
								tri.getString("activo"), tri
										.getString("localizacion"), tri
										.getString("perdido"), tri
										.getString("compartido"));
						Log.d("la urlll", triAux.getNombre());
						listaTris.add(triAux);

					}

					reloadList(listaTris);
				} catch (JSONException e) {
					Log.e("MyActivity", "JSONException encontrada", e);
				}

			}

		});
		// return listaTris;
	}*/

	// Infla todo lo que este en la action Bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		// MenuInflater inflater = getMenuInflater();
		// getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu; this
	 * adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */

	// When the user presses one of the action buttons or another item in the
	// action overflow, the system calls your activity's onOptionsItemSelected()
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new) {
			Log.d("mainActrivity","Agregar tri");
			Intent intent = new Intent(MainActivity.this, AddTri.class);
			try{
				intent.putExtra("json", json.toString());	
				Log.d("mainActrivity","Agregar tri "+json.toString());
			}catch(Exception e){
				Log.d("ACA NUNCA DEBO ESTAR","ACA NUNCA DEBO ESTAR");
			}
			
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
