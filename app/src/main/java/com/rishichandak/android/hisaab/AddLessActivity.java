package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddLessActivity extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    private Intent intent;
    private AdapterClass adapterClass;
    private HelperClass helper;
    public static Date incomingDate;
    private String subTitleDate="Sat, 10 Sep,'94" ;
    private String sqlTypeDate="1994-09-10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_less);
        listView=(ListView) findViewById(R.id.listView);
        adapterClass=new AdapterClass(this);
        helper=new HelperClass(this);
        //Get Intent Date
        intent=getIntent();
        incomingDate=(Date)intent.getSerializableExtra("date");
        subTitleDate = getFormattedDate(incomingDate);
        sqlTypeDate=getSqlDate(incomingDate);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ BasicActivity.selectedHeadName  +" </font>"));
            getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>"+subTitleDate +" </font>"));
            getSupportActionBar().setElevation((float) 4.0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView.setAdapter(new CustomAdapter(this));
        //New Table Structure:  _id | entryId | subHead | quantity | underHearId | price |
        //helper.COL_ID,  helper.COL_JOIN_ENTRY_ID,  helper.COL_SUB_HEAD,  helper.COL_QUANTITY,   COL_UNDER_HEAD_ID,   helper.COL_PRICE

       /* listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
/*      adapterClass.insertValues(helper.TABLE_ENTRIES, new String[]{helper.COL_DATE,helper.COL_SUB_HEAD_ID,helper.COL_QUANTITY}, new String[]{getSqlDate(incommingDate),"1","2"});
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

                    String id,entryId,subHead,quantity;
                    StringBuilder data=new StringBuilder();
                    int total=listView.getCount();
                    for(int i=0; i<total; i++){
                        quantity=CustomAdapter.dataList.get(i).etQuantity;
                       if(!quantity.equals("0") ) {


                           entryId=CustomAdapter.dataList.get(i).tvEntryNo;

                           if (entryId.equals("-1")) {
                               id=CustomAdapter.dataList.get(i).tvItemNo;
                               subHead=CustomAdapter.dataList.get(i).tvItemName;
                               data.append(id + entryId + subHead + quantity + "\n");
                               String[] columnNames = new String[]{helper.COL_DATE, helper.COL_SUB_HEAD_ID, helper.COL_QUANTITY};
                               String[] columnValues = new String[]{sqlTypeDate, id, quantity};
                               long check = adapterClass.insertValues(helper.TABLE_ENTRIES, columnNames, columnValues);
                               if (check > 0) {
                                   Log.i("Insert",data.toString());
                                  // singleListView.setBackgroundColor(Color.GRAY);
                                   // Snackbar.make(findViewById(R.id.layoutAddLess), "Done", Snackbar.LENGTH_SHORT)
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
                    Log.e("error",e.toString());
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
    private String getFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM, ''yy", Locale.getDefault());
        return dateFormat.format(date);
    }
    public String getSqlDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }
}

class SingleRow{
    String tvItemNo;
    String tvEntryNo;
    String tvItemName;
    String etQuantity;
    String tvRate;
    SingleRow(String tvItemNo,String tvEntryNo,String tvItemName,String tvRate,String etQuantity){
        this.tvItemNo=tvItemNo;
        this.tvEntryNo=tvEntryNo;
        this.tvItemName=tvItemName;
        this.etQuantity=etQuantity;
        this.tvRate=tvRate;
    }
}

class CustomAdapter extends BaseAdapter {
    static ArrayList<SingleRow> dataList;
    private AddLessActivity mainClass;
    Cursor allDataCursor;
    Context context;
    AdapterClass adapterClass;
    Date incomingDate;
    CustomAdapter(Context c){
        context=c;
        adapterClass=new AdapterClass(context);
        mainClass=new AddLessActivity();
        incomingDate=mainClass.incomingDate;
        dataList=new ArrayList<SingleRow>();
        try {
            allDataCursor= adapterClass.getAllSubHeadsInfo(mainClass.getSqlDate(AddLessActivity.incomingDate),BasicActivity.selectedHeadNumber);
            while(allDataCursor.moveToNext()){
                String id=allDataCursor.getString(0);
                String rowId=allDataCursor.getString(1);
                String subhead=allDataCursor.getString(2);
                String quantity=allDataCursor.getString(3);
                String price=allDataCursor.getString(5);
                dataList.add(new SingleRow(id,rowId,subhead,price,quantity));
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.getLong( dataList.get(position).tvItemNo);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row= inflator.inflate(R.layout.list_add_less,parent,false);

        TextView tvItemNo= (TextView) row.findViewById(R.id.tvItemNo);
        TextView tvEntryNo= (TextView) row.findViewById(R.id.tvEntryNo);
        TextView tvItemName= (TextView) row.findViewById(R.id.tvItemName);
        final EditText etQuantity= (EditText) row.findViewById(R.id.etQuantity);
        TextView tvRate= (TextView) row.findViewById(R.id.tvRate);
        Button btnPlus= (Button) row.findViewById(R.id.btnPlus);
        Button btnMinus= (Button) row.findViewById(R.id.btnMinus);

        SingleRow temp=dataList.get(position);

        tvItemNo.setText(temp.tvItemNo);
        tvEntryNo.setText(temp.tvEntryNo);
        tvItemName.setText(temp.tvItemName);
        tvRate.setText(temp.tvRate);
        etQuantity.setText(temp.etQuantity);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty= Integer.parseInt(etQuantity.getText().toString());
                etQuantity.setText(String.valueOf(++qty));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty= Integer.parseInt( etQuantity.getText().toString());
                if(qty>0) {
                    etQuantity.setText(String.valueOf(--qty));
                }
                else {
                    Toast.makeText(context, "Quantity cannot be less than 0", Toast.LENGTH_LONG).show();
                }
            }
        });

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataList.get(position).etQuantity=etQuantity.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return row;
    }
}

