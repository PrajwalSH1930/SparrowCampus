package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    EditText editName, editPhone, editEmail, editPassword;
    Button updateButton;
    FloatingActionButton backButton;
    String nameUser, emailUser, phoneUser, passwordUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editName = findViewById(R.id.fullName);
        editPhone = findViewById(R.id.phone);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        updateButton = findViewById(R.id.update);
        backButton = findViewById(R.id.backButton);
        showData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChange() || isEmailChange() || isPasswordChange()) {
                    Toast.makeText(EditProfile.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate back to HomePage, assuming it hosts the PersonFragment
                    Intent intent = new Intent(EditProfile.this, HomePage.class);
                    intent.putExtra("name", nameUser);
                    intent.putExtra("email", emailUser);
                    intent.putExtra("phone", phoneUser);
                    intent.putExtra("password", passwordUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditProfile.this, "No changes found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, HomePage.class);
                intent.putExtra("name", nameUser);
                intent.putExtra("email", emailUser);
                intent.putExtra("password", passwordUser);
                intent.putExtra("phone", phoneUser);
                startActivity(intent);
            }
        });
    }

    public boolean isNameChange() {
        if (!nameUser.equals(editName.getText().toString())) {
            reference.child(phoneUser).child("fullName").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailChange() {
        if (!emailUser.equals(editEmail.getText().toString())) {
            reference.child(phoneUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordChange() {
        if (!passwordUser.equals(editPassword.getText().toString())) {
            reference.child(phoneUser).child("password").setValue(editPassword.getText().toString());
            passwordUser = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData() {
        Intent intent = getIntent();
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        phoneUser = intent.getStringExtra("phone");
        passwordUser = intent.getStringExtra("password");

        editName.setText(nameUser);
        editPhone.setText(phoneUser);
        editEmail.setText(emailUser);
        editPassword.setText(passwordUser);
    }
}
