package com.example.karthi.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity{

    private EditText otpin;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    public static String phonenumber,passwd;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpin = (EditText) findViewById(R.id.otp);
        btn=(Button) findViewById(R.id.button2);

        phonenumber=getIntent().getStringExtra("emailid");
        passwd=getIntent().getStringExtra("password");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                create(phonenumber,passwd);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;

            }

        };
        Log.d("phoneauth","PhoneAuth going to execute");
        otpin.setText(phonenumber);
        String phonenumber1="+919488907642";

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber1,
                60,
                TimeUnit.SECONDS,
                otp.this,
                callBacks

        );
        Log.d("phoneauth","got pass phoneauth");


    }

    public void create(String phonenumber,String password){

        String email=phonenumber + "@gmail.com";
        String passwrd=password;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,passwrd).addOnCompleteListener(otp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Toast.makeText(otp.this,"Authentication Failed."+task.getException(),Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(otp.this,Success.class));
                }

            }
        });

    }

}
