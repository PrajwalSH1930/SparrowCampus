package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    DatabaseReference reference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://sparrow-campus-5b0c9-default-rtdb.firebaseio.com");

    EditText phone, password, confPassword, name, email;
    Button sign_up;
    TextView sign_in,textView1,textView2;
    TextInputLayout textInputLayout1,textInputLayout2,textInputLayout3,textInputLayout4, textInputLayout5;
    LinearLayout linearLayout;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        sign_in=findViewById(R.id.sign_in);
        name=findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        confPassword=findViewById(R.id.confPassword);
        sign_up=findViewById(R.id.register);

        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textInputLayout1=findViewById(R.id.textInputLayout1);
        textInputLayout2=findViewById(R.id.textInputLayout2);
        textInputLayout3=findViewById(R.id.textInputLayout3);
        textInputLayout4=findViewById(R.id.textInputLayout4);
        textInputLayout5= findViewById(R.id.textInputLayout5);
        linearLayout=findViewById(R.id.linearLayout);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        textView1.setTranslationX(-300);
        textView2.setTranslationX(-300);
        textInputLayout1.setTranslationY(300);
        textInputLayout2.setTranslationY(300);
        textInputLayout3.setTranslationY(300);
        textInputLayout4.setTranslationY(300);
        textInputLayout5.setTranslationY(300);
        linearLayout.setTranslationY(300);
        sign_up.setTranslationY(300);

        textView1.setAlpha(v);
        textView2.setAlpha(v);
        textInputLayout1.setAlpha(v);
        textInputLayout2.setAlpha(v);
        textInputLayout3.setAlpha(v);
        textInputLayout4.setAlpha(v);
        textInputLayout5.setAlpha(v);
        linearLayout.setAlpha(v);
        sign_up.setAlpha(v);;

        textView1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textView2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textInputLayout1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300);
        textInputLayout2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        textInputLayout3.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500);
        textInputLayout4.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500);
        textInputLayout5.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);

        sign_up.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
        linearLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName=name.getText().toString();
                String phoneNum =phone.getText().toString();
                String passwordText=password.getText().toString();
                String confPasswordText=confPassword.getText().toString();
                String emailName = email.getText().toString();

                if(fullName.isEmpty())
                    Toast.makeText(Register.this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show();
                else if(phoneNum.isEmpty())
                    Toast.makeText(Register.this, "Please Enter Your Phone Number!", Toast.LENGTH_SHORT).show();
                else if(emailName.isEmpty())
                    Toast.makeText(Register.this, "Please Enter Your Email!", Toast.LENGTH_SHORT).show();
                else if(passwordText.isEmpty())
                    Toast.makeText(Register.this, "Please Enter Your Password!", Toast.LENGTH_SHORT).show();
                else if(confPasswordText.isEmpty())
                    Toast.makeText(Register.this, "Please Confirm Your Password!", Toast.LENGTH_SHORT).show();
                else if(!passwordText.equals(confPasswordText))
                    Toast.makeText(Register.this, "Passwords doesn't match!", Toast.LENGTH_SHORT).show();
                else{


                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNum))
                                Toast.makeText(Register.this, "User from this number is already registered!", Toast.LENGTH_SHORT).show();
                            else {

                                reference.child("users").child(phoneNum).child("fullName").setValue(fullName);
                                reference.child("users").child(phoneNum).child("password").setValue(passwordText);
                                reference.child("users").child(phoneNum).child("email").setValue(emailName);

                                Toast.makeText(Register.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

    }
}