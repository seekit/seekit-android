package com.example.seekit;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.util.Log;



public class ObtenerJson {
	

     void obtenerJson(String url) {
        
        AsyncHttpClient client = new AsyncHttpClient();
        /*client.get(url, new RequestParams("id", "15"), new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("MyActivity", "Codigo: " + statusCode + " - Contenido: " + new String(responseBody));
                Log.d("MyActivity", "Codigo: " + statusCode + " - LALALA: " + new String(responseBody).toString().toUpperCase());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("MyActivity", "onFailure, codigo: " + statusCode, error);
            }
        });*/
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
            	Log.d("MyActivity","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA1");
            	try {
                    
                        String person = response.getString("name");
                        Log.d("MyActivity", person);
                    
                } catch (JSONException e) {
                    Log.e("MyActivity", "JSONException encontrada", e);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arrayPersonas) {
                // Pull out the first event on the public timeline
                Log.d("MyActivity","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA2");
                try {
                    for (int i = 0; i < arrayPersonas.length(); i++) {
                        JSONObject person = (JSONObject) arrayPersonas.get(i);
                        Log.d("MyActivity", person.getString("name"));
                    }
                } catch (JSONException e) {
                    Log.e("MyActivity", "JSONException encontrada", e);
                }
            }
        });

    }
}
