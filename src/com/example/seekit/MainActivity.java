package com.example.seekit;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Obtengo el Json

		Log.d("seekit", "AAAAAAAAAAA");
		// new
		// ObtenerJson().obtenerJson("http://192.168.1.43:8080/Spring42/data/person");

		// fin obtencion Json

		setContentView(R.layout.listado);

		// aca estoy agregando a la lista, todos los parametros que van a venir
		// del ws
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

		datos.add(new Lista_entrada(R.drawable.im_buho, "BUHO", "en orejas."));
		datos.add(new Lista_entrada(R.drawable.im_colibri, "COLIBRÍ",
				"Los troqu40 especies."));
		datos.add(new Lista_entrada(R.drawable.im_cuervo, "CUERVO",
				"El cuervo comúas regiones."));

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
			Intent intent = new Intent(MainActivity.this, AddTri.class);
			startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
