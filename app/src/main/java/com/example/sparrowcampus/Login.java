package com.example.sparrowcampus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sparrow-campus-5b0c9-default-rtdb.firebaseio.com/");

    Button sign_in;
    TextView sign_up, textView1, textView2, admin;
    TextInputLayout textInputLayout1, textInputLayout2;
    EditText phone, password;
    LinearLayout linearLayout, linearLayout2;
    Switch active;
    float v = 0;

    String fullName, email, phoneNum, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_up = findViewById(R.id.sign_up);
        sign_in = findViewById(R.id.log_in);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout2 = findViewById(R.id.linearLayout2);
        active = findViewById(R.id.active);
        admin = findViewById(R.id.admin);

        admin.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, AdminLoginActivity.class);
            startActivity(intent);
            finish();
        });

        sign_up.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        animateViews();

        sign_in.setOnClickListener(view -> {
            phoneNum = phone.getText().toString();
            String passwordText = password.getText().toString();

            if (phoneNum.isEmpty()) {
                textInputLayout1.setError("Please Enter Your Phone Number!");
            } else if (passwordText.isEmpty()) {
                textInputLayout2.setError("Please Enter Your Password!");
            } else {
                loginUser(phoneNum, passwordText);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SessionManagement.getDataLogin(this)) {
            if (SessionManagement.getDataAs(this).equals("user")) {
                navigateToHomePage();
            }
        }
    }

    private void animateViews() {
        textView1.setTranslationX(-300);
        textView2.setTranslationX(-300);
        textInputLayout1.setTranslationY(300);
        textInputLayout2.setTranslationY(300);
        active.setTranslationY(300);
        sign_in.setTranslationY(300);
        linearLayout.setTranslationY(300);
        linearLayout2.setTranslationY(300);

        textView1.setAlpha(v);
        textView2.setAlpha(v);
        textInputLayout1.setAlpha(v);
        textInputLayout2.setAlpha(v);
        active.setAlpha(v);
        sign_in.setAlpha(v);
        linearLayout.setAlpha(v);
        linearLayout2.setAlpha(v);

        textView1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textView2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textInputLayout1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300);
        textInputLayout2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        active.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        sign_in.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
        linearLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
        linearLayout2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
    }

    private void loginUser(String phoneNum, String passwordText) {
        reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(phoneNum)) {
                    getPassword = snapshot.child(phoneNum).child("password").getValue(String.class);
                    if (getPassword != null && getPassword.equals(passwordText)) {

                        if (active.isChecked()) {
                            SessionManagement.setDataLogin(Login.this, true);
                            SessionManagement.setDataAs(Login.this, "user");
                        } else {
                            SessionManagement.setDataLogin(Login.this, false);
                        }

                        fullName = snapshot.child(phoneNum).child("fullName").getValue(String.class);
                        email = snapshot.child(phoneNum).child("email").getValue(String.class);

                        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("fullName",fullName);
                        editor.putString("email",email);
                        editor.putString("phone",phoneNum);
                        editor.putString("password",getPassword);
                        editor.apply();


                        Toast.makeText(Login.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                        getFCMToken();
                        navigateToHomePage();
                    } else {
                        Toast.makeText(Login.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Account doesn't exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void navigateToHomePage() {
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName","");
        String email = sharedPreferences.getString("email","");
        String phoneNum = sharedPreferences.getString("phone","");
        String getPassword = sharedPreferences.getString("password","");


        Intent intent = new Intent(Login.this, HomePage.class);
        intent.putExtra("name", fullName);
        intent.putExtra("phone", phoneNum);
        intent.putExtra("email", email);
        intent.putExtra("password", getPassword);
        startActivity(intent);
        finish();
    }

    private void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Log.i("My Token", token);
            }
        });
    }
}
