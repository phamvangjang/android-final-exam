package com.example.cau2de3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_student.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Student";
    public static final String COL_NAME = "StudentName";
    public static final String COL_CLASS = "StudentClass";
    public static final String COL_CODE = "StudentCode";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " + COL_NAME + " VARCHAR(50), " + COL_CLASS + " VARCHAR(50), " + COL_CODE + " VARCHAR(50) ) ";
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
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('nguyen van a', 'cong nghe thong tin', '3051071025')");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('nguyen van b', 'cong nghe thong tin', '3051071026')");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('nguyen van c', 'cong nghe thong tin', '3051071027')");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('nguyen van d', 'cong nghe phan mem',  '3061071028')");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('nguyen thi e', 'cong nghe phan mem',  '3061071029')");
                execSql(" INSERT INTO " + TBL_NAME + " VALUES('tran my f', 'cong nghe phan mem', '3061071022')");
            }catch (Exception e){
                Log.e("createSampleData", "Error: " + e.getMessage());
            }
        }
    }
    //update data
    public boolean updateData(String name, String Class, String code){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_CLASS, Class);
        contentValues.put(COL_CODE, code);
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
