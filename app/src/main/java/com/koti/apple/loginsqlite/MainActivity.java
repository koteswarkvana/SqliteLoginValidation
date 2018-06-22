package com.koti.apple.loginsqlite;

import android.app.ActivityManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ///
    /** The proportional set size for dalvik. */
    public int dalvikPss;
    /** The private dirty pages used by dalvik. */
    public int dalvikPrivateDirty;
    /** The shared dirty pages used by dalvik. */
    public int dalvikSharedDirty;

    /** The proportional set size for the native heap. */
    public int nativePss;
    /** The private dirty pages used by the native heap. */
    public int nativePrivateDirty;
    /** The shared dirty pages used by the native heap. */
    public int nativeSharedDirty;

    /** The proportional set size for everything else. */
    public int otherPss;
    /** The private dirty pages used by everything else. */
    public int otherPrivateDirty;
    /** The shared dirty pages used by everything else. */
    public int otherSharedDirty;
    ///
    Button save_button;
    EditText name_et,password_et;
    private SQLiteDatabase sqLiteDatabase;

    public static String ARRAYVALUE = "ArrayValue";
    List<String> stringlist = new ArrayList<>();
//    String[] names=new String[10];
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_button= (Button)findViewById(R.id.save_button);
        name_et = (EditText)findViewById(R.id.name_et);
        password_et = (EditText)findViewById(R.id.password_et);
        SaveLoginDatabase saveLoginDatabase = new SaveLoginDatabase(this);
        sqLiteDatabase = saveLoginDatabase.getWritableDatabase();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        /*if (name_et.matches(emailPattern) && name_et.getText().toString().length() > 0) {

        }*/

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

        ///
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        Log.e("getMemoryClass >> ", "" + activityManager.getMemoryClass());
        Log.e("availMem free", "" + TotalInternalMemorySize(mi.availMem));
        Log.e("lowMemory >> ", "" + mi.lowMemory);
        Log.e("totalMem >> ", "" + TotalInternalMemorySize(mi.totalMem));
        Log.e("threshold >> ", "" + TotalInternalMemorySize(mi.threshold));
        Log.e("describeContents >> ", "" + mi.describeContents());

        Log.e("InternalMemorySize >> ", ""+TotalInternalMemorySize(Debug.getNativeHeapAllocatedSize()));
        Log.e("Runtime maxMemory() >> ", ""+TotalInternalMemorySize(Runtime.getRuntime().maxMemory()));
        long val =Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Log.e("virtual memory >> ", ""+TotalInternalMemorySize(val));
        Log.e("Runtime totalMemory >> ", ""+TotalInternalMemorySize(Runtime.getRuntime().totalMemory()));
        ///

        //E/availMem free: 577863680
        //06-18 13:27:01.363 2634-2634/? E/lowMemory >>: false
        //06-18 13:27:01.363 2634-2634/? E/totalMem >>: 1987235840
        //06-18 13:27:01.363 2634-2634/? E/describeContents >>: 0

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        mainIntent.addCategory(Intent.);
        final List pkgAppsList = getApplicationContext().getPackageManager().queryIntentActivities( mainIntent, 0);
        for (int i = 0; i < pkgAppsList.size(); i++) {
            Log.e("InternalMemorySize >> ", pkgAppsList.get(i).toString());
        }

        ///
        stringlist.add("hari");
        stringlist.add("suresh");
        stringlist.add("ramesh");
        stringlist.add("hari");
        for (int i = 0; i < stringlist.size(); i++) {
            name +=stringlist.get(i)+" ";
        }
        Log.e("name >> ", name);
        SharedpreferenceRootApplication.mEditor.putString(ARRAYVALUE, name).commit();
        startActivity(new Intent(this, ReceiveDataActivity.class));
        ///
    }

    ///
    public static String TotalInternalMemorySize(long r) {
        if (r <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(r) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(r / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    ///
}
