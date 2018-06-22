package com.koti.apple.loginsqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDataActivity extends AppCompatActivity {

    private List<String> listVal = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tvNames = findViewById(R.id.tv_names);
        String val = SharedpreferenceRootApplication.mPref.getString(MainActivity.ARRAYVALUE, "");
        Log.e("info >> ", "onCreate: "+ val );
        String[] namesList = val.split(" ");
        if (namesList.length != 0) {
            for (int i = 0; i < namesList.length; i++) {
                listVal.add(namesList[i]);
            }
            tvNames.setText(val.toString());
        }
    }

}
