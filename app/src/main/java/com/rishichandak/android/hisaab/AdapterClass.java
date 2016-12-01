package com.rishichandak.android.hisaab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.rishichandak.android.hisaab.HelperClass;

import static com.rishichandak.android.hisaab.HelperClass.COL_DATE;
import static com.rishichandak.android.hisaab.HelperClass.COL_HEAD;
import static com.rishichandak.android.hisaab.HelperClass.COL_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_JOIN_ENTRY_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_JOIN_TOTAL;
import static com.rishichandak.android.hisaab.HelperClass.COL_PRICE;
import static com.rishichandak.android.hisaab.HelperClass.COL_QUANTITY;
import static com.rishichandak.android.hisaab.HelperClass.COL_SHOW;
import static com.rishichandak.android.hisaab.HelperClass.COL_SUB_HEAD;
import static com.rishichandak.android.hisaab.HelperClass.COL_SUB_HEAD_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_UNDER_HEAD_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_UNDER_SUB_HEAD_ID;
import static com.rishichandak.android.hisaab.HelperClass.COL_WEFDATE;
import static com.rishichandak.android.hisaab.HelperClass.TABLE_ENTRIES;
import static com.rishichandak.android.hisaab.HelperClass.TABLE_HEADS;
import static com.rishichandak.android.hisaab.HelperClass.TABLE_RATES;
import static com.rishichandak.android.hisaab.HelperClass.TABLE_SUBHEADS;

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
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Insert Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
       // db.close();
        return id;
    }

    public long updateValues(String tableName, String[] columnNames, String[] columnValues, String where, String[] whereArgs){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues cValues=new ContentValues();
        long rowsEffected=0;
        for(int i=0;i<columnNames.length;i++){
            cValues.put(columnNames[i],columnValues[i]);
        }
        try {
            rowsEffected=db.update(tableName,cValues,where,whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Update Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        return rowsEffected;
    }

    public Cursor getAllHeads() {
        SQLiteDatabase db=helper.getWritableDatabase();
        //select _id head from heads
        String[] columns={COL_ID,COL_HEAD};
        Cursor cursor=null;
        try {
            cursor=db.query(TABLE_HEADS,columns,null,null,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getAllSubHead() Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
     //   db.close();
        return cursor;
    }

    /*public Cursor getAllSubHeads() {
        SQLiteDatabase db=helper.getWritableDatabase();
        String[] columns={COL_ID,COL_SUB_HEAD,COL_PRICE};
        Cursor cursor=null;
        try {
            cursor=db.query(TABLE_SUBHEADS,
                    columns,
                    COL_UNDER_HEAD_ID + " =? " + " and " + COL_SHOW + " =?",
                    new String[]{BasicActivity.selectedHeadNumber,"1"},
                    null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getAllHead() Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
       // db.close();
        return cursor;
    }*/

    public Cursor getAllSubHeadsInfo(String date,String headNumber) {
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=null;

        // select  subheads._id  ,  IFNULL(entries._id,-1) entryId,  subHead,  IFNULL(entries.quantity,0) quantity,  underHeadId , price  from subheads  left join entries on subheads._id=entries.subHeadId AND entries.date>=? where underHeadId =?

        final String query= "SELECT  "
                + TABLE_SUBHEADS+"."+ COL_ID+
                ",IFNULL("+TABLE_ENTRIES+"."+ COL_ID+",-1) "+COL_JOIN_ENTRY_ID+
                ","+ COL_SUB_HEAD+
                ",IFNULL("+ TABLE_ENTRIES+"."+COL_QUANTITY+",0) "+ COL_QUANTITY+
                ","+ COL_UNDER_HEAD_ID+
                ","+ COL_PRICE+
                            " FROM "+ TABLE_SUBHEADS+" " +
                            "LEFT JOIN "+
                            TABLE_ENTRIES+
                            " ON "+
                            TABLE_SUBHEADS+"."+ COL_ID+"="+ TABLE_ENTRIES+"."+COL_SUB_HEAD_ID+" AND "+ TABLE_ENTRIES+"."+COL_DATE+"= ? "+
                            "WHERE "+ COL_UNDER_HEAD_ID+" = ?";


        /* select subHead._id,subHead.subHead, IFNULL(entries._id,-1) EntryId , ifnull(entries.quantity,0) Qty, subHead.underHeadId, newRates.price
	from subHead
		left join entries
			ON subHead._id=entries.subHeadId and entries.date = '28'
		join (select t.underSubHeadId,t.wefDate,t.price from rates t where t.wefDate = (select max(t2.wefDate) from rates t2 where t2.underSubHeadId = t.underSubHeadId and t2.wefdate <= '28')) as newRates
			ON subHead._id=newRates.underSubheadId
	where  subHead.underHeadId=1 order by subHead._id
         */
        final String query2= "SELECT  "
                        + TABLE_SUBHEADS+"."+ COL_ID+ " "+ COL_ID+
                        ",IFNULL("+TABLE_ENTRIES+"."+ COL_ID+",-1) "+COL_JOIN_ENTRY_ID+
                        ","+ COL_SUB_HEAD+
                        ",IFNULL("+ TABLE_ENTRIES+"."+COL_QUANTITY+",0) "+ COL_QUANTITY+
                        ","+ COL_UNDER_HEAD_ID+
                        ",TABLE_NEW_RATES."+ COL_PRICE +" "+COL_PRICE+
                        " FROM "+ TABLE_SUBHEADS+" " +
                            " LEFT JOIN "+
                                TABLE_ENTRIES+
                            " ON "+
                            TABLE_SUBHEADS+"."+ COL_ID+"="+ TABLE_ENTRIES+"."+COL_SUB_HEAD_ID+" AND "+ TABLE_ENTRIES+"."+COL_DATE+" = ? "+
                            " JOIN "+
                                    "(SELECT T."+COL_UNDER_SUB_HEAD_ID+" , "+
                                        " T."+COL_WEFDATE+" , "+
                                        " T."+COL_PRICE+
                                    " FROM "+TABLE_RATES+" T "+
                                    " WHERE T."+COL_WEFDATE+" = "+
                                        " (SELECT MAX(T2."+COL_WEFDATE+") "+
                                        " FROM "+TABLE_RATES+" T2 "+
                                        " WHERE T2."+COL_UNDER_SUB_HEAD_ID+" = "+" T."+COL_UNDER_SUB_HEAD_ID+" AND "+"T2."+COL_WEFDATE+" <= ?)) TABLE_NEW_RATES"+
                            " ON "+TABLE_SUBHEADS+"."+COL_ID+" = "+"TABLE_NEW_RATES."+COL_UNDER_SUB_HEAD_ID+
                        " WHERE "+ COL_UNDER_HEAD_ID+" = ?" ;

        //New Table Structure:  _id | entryId | subHead | quantity | underHearId | price |
        //helper.COL_ID,  helper.COL_JOIN_ENTRY_ID,  helper.COL_SUB_HEAD,  helper.COL_QUANTITY,   COL_UNDER_HEAD_ID,   helper.COL_PRICE

        try {
     //       StringBuilder data=new StringBuilder();
            cursor=db.rawQuery(query2,new String[]{date,date,headNumber});

       /*     while(cursor.moveToNext()){
                String id=cursor.getString(cursor.getColumnIndex("subheadId"));
                String rowId=cursor.getString(cursor.getColumnIndex("entryId"));
                String subhead=cursor.getString(cursor.getColumnIndex("subhead"));
                String quantity=cursor.getString(cursor.getColumnIndex("quantity"));
                String price=cursor.getString(cursor.getColumnIndex("price"));
                data.append(id+" "+rowId+" "+subhead+" "+price+" "+quantity+"\n");
            }
            Toast.makeText(context,data,Toast.LENGTH_LONG).show();  */
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getAllSubHeadsInfo Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        // db.close();
        return cursor;
    }

    public Cursor getMonthlyTotal(String month,String year,String subHead){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=null;
        final String query=
                "select IFNULL( SUM("+ COL_PRICE+"*"+ COL_QUANTITY+"),0) "+COL_JOIN_TOTAL+" , "+ TABLE_SUBHEADS+"."+ COL_UNDER_HEAD_ID+
                        " from " +
                        TABLE_SUBHEADS +
                        " join " +
                        TABLE_ENTRIES +
                        " on " +
                        TABLE_SUBHEADS+"."+ COL_ID+"="+ TABLE_ENTRIES+"."+ COL_SUB_HEAD_ID+
                        " where "+ TABLE_SUBHEADS+"."+ COL_UNDER_HEAD_ID+"=? "+
                        " and " +
                        " strftime('%Y',"+ TABLE_ENTRIES+"."+ COL_DATE+") = ? "+
                        " AND "+
                        "strftime('%m',"+ TABLE_ENTRIES+"."+ COL_DATE+") = ? ";


        //String query="select subHeadId  from entries where strftime('%m',"+helper.TABLE_ENTRIES+"."+helper.COL_DATE+")='07'";

        try {
            StringBuilder data=new StringBuilder();
            cursor=db.rawQuery(query,new String[]{subHead, String.valueOf(year), month});

            while(cursor.moveToNext()){
                String total=cursor.getString(0);
                data.append(total);
            }
            Toast.makeText(context,month+"/"+year+": "+data,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"getMonthlyTotal Error:    "+e.toString(),Toast.LENGTH_LONG).show();
        }
        // db.close();
        return cursor;
    }
}
