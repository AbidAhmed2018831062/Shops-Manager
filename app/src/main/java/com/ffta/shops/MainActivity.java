package com.ffta.shops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
LinearLayout lin;
View view;
     TextView ask;
     EditText enter;
     Button can, con;
     int count=0;
     CheckBox paid,baki;
     String phone;
    Calendar cal=Calendar.getInstance();
     String name1,item1,amount1,paid1;
     String bname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lin=findViewById(R.id.lin);
        bname=getIntent().getStringExtra("bname");
        AddView();

    }
    private void AddView()
    {
        if(lin.getChildCount()!=0)
        {
            if(count==1)
                name1=enter.getText().toString();
            else if(count==2)
                item1=enter.getText().toString();
            else if(count==3)
                amount1=enter.getText().toString();
            lin.removeAllViews();
        }
       view=getLayoutInflater().inflate(R.layout.addingrow, null,false);
       ask=view.findViewById(R.id.ask);
       enter=view.findViewById(R.id.enter);
       can=view.findViewById(R.id.can);
       con=view.findViewById(R.id.con);
       paid=view.findViewById(R.id.paid);
       baki=view.findViewById(R.id.baki);
       lin.addView(view);
       con.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!enter.getText().toString().isEmpty()&&paid.getVisibility()==View.GONE) {

                   AddView();
               }
               else
               {
                   enter.setError("This field cannot be empty");
                   enter.requestFocus();

               }
                if(paid.getVisibility()==View.VISIBLE&&!paid.isChecked()&&!baki.isChecked())
               {
                   Toast.makeText(getApplicationContext(),"Select One Payment Method!!",Toast.LENGTH_LONG).show();
               }
           else  if(con.getText().toString().equals("Finish")){
                           if(paid.isChecked())
                               paid1="Paid";
                           else if(baki.isChecked())
                               paid1="Baki";
                           int cday=cal.get(Calendar.DAY_OF_MONTH);
                           int cyear=cal.get(Calendar.YEAR);
                    int cmonth=cal.get(Calendar.MONTH);
                    String month="";
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
                 ShopData sh1=new ShopData(name1,item1,Integer.parseInt(amount1),paid1);
                    SessionManager sh=new SessionManager(getApplicationContext(),SessionManager.USERSESSION);

                    HashMap<String,String> hm=sh.returnData();

                    phone=hm.get(SessionManager.PHONE);
                    Random rn=new Random();
                    long num=rn.nextInt(10000000);
                    FirebaseDatabase.getInstance().getReference("Users").child(phone).child(bname).child("BusinessDates").child(cday+" "+month+" "+cyear).child(num+"").setValue(sh1);

                           startActivity(new Intent(getApplicationContext(),DailyExpense.class));
                           finish();

                }

           }
       });
       if(count==0)
       {
           ask.setText("Enter Your Name:");
           con.setText("Continue");

           count++;
       }
       else if(count==1)
       {
           ask.setText("Enter Item Name:");
           con.setText("Continue");
           count++;
       }
       else if(count==2)
       {
           ask.setText("Enter Amount:");
           enter.setInputType(InputType.TYPE_CLASS_NUMBER);
           con.setText("Continue");
           count++;
       }
       else
       {
           con.setText("Finish");
           enter.setVisibility(View.GONE);
           baki.setVisibility(View.VISIBLE);
           paid.setVisibility(View.VISIBLE);
           if(baki.isChecked())
           {
                  paid.setVisibility(View.GONE);
           }
           else if(paid.isChecked())
           {
               baki.setVisibility(View.GONE);
           }

       }



    }

}