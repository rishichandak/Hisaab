package com.rishichandak.android.hisaab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

public class ListTry extends AppCompatActivity {

    ListView listView;
    private Intent intent;
    public static Date incomingDate;
    private String sqlTypeDate="1994-09-10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);
        listView= (ListView) findViewById(R.id.listView1);
        intent=getIntent();
        incomingDate=(Date)intent.getSerializableExtra("date");
        listView.setAdapter(new CustomAdapter(this));
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

class CustomAdapter extends BaseAdapter{
    ArrayList<SingleRow> list;
    ListTry listTry;
    Cursor allDataCursor;
    Context context;
    AdapterClass adapterClass;
    Date incomingDate;
    CustomAdapter(Context c){
        context=c;
        adapterClass=new AdapterClass(context);
        listTry=new ListTry();
        incomingDate=listTry.incomingDate;
        list=new ArrayList<SingleRow>();
        try {
            allDataCursor= adapterClass.getAllSubHeadsInfo(getSqlDate(incomingDate),BasicActivity.selectedHeadNumber);
            while(allDataCursor.moveToNext()){
                String id=allDataCursor.getString(0);
                String rowId=allDataCursor.getString(1);
                String subhead=allDataCursor.getString(2);
                String quantity=allDataCursor.getString(3);
                String price=allDataCursor.getString(5);
                list.add(new SingleRow(id,rowId,subhead,price,quantity));
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.getLong( list.get(position).tvItemNo);
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

        SingleRow temp=list.get(position);

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
            list.get(position).etQuantity=etQuantity.getText().toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return row;
    }

    public String getSqlDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }
}
