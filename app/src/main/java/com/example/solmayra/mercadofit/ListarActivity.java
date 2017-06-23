package com.example.solmayra.mercadofit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John Fontalvo Perez on 12/06/2017.
 */

public class ListarActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> mercadoList;

    private ListView lvlistar;
    String url = "http://www.mercadouac.ml/mercado/mercado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        mercadoList = new ArrayList<>();

        lvlistar = (ListView) findViewById(R.id.listListar);

        new GetMercados().execute();

    }

    private class GetMercados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ListarActivity.this);
            pDialog.setMessage("Espere...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Respuesta de la url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray mercados = jsonObj.getJSONArray("data");


                    for (int i = 0; i < mercados.length(); i++) {
                        JSONObject c = mercados.getJSONObject(i);

                        String id = c.getString("id");
                        String fecha = c.getString("fecha");
                        String nombre = c.getString("nombre");
                        String marca = c.getString("marca");
                        String cantidad = c.getString("cantidad");
                        String almacen = c.getString("almacen");



                        HashMap<String, String> mercado = new HashMap<>();

                        // adding each child node to HashMap key => value
                        mercado.put("id", id);
                        mercado.put("fecha", fecha);
                        mercado.put("nombre", nombre);
                        mercado.put("marca", marca);
                        mercado.put("cantidad", cantidad);
                        mercado.put("almacen", almacen);


                        mercadoList.add(mercado);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json error de parseo: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json error de parseo: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "No se pudo recibir el json del servidor.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "No se pudo recibir json del servidor.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ListarActivity.this, mercadoList,
                    R.layout.list_item, new String[]{"nombre", "fecha",
                    "marca", "cantidad", "almacen"}, new int[]{R.id.nombre,
                    R.id.fecha, R.id.marca, R.id.cantidad, R.id.almacen});

            lvlistar.setAdapter(adapter);
        }

    }



}
