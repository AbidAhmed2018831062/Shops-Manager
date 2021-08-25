package com.ffta.shops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class PhoneNumber extends AppCompatActivity {
    private CountryCodePicker ccp;
    EditText phone;
    Button con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phone = (EditText) findViewById(R.id.phone);
        con = (Button) findViewById(R.id.con);

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = ccp.getSelectedCountryCode();
                String country = ccp.getSelectedCountryEnglishName();
                String number = phone.getText().toString();
                String phoneNum=code+number;
                HashMap hm=new HashMap();
                SessionManager sh=new SessionManager(PhoneNumber.this,SessionManager.USERSESSION);
                hm.put("Phone",phoneNum);
sh.loginSession(phoneNum);
                FirebaseDatabase.getInstance().getReference("Users").child(phoneNum).setValue(hm);
                startActivity(new Intent(getApplicationContext(),Personal_Information.class));

            }
        });
    }
}