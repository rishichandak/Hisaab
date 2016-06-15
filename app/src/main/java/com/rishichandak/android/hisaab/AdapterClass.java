package com.rishichandak.android.hisaab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rishi on 09-06-2016.
 */
public class AdapterClass  {

    HelperClass helper;
    private Context context;
    public AdapterClass(Context context)
    {
        helper=new HelperClass(context);
        this.context=context;
    }
  /*  public long insertHeads(String value){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(helper.COL_HEAD,value);
        long id= 0;
        try {
            id = db.insert(helper.TABLE_HEADS,null,values);
            Toast.makeText(context,"Insert",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Insert Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        return id;
    }*/

    public long insertValues(String tableName, String[] columnName, String[] columnValues){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cValues=new ContentValues();
        for(int i=0;i<columnName.length;i++){
            cValues.put(columnName[i],columnValues[i]);
        }
        long id= 0;
        try {
            id = db.insert(tableName,null,cValues);
           // Toast.makeText(context,"Insert",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Insert Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        return id;
    }

    public ArrayList<String> getHeadsNames(ArrayList<String> arrayList) {
        SQLiteDatabase db=helper.getWritableDatabase();

        //select _id head from heads
        String[] columns={helper.COL_ID,helper.COL_HEAD};
        Cursor cursor=db.query(helper.TABLE_HEADS,columns,null,null,null,null,null);
        int idIndex=cursor.getColumnIndex(helper.COL_ID);
        int headIndex=cursor.getColumnIndex(helper.COL_HEAD);
        //StringBuffer data=new StringBuffer();

        arrayList.clear();
        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(headIndex));
            //int id= cursor.getInt(idIndex);
            //String head=cursor.getString(headIndex);
            //data.append(id+" "+head+"\n");
        }
        return arrayList;
    }

    public Cursor getAllData() {
        SQLiteDatabase db=helper.getWritableDatabase();
        //select _id head from heads
        String[] columns={helper.COL_ID,helper.COL_HEAD};
        Cursor cursor=db.query(helper.TABLE_HEADS,columns,null,null,null,null,null);
        return cursor;
    }


}
