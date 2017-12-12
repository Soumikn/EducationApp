package com.example.asus.educationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by asus on 12-Dec-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB";
    public static final String TABLE_IMAGE = "TABLEIMAGE";
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DB","------CREATION DU BD----------");
    String creation = "CREATE "+TABLE_IMAGE+" ( ID INTEGER PRIMARY KEY,IMAGE BLOB);"; //TABLE DIMAGE
        sqLiteDatabase.execSQL(creation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertImage(Bitmap bmp,int id){ // convert and insert bmp into db
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID",    id);
        cv.put("IMAGE",   getBytes(bmp));
        db.insert( TABLE_IMAGE, null, cv );
        db.close();
    }

    public Bitmap getImage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String rq = "SELECT IMAGE FROM ? WHERE 'ID = ?';";
        Cursor c = db.rawQuery(rq,new String[]{TABLE_IMAGE,Integer.toString(id)});
        if(c != null){
            c.moveToFirst();
        }
        Bitmap bmp = getImage(c.getBlob(0));
        return bmp;
    }

    public static byte[] getBytes(Bitmap bitmap) { // Convert BMP to byte Array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
