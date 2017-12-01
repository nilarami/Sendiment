package com.google.firebase.codelab.friendlychat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.databinding.DataBindingUtil;
import com.google.firebase.codelab.friendlychat.databinding.ActivityFindUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        //arrayList.add("Test_User");


        adapter = (ListAdapter) new ListAdapter(arrayList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        SearchView searchview = findViewById(R.id.search);



        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query_string) {
                Query query = reference.child("users").orderByChild("username").equalTo(query_string);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // dataSnapshot is the "issue" node with all children with id 0
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                Log.d("FindUser", "onDataChange: " + issue.child("username").getValue());
                                arrayList.add(issue.child("username").getValue());
                                adapter.getFilter().filter(query_string);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String val =(String) parent.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(FindUser.this, R.style.MyDialogTheme);

                builder.setMessage("Are you sure you want to start a chat with " + val + "?")
                        .setTitle("Start Chat?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Starting Chat with " + val, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(FindUser.this, FriendlyMessage.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

            }
        });
    }

}
