package com.ffta.shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class Personal_Information extends AppCompatActivity {
Button fin,old,newB;
EditText name,business,code;
TextView or,ask; String cod = "";

Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__information);
        fin=(Button)findViewById(R.id.fin);
        newB=(Button)findViewById(R.id.newB);
        old=(Button)findViewById(R.id.old);
        name=(EditText) findViewById(R.id.name);
        code=(EditText) findViewById(R.id.code);
        or=(TextView) findViewById(R.id.or);
        ask=(TextView) findViewById(R.id.ask);
        business=(EditText) findViewById(R.id.business);
        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newB.setVisibility(View.GONE);
                old.setVisibility(View.GONE);
                or.setVisibility(View.GONE);
                fin.setVisibility(View.VISIBLE);
                ask.setVisibility(View.VISIBLE);
                ask.setText(R.string.Ask);
                code.setVisibility(View.VISIBLE);
            }
        });
        newB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newB.setVisibility(View.GONE);
                old.setVisibility(View.GONE);
                or.setVisibility(View.GONE);
                fin.setVisibility(View.VISIBLE);
                Random rn=new Random();
                String random= new String("0123456789@123#%&*/abcdefghijklmnopqrfstuvwxyz");

                ask.setVisibility(View.VISIBLE);
                ask.setText(R.string.Ask);
                code.setVisibility(View.VISIBLE);
                for(int i=0;i<9;i++)
                {
                    int r=rn.nextInt(random.length());
                    cod+=random.charAt(r);

                }
                code.setText(cod);
                code.setEnabled(false);

                business.setVisibility(View.VISIBLE);

            }
        });

        spinner=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> ar=ArrayAdapter.createFromResource(this,R.array.Age1,android.R.layout.simple_spinner_item);
        ar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ar);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().isEmpty()&&!business.getText().toString().isEmpty()&&old.getVisibility()!=View.VISIBLE)
                {
                    HashMap hm=new HashMap() ;
                    hm.put("Name",name.getText().toString());
                    SessionManager sh=new SessionManager(getApplicationContext(),SessionManager.USERSESSION);

                    HashMap<String,String> hm2=sh.returnData();

                  String  Phone=hm2.get(SessionManager.PHONE);
                    FirebaseDatabase.getInstance().getReference("Users").child(Phone).updateChildren(hm);
                    Random rn=new Random();
                    long r=rn.nextInt(1000000);
                    HashMap hm1=new HashMap() ;
                    hm1.put("Name"+r,business.getText().toString());

                    FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("BusinessNames").updateChildren(hm1);
                    ReferCodes rc=new ReferCodes(cod,Phone,business.getText().toString(),name.getText().toString());
                    FirebaseDatabase.getInstance().getReference("ReferCodes").child(cod).setValue(rc);
                    startActivity(new Intent(getApplicationContext(),DailyExpense.class));
                    finish();

                }
                else if(name.getText().toString().isEmpty())
                {
                    name.setError("This field needs to be filled");
                    name.requestFocus();
                }
                else if(business.getVisibility()==View.VISIBLE&&business.getText().toString().isEmpty())
                {
                    business.setError("This field needs to be filled");
                    business.requestFocus();
                }
                else
                {
                    if(code.getText().toString().isEmpty())
                    {
                        code.setError("This field needs to be filled");
                        code.requestFocus();
                    }
                    else
                    {
                        cod=code.getText().toString();
                    Query c= FirebaseDatabase.getInstance().getReference("ReferCodes").orderByChild("refer").equalTo(cod);
                    c.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                SessionManager sh=new SessionManager(getApplicationContext(),SessionManager.USERSESSION);

                                HashMap<String,String> hm2=sh.returnData();

                                String  Phone=hm2.get(SessionManager.PHONE);
                                   String BNAME=snapshot.child(cod).child("bname").getValue().toString();
                                   String PHONE=snapshot.child(cod).child("phone").getValue().toString();
                                   HashMap hm5=new HashMap();
                                   hm5.put("SBName",BNAME);
                                   hm5.put("Phone",PHONE);
                                   Random rm3=new Random();
                                   long yui=rm3.nextInt(10000000);
                                   HashMap hm6=new HashMap();
                                hm6.put("SBName",BNAME);
                                hm6.put("Phone",Phone);
                                hm6.put("Name",name.getText().toString());


                                   FirebaseDatabase.getInstance().getReference("Users").child(Phone).child("SharedBusiness").child(yui+"").setValue(hm5);
                                   FirebaseDatabase.getInstance().getReference("Users").child(PHONE).child("SharedBusinessWith").child(yui+"").setValue(hm6);

                                Toast.makeText(getApplicationContext(),"Added With The Business",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),cod+"",Toast.LENGTH_LONG).show();
                                code.setError(R.string.CodeError+" ");
                                code.requestFocus();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    }
                }
            }
        });

    }
}