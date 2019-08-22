package com.koti.apple.loginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class PurchaseQuantitySaveDatabase extends SQLiteOpenHelper {
    //database helper
    public static final String DATABASE_NAME_ = "users.db";
    public static final int DATABASE_VERSION = 1;

    //User table
    public static final String TABLE_USERS = "users";

    //users table rows
    public static final String PURCHASE_COLUMN_ID = "ID";
    public static final String PURCHASE_ITEM_POSITION = "itemPosition";
    public static final String PURCHASE_ITEM_QUANTITY = "itemQuantity";

    //quarries
    //create
    public static final String CREATE_TABLES_USERS = "create table " + TABLE_USERS +
            "(" +
            PURCHASE_COLUMN_ID + " integer primary key," +
            PURCHASE_ITEM_POSITION + " text UNIQUE," +
            PURCHASE_ITEM_QUANTITY + " text)";

    //drop
    public static final String DROP_TABLES_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    //select rows
    public static final String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_USERS;

    //array of columns
    private static String[] USER_COLUMNS = {PURCHASE_COLUMN_ID, PURCHASE_ITEM_POSITION, PURCHASE_ITEM_QUANTITY};

    PurchaseQuantitySaveDatabase purchaseQuantitySaveDatabase;
    private Context context;

    public PurchaseQuantitySaveDatabase getInstance() {
        if (purchaseQuantitySaveDatabase == null) {
            purchaseQuantitySaveDatabase = new PurchaseQuantitySaveDatabase(context);
        }
        return purchaseQuantitySaveDatabase;
    }

    public PurchaseQuantitySaveDatabase(Context context) {
        super(context, DATABASE_NAME_, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES_USERS);
    }

    public void deleteEntaireTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete old tables
        db.execSQL(DROP_TABLES_USERS);
        //creating tables again
        onCreate(db);
    }

    public void insertPurchaseInfo(String position, String quantity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PURCHASE_ITEM_POSITION, position);
        contentValues.put(PURCHASE_ITEM_QUANTITY, quantity);
        //insert
        sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        Log.e("info >> ", "addUser : inserted ");
    }

    public int getPurchaseInfo(String position) {
        try {
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT itemQuantity FROM users WHERE itemPosition = ?", new String[]{position});
            String quantityVal = null;
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        quantityVal = cursor.getString(cursor.getColumnIndex("itemQuantity"));
                        Log.e("info >> ", "itemQuantity  :  " + quantityVal);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
            if (quantityVal == null){
                quantityVal="0";
            }
            return Integer.valueOf(quantityVal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor getPurchaseTotalInfo() {
        Cursor cursor=null;
        try {
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            cursor= sqLiteDatabase.rawQuery("SELECT itemQuantity FROM users", new String[]{});
            return cursor;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public void deleteRow(String position){
        try {
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("DELETE FROM users WHERE itemPosition = ?", new String[]{position});
            String quantityVal = null;
            if ((cursor != null && cursor.getCount()>0)) {
                while (cursor.moveToFirst()) {
                    quantityVal = cursor.getString(cursor.getColumnIndex("itemQuantity"));
                    Log.e("info >> ", "itemQuantity  :  " + quantityVal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuantityPosition(String position, String quatity){
        try {
            SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("UPDATE users SET itemQuantity=? WHERE itemPosition = ?", new String[]{quatity, position});
            String quantityVal = null;
            if ((cursor != null && cursor.getCount()>0)) {
                while (cursor.moveToFirst()) {
                    quantityVal = cursor.getString(cursor.getColumnIndex("itemQuantity"));
                    Log.e("info >> ", "itemQuantity  :  " + quantityVal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*

    in constructor we need to call deleteEntaireTable() method.

    onBindvie() method {
            holder.btAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: >>   " + position);
            holder.btAdd.setVisibility(View.GONE);
            holder.llAddOrRemove.setVisibility(View.VISIBLE);
            purchaseQuantitySaveDatabase.insertPurchaseInfo(String.valueOf(position), String.valueOf(intialCount));
            sendMessage(totalCount(purchaseQuantitySaveDatabase.getPurchaseTotalInfo()));
            addOrDeleteItem(holder, position);
        }
    });
}

    int intialCount = 1;
    int count = 1;

    private void addOrDeleteItem(ViewHolder holder, int position) {

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = purchaseQuantitySaveDatabase.getPurchaseInfo(String.valueOf(position));
                count++;
                holder.tvIncreseOrDecreseItems.setText("" + count);
                purchaseQuantitySaveDatabase.updateQuantityPosition(String.valueOf(position), String.valueOf(count));
                sendMessage(totalCount(purchaseQuantitySaveDatabase.getPurchaseTotalInfo()));
            }
        });

        holder.ivSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = purchaseQuantitySaveDatabase.getPurchaseInfo(String.valueOf(position));
                if (count == 1) {
                    holder.btPurchaseAdd.setVisibility(View.VISIBLE);
                    holder.llAddOrRemove.setVisibility(View.GONE);
                    purchaseQuantitySaveDatabase.deleteRow(String.valueOf(position));
                } else {
                    count--;
                    holder.tvIncreseOrDecreseItems.setText("" + count);
                }
                purchaseQuantitySaveDatabase.updateQuantityPosition(String.valueOf(position), String.valueOf(count));
                sendMessage(totalCount(purchaseQuantitySaveDatabase.getPurchaseTotalInfo()));
            }
        });
    }

    private int totalCount(Cursor cursor) {
        int total = 0;
        String quantityVal;
        if (cursor.moveToFirst()) {
            do {
                quantityVal = cursor.getString(cursor.getColumnIndex("itemQuantity"));
                total += Integer.valueOf(quantityVal);
                Log.e("info >> ", "itemQuantity  :  " + total);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return total;
    }
*/
}