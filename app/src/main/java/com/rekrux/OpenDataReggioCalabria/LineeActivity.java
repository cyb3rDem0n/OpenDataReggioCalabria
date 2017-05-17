package com.rekrux.OpenDataReggioCalabria;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.HashMap;

public class LineeActivity extends AppCompatActivity {
    String url = "http://dati.reggiocal.it/opendata/TrasportiAtam/linee.json";
    ArrayList<HashMap<String, String>> Item_List;
    ListAdapter adapter;
    RequestQueue requestQueue;

    ListView listview = null;

    private static final String ITEM_NOMEBREVE = "lineaNomeBreve";
    private static final String ITEM_NOMEESTESO = "lineaNomeEsteso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.read_linee);
        requestQueue = Volley.newRequestQueue(this);
        listview = (ListView) findViewById(R.id.listview_linee);
        Item_List = new ArrayList<>();

        JsonArrayRequest arrayreq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        HashMap<String, String> item = new HashMap<>();

                        item.put(ITEM_NOMEBREVE, jsonObject.getString(ITEM_NOMEBREVE));
                        item.put(ITEM_NOMEESTESO, jsonObject.getString(ITEM_NOMEESTESO));
                        Item_List.add(item);

                        String[] from = {ITEM_NOMEBREVE, ITEM_NOMEESTESO};
                        int[] to = {R.id.nome_breve, R.id.nome_esteso};

                        adapter = new SimpleAdapter(
                                getApplicationContext(), Item_List,
                                R.layout.list_item_linee, from, to);

                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(new ListitemClickListener());


                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(arrayreq);
    }
    class ListitemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            //to_corse
            Intent modify_intent = new Intent(LineeActivity.this, CorseActivity.class);
            modify_intent.putExtra("NOME_BREVE", Item_List.get(position).get(ITEM_NOMEBREVE));
            modify_intent.putExtra("NOME_ESTESO", Item_List.get(position).get(ITEM_NOMEESTESO));
            startActivity(modify_intent);


        }


    }
}
