package com.ffta.shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ShowAll extends AppCompatActivity {
Calendar cal=Calendar.getInstance();
RecyclerView  insert;
TextView date,bus;
String phone,bname;

    InsertionAdapter ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        date=(TextView)findViewById(R.id.date);
        bus=(TextView)findViewById(R.id.bus);
        bname=getIntent().getStringExtra("bname");
        bus.setText(bname);
        insert=(RecyclerView)findViewById(R.id.insert);  insert.setLayoutManager(new LinearLayoutManager(this));
        //ia.notifyDataSetChanged();
        ia = new InsertionAdapter(setList(), this);
        insert.setAdapter(ia);

    }
    public List<ShopData> setList() {
        List<ShopData> list = new ArrayList<>();
        int cday = cal.get(Calendar.DAY_OF_MONTH);
        int cyear = cal.get(Calendar.YEAR);
        int cmonth = cal.get(Calendar.MONTH);
        String month = "";
        if (cmonth == 1 - 1)
            month = "January";
        else if (cmonth == 2 - 1)
            month = "February";
        else if (cmonth == 3 - 1)
            month = "March";
        else if (cmonth == 4 - 1)
            month = "April";
        else if (cmonth == 5 - 1)
            month = "May";
        else if (cmonth == 6 - 1)
            month = "June";
        else if (cmonth == 7 - 1)
            month = "July";
        else if (cmonth == 8 - 1)
            month = "August";
        else if (cmonth == 9 - 1)
            month = "September";
        else if (cmonth == 10 - 1)
            month = "October";
        else if (cmonth == 11 - 1)
            month = "November";
        else month = "December";
        date.setText("Date: "+cday+" "+month+" "+cyear);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);

        HashMap<String, String> hm = sh.returnData();

        phone = hm.get(SessionManager.PHONE);
        FirebaseDatabase.getInstance().getReference("Users").child(phone).child(bname).child("BusinessDates").child(cday + " " + month + " " + cyear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();


                for (DataSnapshot ds : snapshot.getChildren()) {
                    ShopData sh=ds.getValue(ShopData.class);
                    list.add(sh);

                }
                ia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Toast.makeText(getApplicationContext(),sd1.getP_b(),Toast.LENGTH_LONG).show();
        return list;
    }
}