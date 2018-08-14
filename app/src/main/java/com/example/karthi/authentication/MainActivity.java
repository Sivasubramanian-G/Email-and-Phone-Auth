package com.example.karthi.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();

        btnSignIn=(Button) findViewById(R.id.login);
        btnSignUp=(Button) findViewById(R.id.register);
        inputEmail=(EditText) findViewById(R.id.emaill);
        inputPassword=(EditText) findViewById(R.id.password);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword=(Button) findViewById(R.id.resetpass);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=inputEmail.getText().toString().trim();
                String password=inputPassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getApplicationContext(),"Enter email or phone number!!!",Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter password!!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password is too short, enter minimum 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }

                char[] arr=email.toCharArray();

                if(arr[0]>='0'&&arr[0]<='9'){
                    if(arr[1]>='0'&&arr[1]<='9'){
                        if(arr[2]>='0'&&arr[2]<='9'){

                            btnSignUp.setText("SEND");
                            phno(email,password);

                        }
                    }
                }

                if(arr[0]>='a'&&arr[0]<='z' || arr[0]>='A'&&arr[0]<='Z'){
                    if(arr[1]>='a'&&arr[1]<='z' || arr[1]>='A'&&arr[1]<='Z'){
                        if(arr[2]>='a'&&arr[2]<='z' || arr[2]>='A'&&arr[2]<='Z'){
                            btnSignUp.setText("SIGN UP");
                            reg(email,password);
                        }
                    }
                }


            }
        });

    }

    public void reg(String email,String password) {
        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(MainActivity.this,"Email id successfully registered",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Authentication Failed."+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(MainActivity.this,Success.class));
                        }

                    }
                });

    }

    public void phno(String phno,String password){

        progressBar.setVisibility(View.VISIBLE);

        startActivity(new Intent(MainActivity.this,otp.class));



    }

    public void reset(View view) {

        startActivity(new Intent(MainActivity.this,Resetpass.class));

    }

    public void loggingin(View view) {

        startActivity(new Intent(MainActivity.this,Login.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
