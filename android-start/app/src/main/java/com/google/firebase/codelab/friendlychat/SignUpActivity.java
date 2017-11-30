package com.google.firebase.codelab.friendlychat;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText fname;
    private EditText bday;
    private EditText uname;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button submit = findViewById(R.id.btn_submit_sign_up);
        mEmailField = findViewById(R.id.signup_email);
        mPasswordField = findViewById(R.id.signup_pswd);
        fname = findViewById(R.id.fname);
        bday = findViewById(R.id.birthday_spinner);
        uname = findViewById(R.id.username);
        progressdialog = new ProgressDialog(SignUpActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password){
        if (!validateForm()) {
            return;
        }
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        final String fullname = fname.getText().toString();
        final String birthday = bday.getText().toString();
        final String username = uname.getText().toString();
        final String user_email = email;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            User usr = new User(username, fullname, user_email, birthday);
                            setProfileInfo(usr, user.getUid().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }

                        // [START_EXCLUDE]
                        progressdialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void setProfileInfo(User user, String user_id) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user_id).setValue(user);



    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String fullname = fname.getText().toString();
        if (TextUtils.isEmpty(fullname)) {
            fname.setError("Required.");
            valid = false;
        } else {
            fname.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser currentUser) {

        //TODO Update UI Based on what the currentUser is
        Toast.makeText(SignUpActivity.this, "Updating UI...",
                Toast.LENGTH_SHORT).show();


    }
}
 class User {

    public String username;
    public String email;
    public String fullname;
    public String birthday;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String fname, String email, String birthday) {
        this.username = username;
        this.fullname = fname;
        this.email = email;
        this.birthday = birthday;
    }

}