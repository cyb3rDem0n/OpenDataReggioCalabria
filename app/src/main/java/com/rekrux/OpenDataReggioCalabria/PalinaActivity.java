package com.rekrux.OpenDataReggioCalabria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rekrux.mysql_test_volley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PalinaActivity extends AppCompatActivity implements View.OnClickListener {

    String url = "http://dati.reggiocal.it/opendata/TrasportiAtam/paline.json";
    String jsonResponse;
    TextView txtResponse;
    String PALINA_PARTENZA;
    String PALINA_ARRIVO;
    String PALINA_INTERMEDIA;

    Button button;
    public ProgressDialog pDialog;
    RequestQueue requestQueue;
    ArrayList<Double> arrayListLat;
    ArrayList<Double> arrayListLon;
    ArrayList<String> arrayListName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palina);
        pDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Sto caricando la mappa...");
        pDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);

        txtResponse = (TextView)findViewById(R.id.coordinate_txt);
        button = (Button)findViewById(R.id.btn_coordinate);
        button.setOnClickListener(this);

        arrayListLat = new ArrayList<>();
        arrayListLon = new ArrayList<>();
        arrayListName = new ArrayList<>();

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onClick(View v){
        showpDialog();
        final JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject json = (JSONObject) response.get(i);

                                Intent myIntent = getIntent();
                                PALINA_PARTENZA = myIntent.getStringArrayListExtra("PALINE").get(0);
                                PALINA_INTERMEDIA = myIntent.getStringArrayListExtra("PALINE").get(myIntent.getStringArrayListExtra("PALINE").size() /2);
                                PALINA_ARRIVO = myIntent.getStringArrayListExtra("PALINE").get(myIntent.getStringArrayListExtra("PALINE").size() -1);

                                if (json.getString("codicePalina").equals(String.valueOf(PALINA_ARRIVO)) ||
                                        json.getString("codicePalina").equals(String.valueOf(PALINA_INTERMEDIA))||
                                          json.getString("codicePalina").equals(String.valueOf(PALINA_PARTENZA)) ) {

                                    arrayListLat.add(json.getDouble("lat"));
                                    arrayListLon.add(json.getDouble("lon"));
                                    arrayListName.add(json.getString("nomePaline"));

                                    Intent intent = new Intent(PalinaActivity.this, PopolatedMap.class);
                                    intent.putExtra("LAT_LIST",arrayListLat);
                                    intent.putExtra("LON_LIST", arrayListLon);
                                    intent.putStringArrayListExtra("NOME_LIST", arrayListName);
                                    startActivity(intent);


                                    jsonResponse += "Latitudine:       " + json.getDouble("lat") + "\n\n" +
                                            "Longitudine:    " + json.getDouble("lon") + "\n\n" +
                                            "Fermata:          " + json.getString("nomePaline")+ "\n\n" +
                                            "Codice P.:          " + json.getString("codicePalina") +
                                            "\n\n\n";
                                }
                                txtResponse.setText(jsonResponse);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });
        MyApplication.getInstance().addToReqQueue(req);
    }
    public void OnClicMap(View v){
        Intent intent = new Intent(PalinaActivity.this, PopolatedMap.class);
        startActivity(intent);
    }
    public void go_back_2(View v){
        Intent intent = new Intent(PalinaActivity.this, MainActivity.class);
        startActivity(intent);
    }
}