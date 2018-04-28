package com.koti.apple.loginsqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button save_button;
    EditText name_et,password_et;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_button= (Button)findViewById(R.id.save_button);
        name_et = (EditText)findViewById(R.id.name_et);
        password_et = (EditText)findViewById(R.id.password_et);
        SaveLoginDatabase saveLoginDatabase = new SaveLoginDatabase(this);
        sqLiteDatabase = saveLoginDatabase.getWritableDatabase();
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validCredentials = new SaveLoginDatabase(getApplicationContext()).getUserInfo(sqLiteDatabase, name_et.getText().toString(), password_et.getText().toString());
                if (validCredentials.equalsIgnoreCase("nullnull")){
                    new SaveLoginDatabase(getApplicationContext()).addUser(sqLiteDatabase, name_et.getText().toString(), password_et.getText().toString());
                    Toast.makeText(MainActivity.this, "user data saved successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "user login exists ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
