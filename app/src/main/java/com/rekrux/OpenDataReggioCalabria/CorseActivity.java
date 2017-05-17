package com.rekrux.OpenDataReggioCalabria;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CorseActivity extends Activity implements View.OnClickListener {


    String item_idLinea, item_nomeLineaEsteso;
    TextView textView;
    String jsonResponse;
    public String urlJsonArry_corse = "http://dati.reggiocal.it/opendata/TrasportiAtam/corse.json";
    public ProgressDialog pDialog;
    RequestQueue requestQueue;

    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Sto cercando...");
        pDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);

        textView = (TextView)findViewById(R.id.linee_corse);
        button = (Button)findViewById(R.id.da_linea_a_corse);
        button.setOnClickListener(this);

        findViewById(R.id.da_linea_a_corse).setVisibility(View.VISIBLE);

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
            JsonArrayRequest req = new JsonArrayRequest(urlJsonArry_corse,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                jsonResponse = "";
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject json_corsa = (JSONObject) response.get(i);

                                    Intent p = getIntent();
                                    item_idLinea = p.getStringExtra("NOME_BREVE");
                                    item_nomeLineaEsteso = p.getStringExtra("NOME_ESTESO");

                                    if (json_corsa.getString("idLinea").equals(String.valueOf(item_idLinea))) {

                                        Intent intent = new Intent(CorseActivity.this, OrarioPalineActivity.class);
                                        intent.putExtra("IDCORSA", json_corsa.getString("idCorsa"));
                                        startActivity(intent);

                                        jsonResponse += json_corsa.getString("idCorsa") + " ";
                                    }

                                    textView.setText(jsonResponse);
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
}