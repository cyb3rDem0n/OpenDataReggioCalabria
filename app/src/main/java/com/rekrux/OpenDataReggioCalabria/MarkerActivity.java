package com.rekrux.OpenDataReggioCalabria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rekrux.mysql_test_volley.R;

/**
 * Created by rekrux on 14/08/2016.
 */
public class MarkerActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markers);

    Intent intent = getIntent();
        String marker_title = intent.getStringExtra("INFOMARKER");

        TextView title = (TextView)findViewById(R.id.marker_title);
        title.setText(marker_title);

    }

    public void go_back_3(View v){
        Intent intent = new Intent(MarkerActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
