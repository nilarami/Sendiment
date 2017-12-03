package com.google.firebase.codelab.friendlychat;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;



public class ToneAnalyzer extends AppCompatActivity {

    //Watson
    com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer toneAnalyzer = new com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer("2016-05-19",
            "6a838dfd-893d-4325-848f-e93e29bf15cf",
            "kENotTgRfB8u");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tone_analyzer);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String text =
                "I know the times are difficult! Our sales have been doing great";

// Call the service and get the tone
        ToneOptions toneOptions = new ToneOptions.Builder().html(text).build();
        ToneAnalysis tone = toneAnalyzer.tone(toneOptions).execute();
        System.out.println(tone);
        System.out.println("TONE IS: " + tone.getDocumentTone());

    }



}