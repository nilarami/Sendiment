package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToneAnalyzer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tone_analyzer);

        Button messageSent = (Button) findViewById(R.id.msendingsent);

        messageSent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
//                String text = msending
//                //declare edit text field
//                EditText msending = findViewById(R.id.msending);

            }
        });



    }


}
