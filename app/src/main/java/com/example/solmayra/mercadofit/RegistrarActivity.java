package com.example.solmayra.mercadofit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sol Mayra on 03/06/2017.
 */

public class RegistrarActivity extends AppCompatActivity {
    EditText editfecha, editnombre, editmarca, editcantidad, editalmacen;
    Button btnregistar;
    Mercado m;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        editfecha = (EditText) findViewById(R.id.editfecha);
        editnombre = (EditText) findViewById(R.id.editnombre);
        editmarca = (EditText) findViewById(R.id.editMarca);
        editcantidad = (EditText) findViewById(R.id.editcant);
        editalmacen = (EditText) findViewById(R.id.editalmacen);
        btnregistar = (Button) findViewById(R.id.btnregistrar);

        btnregistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncT().execute();
            }
        });
    }

    private class AsyncT extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL("http://www.mercadouac.ml/mercado/mercado"); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(crearJson().toString());
                wr.flush();
                wr.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    public JSONObject crearJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("fecha", editfecha.getText());
        json.put("nombre", editnombre.getText());
        json.put("marca", editmarca.getText());
        json.put("cantidad", editcantidad.getText());
        json.put("almacen", editalmacen.getText());
        return json;
    }

}
