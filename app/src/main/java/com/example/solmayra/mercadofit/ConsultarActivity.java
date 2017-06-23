package com.example.solmayra.mercadofit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sol Mayra on 03/06/2017.
 */

public class ConsultarActivity extends AppCompatActivity {

    RadioGroup rb;
    RadioButton rbmarca, rbalmacen;
    Button btnconsultar;
    ListView listConsulta;
    EditText editmaral;
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> mercadoList;
    String url = "http://www.mercadouac.ml/mercado/mercado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        mercadoList = new ArrayList<>();
        editmaral = (EditText) findViewById(R.id.editmaral);
        listConsulta = (ListView) findViewById(R.id.listConsulta);
        rb = (RadioGroup) findViewById(R.id.rbconsulta);
        rbmarca = (RadioButton) findViewById(R.id.rbmarca);
        rbalmacen = (RadioButton) findViewById(R.id.rbalmacen);
        btnconsultar = (Button) findViewById(R.id.btnconsultar);

        btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mercadoList.clear();
                new GetMercados().execute();
            }
        });
    }

    private class GetMercados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ConsultarActivity.this);
            pDialog.setMessage("Espere...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

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

                        if(mercado.containsKey(buscarKey()) && mercado.containsValue(buscarValue())){
                            mercadoList.add(mercado);
                        }
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

        public String buscarKey()
        {
            String maral = "";

            if(rbmarca.isChecked())
            {
                maral = "marca";
            }
            else
            if(rbalmacen.isChecked())
            {
                maral = "almacen";
            }

            return maral;
        }

        public String buscarValue()
        {
            String maral = "";

            if(rbmarca.isChecked())
            {
                maral = editmaral.getText().toString();
            }
            else
            if(rbalmacen.isChecked())
            {
                maral = editmaral.getText().toString();
            }

            return maral;
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
                    ConsultarActivity.this, mercadoList,
                    R.layout.list_item, new String[]{"nombre", "fecha",
                    "marca", "cantidad", "almacen"}, new int[]{R.id.nombre,
                    R.id.fecha, R.id.marca, R.id.cantidad, R.id.almacen});

            listConsulta.setAdapter(adapter);
        }

    }
}
