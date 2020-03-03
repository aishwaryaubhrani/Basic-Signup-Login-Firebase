package com.example.minor2muskan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button signin;
    TextView login_text;
    FirebaseAuth nFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editText1);
        password = findViewById(R.id.editText2);
        signin = findViewById(R.id.button);
        login_text = findViewById(R.id.textView);


        nFirebaseAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String musername = username.getText().toString();
                String mpassword = password.getText().toString();
                if(musername.isEmpty()){
                    username.setError("Please enter username");
                    username.requestFocus();
                }
                else if(mpassword.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(musername.isEmpty() && mpassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(musername.isEmpty() && mpassword.isEmpty())){


                    nFirebaseAuth.createUserWithEmailAndPassword(musername, mpassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else {

                                nFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(MainActivity.this, "Verification Mail has been sent. Please check your email.", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Login.class);
                startActivity(in);
            }
        });
    }
}
