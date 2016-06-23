package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddLessActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterClass adapterClass;
    private HelperClass helper;
    private Toolbar toolbar;
    private Intent intent;
    private Date incomingDate;
    private String subTitleDate="Sat, 10 Sep,'94" ;
    private String sqlTypeDate="1994-09-10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        listView=(ListView) findViewById(R.id.listView);
        //Get Intent Date
        intent=getIntent();
        incomingDate=(Date)intent.getSerializableExtra("date");
        subTitleDate = getFormatedDate(incomingDate);
        sqlTypeDate=getSqlDate(incomingDate);
        adapterClass=new AdapterClass(getApplicationContext());
        helper=new HelperClass(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ BasicActivity.selectedHeadName  +" </font>"));
            getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>"+subTitleDate +" </font>"));
            getSupportActionBar().setElevation((float) 4.0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //New Table Structure:  _id | entryId | subHead | quantity | underHearId | price |
        //helper.COL_ID,  helper.COL_JOIN_ENTRY_ID,  helper.COL_SUB_HEAD,  helper.COL_QUANTITY,   COL_UNDER_HEAD_ID,   helper.COL_PRICE

        final Cursor allDataCursor=adapterClass.getAllSubHeadsInfo(getSqlDate(incomingDate),BasicActivity.selectedHeadNumber);
        String[] from=new String[]{helper.COL_ID, helper.COL_JOIN_ENTRY_ID, helper.COL_SUB_HEAD, helper.COL_PRICE, helper.COL_QUANTITY};
        int[] to=new int[]        {R.id.tvItemNo, R.id.tvEntryNo,           R.id.tvItemName,     R.id.tvRate,      R.id.etQuantity};

        //final Cursor allDataCursor= adapterClass.getAllSubHeads();
        //String[] from=new String[]{helper.COL_ID,helper.COL_SUB_HEAD,helper.COL_PRICE};
        //int[] to=new int[]{R.id.tvItemNo,R.id.tvItemName,R.id.tvRate};


        StringBuilder data=new StringBuilder();
        while(allDataCursor.moveToNext()){
            String id=allDataCursor.getString(0);
            String rowId=allDataCursor.getString(1);
            String subhead=allDataCursor.getString(2);
            String quantity=allDataCursor.getString(3);
            String price=allDataCursor.getString(4);
            data.append(id+" "+rowId+" "+subhead+" "+price+" "+quantity+"\n");
        }
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();

        SimpleCursorAdapter simpleCursorAdapter= null;
        try {
            simpleCursorAdapter = new SimpleCursorAdapter(AddLessActivity.this, R.layout.list_add_less, allDataCursor, from, to, 0)
            {
                @Override
                public void bindView(final View view, final Context context, Cursor cursor) {
                    super.bindView(view, context, cursor);

                    //  final int row_id=cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                    Button btnPlus= (Button) view.findViewById(R.id.btnPlus);
                    Button btnMinus= (Button) view.findViewById(R.id.btnMinus);

                    btnPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText etQuantity= (EditText) view.findViewById(R.id.etQuantity);
                            int qty= Integer.parseInt( etQuantity.getText().toString());
                            etQuantity.setText(String.valueOf(++qty));
                        }
                    });

                    btnMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText etQuantity= (EditText) view.findViewById(R.id.etQuantity);
                            int qty= Integer.parseInt( etQuantity.getText().toString());
                            if(qty>0) {
                                etQuantity.setText(String.valueOf(--qty));
                            }
                            else {
                                Toast.makeText(context, "Quantity cannot be less than 0", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            };
            simpleCursorAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(simpleCursorAdapter);

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int item_id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String item_content1 = cursor.getString(cursor.getColumnIndex(helper.COL_HEAD));
                String item = String.valueOf(item_id) + " : " + item_content1 +"\n";
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                Log.i("Click Event eeee","List Item: "+item);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int item_id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
                String item_content1 = cursor.getString(cursor.getColumnIndex(helper.COL_HEAD));
                String item = String.valueOf(item_id) + " : " + item_content1 +"\n";
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                Log.i("Click Event","List Item: "+item);
                return true;
            }
        }); */



/*

        adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"1","2"});
        adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"2","4"});
        adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"4","1"});

*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_less_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId=item.getItemId();
        switch(menuItemId){
            case R.id.action_save:
                try {
                    ListAdapter listAdapter= listView.getAdapter();
                    String id,entryId,subHead,quantity,underHead,price;
                    StringBuilder data=new StringBuilder();
                    int total=listView.getCount();
                    for(int i=0; i<total; i++){
                        View singleListView=listView.getChildAt(i);
                        quantity=((EditText)singleListView.findViewById(R.id.etQuantity)).getText().toString();
                       if(!quantity.equals("0") ) {

                           //Cursor cursor = (Cursor) listAdapter.getItem(i);
                           //id = cursor.getString(cursor.getColumnIndex(helper.COL_ID));
                           //entryId = cursor.getString(cursor.getColumnIndex(helper.COL_JOIN_ENTRY_ID));
                           //subHead = cursor.getString(cursor.getColumnIndex(helper.COL_SUB_HEAD));  //No Need
                           //underHead = cursor.getString(cursor.getColumnIndex(helper.COL_UNDER_HEAD_ID)); //No Need
                           //price = cursor.getString(cursor.getColumnIndex(helper.COL_PRICE));  //No need

                            id=((TextView)singleListView.findViewById(R.id.tvItemNo)).getText().toString();
                           entryId=((TextView)singleListView.findViewById(R.id.tvEntryNo)).getText().toString();
                           subHead=((TextView)singleListView.findViewById(R.id.tvItemName)).getText().toString();
                           price=((TextView)singleListView.findViewById(R.id.tvRate)).getText().toString();

                           data.append(id + entryId + subHead + quantity + "\n");

                           if (entryId.equals("-1")) {
                               String[] columnNames = new String[]{helper.COL_DATE, helper.COL_SUB_HEAD_ID, helper.COL_QUANTITY};
                               String[] columnValues = new String[]{sqlTypeDate, id, quantity};
                               long check = adapterClass.insertValues(helper.TABLE_ENTRIES, columnNames, columnValues);
                               if (check > 0) {
                                   Log.i("Insert",data.toString());
                                   singleListView.setBackgroundColor(Color.GRAY);

                                   singleListView.setBackgroundColor(Color.WHITE);

                                   //Snackbar.make(findViewById(R.id.layoutAddLess), "Done", Snackbar.LENGTH_SHORT)
                                    //       .setAction("Action", null).show();
                               } else {
                                    Log.i("Insert", "Error");
                                   //Snackbar.make(findViewById(R.id.layoutAddLess), "Fail", Snackbar.LENGTH_SHORT)
                                   //        .setAction("Action", null).show();
                               }


                           } else {

                           }

                       }

                    }
                   // Toast.makeText(this,data,Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.action_discard:
                Intent back=new Intent(this,BasicActivity.class);
                startActivity(back);
                break;
            default:Toast.makeText(this,"Wrong Selection",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private String getFormatedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM, ''yy", Locale.getDefault());
        return dateFormat.format(date);
    }
    private String getSqlDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }
}
