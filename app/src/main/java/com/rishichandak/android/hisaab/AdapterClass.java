package com.rishichandak.android.hisaab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by rishi on 09-06-2016.
 */
public class AdapterClass  {

    HelperClass helper;
    private Context context;
    public AdapterClass(Context context) {
        helper=new HelperClass(context);
        this.context=context;
    }

    public long insertValues(String tableName, String[] columnNames, String[] columnValues){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cValues=new ContentValues();
        for(int i=0;i<columnNames.length;i++){
            cValues.put(columnNames[i],columnValues[i]);
        }
        long id= 0;
        try {
            id = db.insert(tableName,null,cValues);
           // Toast.makeText(context,"Insert",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Insert Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
       // db.close();
        return id;
    }

    public Cursor getAllHeads() {
        SQLiteDatabase db=helper.getWritableDatabase();
        //select _id head from heads
        String[] columns={helper.COL_ID,helper.COL_HEAD};
        Cursor cursor=null;
        try {
            cursor=db.query(helper.TABLE_HEADS,columns,null,null,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getAllSubHead() Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
     //   db.close();
        return cursor;
    }

    public Cursor getAllSubHeads() {
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={helper.COL_ID,helper.COL_SUB_HEAD,helper.COL_PRICE};
        Cursor cursor=null;
        try {
            cursor=db.query(helper.TABLE_SUBHEADS,
                    columns,
                    helper.COL_UNDER_HEAD_ID + " =? " + " and " + helper.COL_SHOW + " =?",
                    new String[]{BasicActivity.selectedHeadNumber,"1"},
                    null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getAllHead() Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
       // db.close();
        return cursor;
    }

    public Cursor getAllSubHeadsInfo(String date,String headNumber) {
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=null;

        // select  subheads._id  ,  IFNULL(entries._id,-1) entryId,  subHead,  IFNULL(entries.quantity,0) quantity,  underHeadId , price  from subheads  left join entries on subheads._id=entries.subHeadId AND entries.date>=? where underHeadId =?

        final String query= "SELECT  "+helper.TABLE_SUBHEADS+"."+helper.COL_ID+"  ,  IFNULL("+helper.TABLE_ENTRIES+"."+helper.COL_ID+",-1) "+helper.COL_JOIN_ENTRY_ID+",  "+helper.COL_SUB_HEAD+",  IFNULL("+helper.TABLE_ENTRIES+"."+helper.COL_QUANTITY+",0) "+helper.COL_QUANTITY+",  "+helper.COL_UNDER_HEAD_ID+" , "+helper.COL_PRICE+
                            " FROM "+helper.TABLE_SUBHEADS+" " +
                            "LEFT JOIN "+
                            helper.TABLE_ENTRIES+
                            " ON "+
                            helper.TABLE_SUBHEADS+"."+helper.COL_ID+"="+helper.TABLE_ENTRIES+"."+helper.COL_SUB_HEAD_ID+" AND "+helper.TABLE_ENTRIES+"."+helper.COL_DATE+"= ? "+
                            "WHERE "+helper.COL_UNDER_HEAD_ID+" = ?";
        //New Table Structure:  _id | entryId | subHead | quantity | underHearId | price |
        //helper.COL_ID,  helper.COL_JOIN_ENTRY_ID,  helper.COL_SUB_HEAD,  helper.COL_QUANTITY,   COL_UNDER_HEAD_ID,   helper.COL_PRICE

        try {
  //          StringBuilder data=new StringBuilder();
            cursor=db.rawQuery(query,new String[]{date,headNumber});
/*
            while(cursor.moveToNext()){
                String id=cursor.getString(cursor.getColumnIndex("subheadId"));
                String rowId=cursor.getString(cursor.getColumnIndex("entryId"));
                String subhead=cursor.getString(cursor.getColumnIndex("subhead"));
                String quantity=cursor.getString(cursor.getColumnIndex("quantity"));
                String price=cursor.getString(cursor.getColumnIndex("price"));
                data.append(id+" "+rowId+" "+subhead+" "+price+" "+quantity+"\n");
            }
            Toast.makeText(context,data,Toast.LENGTH_LONG).show();*/
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Show Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        // db.close();
        return cursor;
    }
}
