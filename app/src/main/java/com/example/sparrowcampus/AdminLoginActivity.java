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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sparrow-campus-5b0c9-default-rtdb.firebaseio.com/");

    Button sign_in;
    TextView textView1, textView2, user;
    TextInputLayout textInputLayout1, textInputLayout2;
    EditText username, password;
    LinearLayout linearLayout;
    float v = 0;
    int staffCount, userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);

        sign_in = findViewById(R.id.log_in);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        linearLayout = findViewById(R.id.linearLayout);
        user = findViewById(R.id.user);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminLoginActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        textView1.setTranslationX(-300);
        textView2.setTranslationX(-300);
        textInputLayout1.setTranslationY(300);
        textInputLayout2.setTranslationY(300);
        sign_in.setTranslationY(300);
        linearLayout.setTranslationY(300);


        textView1.setAlpha(v);
        textView2.setAlpha(v);
        textInputLayout1.setAlpha(v);
        textInputLayout2.setAlpha(v);
        sign_in.setAlpha(v);
        linearLayout.setAlpha(v);


        textView1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textView2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200);
        textInputLayout1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300);
        textInputLayout2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400);
        sign_in.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);
        linearLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passwordText = password.getText().toString();

                if (userName.isEmpty()) {
                    textInputLayout1.setError("Please Enter Admin Username!");
                } else if (passwordText.isEmpty()) {
                    textInputLayout2.setError("Please Enter Admin Password!");
                } else {
                    reference.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(userName)) {
                                String getPassword = snapshot.child(userName).child("password").getValue(String.class);
                                if (getPassword != null && getPassword.equals(passwordText)) {
                                    String fullName = snapshot.child(userName).child("username").getValue(String.class);

                                    Toast.makeText(AdminLoginActivity.this, "Logged in as Admin!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(AdminLoginActivity.this, AdminHomePage.class);
                                    intent.putExtra("name", fullName);
                                    intent.putExtra("password",getPassword);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminLoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminLoginActivity.this, "Admin doesn't exist!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Handle database error
                        }
                    });
                }
            }
        });


    }
}