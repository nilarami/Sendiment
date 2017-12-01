package com.google.firebase.codelab.friendlychat;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.databinding.DataBindingUtil;
import com.google.firebase.codelab.friendlychat.databinding.ActivityFindUserBinding;

import java.util.ArrayList;
import java.util.List;


public class FindUser extends AppCompatActivity {

    List arrayList= new ArrayList();
    ActivityFindUserBinding activityMainBinding;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        arrayList.add("January");
        arrayList.add("February");
        arrayList.add("March");
        arrayList.add("April");
        arrayList.add("May");
        arrayList.add("June");
        arrayList.add("July");
        arrayList.add("August");
        arrayList.add("September");
        arrayList.add("October");
        arrayList.add("November");
        arrayList.add("December");

        adapter = (ListAdapter) new ListAdapter(arrayList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        SearchView searchview = findViewById(R.id.search);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

}
