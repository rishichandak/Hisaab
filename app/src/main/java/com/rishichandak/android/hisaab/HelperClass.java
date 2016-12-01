package com.rishichandak.android.hisaab;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by rishi on 14-06-2016.
 */
public class HelperClass extends SQLiteOpenHelper {

    //DataBase Name
    private static final String DATABASE_NAME= "hisaabDb.db";

    //Table Name
    public static final String TABLE_HEADS="heads";
    public static final String TABLE_SUBHEADS="subheads";
    public static final String TABLE_RATES="rates";
    public static final String TABLE_ENTRIES="entries";

    //Common Table Column
    public static final String COL_ID="_id";

    //Heads Table Column
    public static final String COL_HEAD="head";

    //SubHead Table Column
    public static final String COL_SUB_HEAD="subHead";
    public static final String COL_UNDER_HEAD_ID="underHeadId";
    public static final String COL_SHOW="show";
    public static final String COL_ICON="icon";

    //Rate Table Column
    public static final String COL_UNDER_SUB_HEAD_ID="underSubHeadId";
    public static final String COL_WEFDATE="wefDate";
    public static final String COL_PRICE="price";

    //Entries Table Column
    public static final String COL_DATE="date";
    public static final String COL_SUB_HEAD_ID="subHeadId";
    public static final String COL_QUANTITY="quantity";

    //JOIN COLUMN NAME
    public static final String COL_JOIN_ENTRY_ID="entryId";
    public static final String COL_JOIN_TOTAL="total";

    //Database Version
    private static final int DATABASE_VERSION=1;
    private Context context;
    //Table Creation Query
    private static final String CREATE_HEADS= "CREATE TABLE "
            +TABLE_HEADS+ "("
            +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_HEAD+ " VARCHAR(100) UNIQUE );";

    private static final String CREATE_SUBHEADS= "CREATE TABLE "
            +TABLE_SUBHEADS+ "("
            +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COL_SUB_HEAD+ " VARCHAR(100) UNIQUE  ,"
            +COL_UNDER_HEAD_ID+ " INTEGER NOT NULL,"
            +COL_SHOW+ " INTEGER DEFAULT 1,"
            +COL_ICON+ " VARCHAR(255),"
            +" FOREIGN KEY(" +COL_UNDER_HEAD_ID+ ") REFERENCES " +TABLE_HEADS+ "( " +COL_ID+ " ));";

    private static final String CREATE_ENTRIES= "CREATE TABLE "
            +TABLE_ENTRIES+ "("
            +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COL_DATE+ " DATE NOT NULL,"
            +COL_SUB_HEAD_ID+ " INTEGER NOT NULL,"
            +COL_QUANTITY+ " REAL NOT NULL,"
            +"FOREIGN KEY(" +COL_SUB_HEAD_ID+ ") REFERENCES " +TABLE_SUBHEADS+ " ( " +COL_ID+ "));";

    private static final String CREATE_RATES= "CREATE TABLE "
            +TABLE_RATES+ "("
            +COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COL_UNDER_SUB_HEAD_ID+ " INTEGER NOT NULL,"
            +COL_WEFDATE+ " DATE NOT NULL,"
            +COL_PRICE+ " REAL NOT NULL,"
            +"UNIQUE("+COL_ID+","+COL_UNDER_SUB_HEAD_ID+","+COL_WEFDATE+"),"
            +"FOREIGN KEY(" +COL_UNDER_SUB_HEAD_ID+ ") REFERENCES " +TABLE_SUBHEADS+ " ( " +COL_ID+ "));";

    //Table Deletion Queries
    private static final String DELETE_HEADS= "DROP TABLE IF EXISTS "+TABLE_HEADS;
    private static final String DELETE_SUBHEADS= "DROP TABLE IF EXISTS "+TABLE_SUBHEADS;
    private static final String DELETE_ENTRIES= "DROP TABLE IF EXISTS "+TABLE_ENTRIES;
    private static final String DELETE_RATES= "DROP TABLE IF EXISTS "+TABLE_RATES;

    public HelperClass(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_HEADS);
            db.execSQL(CREATE_SUBHEADS);
            db.execSQL(CREATE_ENTRIES);
            db.execSQL(CREATE_RATES);
            Toast.makeText(context,"onCreate Called",Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DELETE_HEADS);
            db.execSQL(DELETE_SUBHEADS);
            db.execSQL(DELETE_ENTRIES);
            db.execSQL(DELETE_RATES);
            Toast.makeText(context,"onUpdate Called",Toast.LENGTH_SHORT).show();
            this.onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
