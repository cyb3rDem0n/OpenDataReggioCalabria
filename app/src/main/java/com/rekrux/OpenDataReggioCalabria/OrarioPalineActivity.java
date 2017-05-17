package com.rekrux.OpenDataReggioCalabria;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rekrux.mysql_test_volley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrarioPalineActivity extends AppCompatActivity {
    String url = "http://testingwordpress.altervista.org/read_allorder.php";
    ProgressDialog PD;
    ArrayList<String> arrayList;

    // JSON Node names
    public static final String ITEM_IDCORSA = "idCorsa";
    public static final String ITEM_CODICEPALINA = "codicePalina";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PD = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        PD.setMessage("Sto cercando...");
        PD.setCancelable(false);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        ReadDataFromDB(String.valueOf(intent.getStringExtra("IDCORSA")));
        }

    private void showpDialog() {
        if (!PD.isShowing())
            PD.show();
    }
    private void hidepDialog() {
        if (PD.isShowing())
            PD.dismiss();
    }

    private void ReadDataFromDB(final String x) {
        showpDialog();
        PD.setMessage("Un attimo.....");
        PD.show();

        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");

                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("orders");

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jobj = ja.getJSONObject(i);

                                    if (x.equals(jobj.getString(ITEM_IDCORSA))) {
                                        arrayList.add(jobj.getString(ITEM_CODICEPALINA));


                                        Intent intent = new Intent(getBaseContext(), PalinaActivity.class);
                                        intent.putStringArrayListExtra("PALINE", arrayList);
                                        startActivity(intent);

                                    } // for loop ends
                                    PD.dismiss();
                                }
                            } // if ends

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
                PD.dismiss();
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jreq);

    }
}