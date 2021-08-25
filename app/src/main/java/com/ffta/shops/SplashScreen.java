package com.ffta.shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
String phone2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);

        HashMap<String,String> hm=sh.returnData();

     phone2=hm.get(SessionManager.PHONE);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                New();
            }
        });
        t.start();


    }
    private void doWork() {
        for(int i=10;i<=60;i=i+20){
            try{
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void New()
    {
        if(phone2==null||phone2.isEmpty())
        {
          //  Toast.makeText(getApplicationContext(),phone2,Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),PhoneNumber.class));
            finish();
        }
        else{
            FirebaseDatabase.getInstance().getReference("Users").child(phone2).child("BusinessNames").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                  long k=snapshot.getChildrenCount();
                  if(k==0)
                  {
                      FirebaseDatabase.getInstance().getReference("Users").child(phone2).child("SharedBusiness").addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              long d=snapshot.getChildrenCount();
                              if(d==0)
                              {
                                  startActivity(new Intent(getApplicationContext(),Personal_Information.class));
                                  finish();
                              }
                              else
                              {
                                  startActivity(new Intent(getApplicationContext(),DailyExpense.class));
                                  finish();
                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {

                          }
                      });
                  }
                  else
                  {
                      startActivity(new Intent(getApplicationContext(),DailyExpense.class));
                      finish();
                  }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }

}