package com.example.roomdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class add extends AppCompatActivity {
    Button btAdd, btReset;
    EditText nom,job,email,numero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final RoomDB database=RoomDB.getInstance(this);
        final List<MainData> dataList=new ArrayList<>();
        nom=findViewById(R.id.nomAdd);
        job=findViewById(R.id.jobAdd);
        email=findViewById(R.id.emailAdd);
        numero=findViewById(R.id.numAdd);
        btAdd=findViewById(R.id.Ajouter);
       final Intent intent = new Intent(this,MainActivity.class);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get string from edit text
                String snom=nom.getText().toString().trim();
                String sjob=job.getText().toString().trim();
                String semail=email.getText().toString().trim();
                String snumero=numero.getText().toString().trim();

                // check condition
                if(!snom.equals(""))
                {
                    // when text is not empty
                    // initialize main data

                    MainData data=new MainData();

                    //Set text on main data
                    data.setNom(snom);
                    data.setEmail(semail);
                    data.setJob(sjob);
                    data.setPhone(snumero);

                    //Insert text in database
                    database.mainDao().insert(data);

                    //Clear edit text
                    nom.setText("");

                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    startActivity(intent);
                }
            }
        });
    }
}