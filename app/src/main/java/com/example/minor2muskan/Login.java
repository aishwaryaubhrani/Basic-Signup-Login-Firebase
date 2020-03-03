package com.example.minor2muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText lemail, lpass;
    Button mlogin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lemail = findViewById(R.id.editText3);
        lpass = findViewById(R.id.editText4);
        mlogin = findViewById(R.id.button2);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!= null && mFirebaseUser.isEmailVerified()){
                    Toast.makeText(Login.this, "You are Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Home.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = lemail.getText().toString();
                String mpassword = lpass.getText().toString();
                if(emailid.isEmpty()){
                    lemail.setError("Please enter Email id");
                    lemail.requestFocus();
                }
                else if(mpassword.isEmpty()){
                    lpass.setError("Please enter password");
                    lpass.requestFocus();
                }
                else if(emailid.isEmpty() && mpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(emailid.isEmpty() && mpassword.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailid, mpassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                                    Intent in = new Intent(Login.this,Home.class);
                                    startActivity(in);
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this, "Please Verify your email adress.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }
}
