package com.ffta.shops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DailyExpense extends AppCompatActivity {
    RecyclerView insert;
    Calendar cal = Calendar.getInstance();
    InsertionAdapter ia;
    String phone;
    Button New,watch;
    TextView total,paid,baki,bus,date,item,name,system,amount2,name1,item1,system1,amount3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_expense);

        New = (Button) findViewById(R.id.New);
        watch = (Button) findViewById(R.id.watch);
        bus=(TextView)findViewById(R.id.bus);
        date=(TextView)findViewById(R.id.date);
        item=(TextView)findViewById(R.id.item);
        system=(TextView)findViewById(R.id.system);
        amount2=(TextView)findViewById(R.id.amount);
        name=(TextView)findViewById(R.id.name);
        name1=(TextView)findViewById(R.id.name1);
        system1=(TextView)findViewById(R.id.system1);
        amount3=(TextView)findViewById(R.id.amount1);
        item1=(TextView)findViewById(R.id.item1);
        total=(TextView)findViewById(R.id.total);
        paid=(TextView)findViewById(R.id.paid);


        baki=(TextView)findViewById(R.id.baki);
setList();
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowAll.class).putExtra("bname",bus.getText().toString()));
                finish();
            }
        });

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("bname",bus.getText().toString()));
                finish();
            }
        });

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
        FirebaseDatabase.getInstance().getReference("Users").child(phone).child(bus.getText().toString()).child("BusinessDates").child(cday + " " + month + " " + cyear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                long t=0,pai=0,ba=0;
                int y=0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    y++;

 ShopData sh3=ds.getValue(ShopData.class);
                    if(y==1)
                    {
                        name.setText(sh3.getName());
                        item.setText(sh3.getItem());
                        amount2.setText(sh3.getAmount()+"");
                        system.setText(sh3.getP_b());

                    }
                    else if(y==2)
                    {
                        name1.setText(sh3.getName());
                        item1.setText(sh3.getItem());
                        amount3.setText(sh3.getAmount()+"");
                        system1.setText(sh3.getP_b());
                    }
 long amount=sh3.getAmount();
 list.add(sh3);
 t+=amount;
 if(sh3.getP_b().contains("d"))
     pai+=amount;
 else
     ba+=amount;
                }
                if(y<=2)
                    watch.setVisibility(View.GONE);

                total.setText("Total: "+t+"");
                paid.setText("Paid: "+pai+"");
                baki.setText("Baki: "+ba+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Toast.makeText(getApplicationContext(),sd1.getP_b(),Toast.LENGTH_LONG).show();
        return list;
    }

}