package com.google.firebase.codelab.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddChat extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(AddChat.this, FindUser.class);
                startActivity(intent);

            }
        });

        final ArrayList<String> arrList = new ArrayList<String>();
        final ListView listView1 = (ListView) findViewById(R.id.chats_list_view);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseUser.getDisplayName();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Query query = mDatabase.child("users").equalTo(mFirebaseUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                     if (dataSnapshot.exists()) {
                                                         // dataSnapshot is the "issue" node with all children with id 0
                                                         Log.d("TAGGGGGGGGG", "onDataChange: " + dataSnapshot.toString());
                                                         for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                                             // do something with the individual "issues"
                                                             Log.d("ADDCHAT", "onDataChange: " + issue.child("texting").getValue());
                                                             String temp = issue.child("texting").getValue().toString();
                                                             //arrayList.add(issue.child("username").getValue());
                                                             //adapter.getFilter().filter(query_string);
                                                             arrList.add(temp);

                                                         }
                                                     }

                                                 }

                                                 @Override
                                                 public void onCancelled(DatabaseError databaseError) {

                                                 }

                                             });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrList);
        listView1.setAdapter(adapter);







    }

}
