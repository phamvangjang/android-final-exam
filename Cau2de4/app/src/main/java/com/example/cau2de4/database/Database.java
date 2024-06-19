package com.example.cau2de4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_product.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Product";
    public static final String COL_CODE = "ProductCode";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " + COL_CODE + " VARCHAR(50), " + COL_NAME + " VARCHAR(50), " + COL_PRICE + " REAL ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    //select
    public Cursor queryData(String sql){
        try{
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }catch (Exception e){
            Log.e("query data", "Error: " + e.getMessage());
            return null;
        }
    }

    //update, delete
    public boolean execSql (String sql){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            Log.e("execSql", "Error:" +e.getMessage());
            return false;
        }
    }

    public  int getNumbOfRows(){
        Cursor cursor = queryData( " SELECT * FROM " + TBL_NAME);
        int numbOfRows = cursor.getCount();
        cursor.close();
        return numbOfRows;
    }

    public void createSampleData(){
        if (getNumbOfRows()==0){
            try{
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-101', 'thuoc tru sau 1', 20000)");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-102', 'thuoc tru sau 2', 30000)");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-103', 'thuoc tru sau 3', 25000)");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-104', 'thuoc tru sau 4',  15000)");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-105', 'thuoc tru sau 5',  29000)");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('SP-106', 'thuoc tru sau 6', 21000)");
            }catch (Exception e){
                Log.e("createSampleData", "Error: " + e.getMessage());
            }
        }
    }
    //update data
    public boolean updateData(String code, String name, String price){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CODE, code);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PRICE, price);
        int updateStatus = db.update(TBL_NAME, contentValues, COL_CODE + " = ? ", new String[]{code});
        db.close();
        return updateStatus>0;
    }

    //delete data
    public boolean deleteData(String code){
        SQLiteDatabase db = this.getReadableDatabase();
        int delete = db.delete(TBL_NAME, COL_CODE + " = ? ", new String[]{code});
        db.close();
        return delete>0;
    }
}
