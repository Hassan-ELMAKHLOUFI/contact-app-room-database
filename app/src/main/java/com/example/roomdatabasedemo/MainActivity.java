package com.example.roomdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

// Initialize variable

    EditText nom,job,email,numero;
    FloatingActionButton btAdd;
    RecyclerView recyclerView;
    Intent intent;
    List<MainData> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,add.class);
        //Assign variable

        nom=findViewById(R.id.nomAdd);
        job=findViewById(R.id.jobAdd);
        email=findViewById(R.id.emailAdd);
        numero=findViewById(R.id.numAdd);
        btAdd=findViewById(R.id.bt_add);
        recyclerView=findViewById(R.id.recycler_view);

        // initialize database
        database=RoomDB.getInstance(this);

        // store database value in data list

        dataList=database.mainDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager=new LinearLayoutManager(this);

        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        // Initialize adapter
        adapter=new MainAdapter(MainActivity.this,dataList);

        // set adapter

        recyclerView.setAdapter(adapter);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //get string from edit text
//                String snom=nom.getText().toString().trim();
//                String sjob=job.getText().toString().trim();
//                String semail=email.getText().toString().trim();
//                String snumero=numero.getText().toString().trim();
//
//                // check condition
//                if(!snom.equals(""))
//                 {
//                    // when text is not empty
//                    // initialize main data
//
//                 MainData data=new MainData();
//
//                 //Set text on main data
//                    data.setNom(snom);
//                    data.setEmail(semail);
//                    data.setJob(sjob);
//                    data.setPhone(snumero);
//
//                    //Insert text in database
//                    database.mainDao().insert(data);
//
//                    //Clear edit text
//                    nom.setText("");
//
//                    //Notify when data is inserted
//                    dataList.clear();
//                    dataList.addAll(database.mainDao().getAll());
//                    adapter.notifyDataSetChanged();

   //             }
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    dataList.clear();
                    for (int i = 0; i < database.mainDao().findUserWithName(s).size(); i++) {
                        System.out.println("**8888888888888888888");
                    }
                    dataList.addAll(database.mainDao().findUserWithName(s));
                    adapter.notifyDataSetChanged();
                } else {
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }
}
