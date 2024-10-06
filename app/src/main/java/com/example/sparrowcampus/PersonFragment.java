package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PersonFragment extends Fragment {

    TextView profileName, profileEmail, profilePhone, profilePassword;
    TextView titleName, titleUsername;
    Button editButton, logOut;
    LinearLayout availLayout;
    AppCompatImageView apiv;
    Switch availSwitch;

    DatabaseReference databaseReference;
    String name, email, phone, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        // Initialize the TextViews
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.emailName);
        profilePhone = view.findViewById(R.id.phoneNum);
        profilePassword = view.findViewById(R.id.password);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        editButton = view.findViewById(R.id.editButton);
        logOut = view.findViewById(R.id.logout);
        availLayout = view.findViewById(R.id.availLayout);
        apiv = view.findViewById(R.id.apiv1);
        availSwitch = view.findViewById(R.id.availSwitch);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (getArguments() != null) {
            name = getArguments().getString("name");
            email = getArguments().getString("email");
            phone = getArguments().getString("phone");
            password = getArguments().getString("password");

            profileName.setText(name);
            profileEmail.setText(email);
            profilePhone.setText(phone);
            profilePassword.setText(password);
            titleName.setText(name);
            titleUsername.setText(email);
        }

        checkPhoneInStaffNumbers(phone);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                Toast.makeText(getActivity(), "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                SessionManagement.clearData(getActivity());
            }
        });

        return view;
    }


    private void checkPhoneInStaffNumbers(String staffPhone) {
        if (staffPhone != null) {
            staffPhone = profilePhone.getText().toString();
            String finalStaffPhone = staffPhone;
            databaseReference.child("staffNumbers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(finalStaffPhone)) {
                        availLayout.setVisibility(View.VISIBLE);
                        apiv.setVisibility(View.VISIBLE);

                        Boolean value = snapshot.child(finalStaffPhone).child("avail").getValue(Boolean.class);
                        if (value == null) {
                            value = false; // Default value if 'avail' is null
                        }

                        availSwitch.setChecked(value);
                        availSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                databaseReference.child("staffNumbers").child(finalStaffPhone).child("avail").setValue(isChecked).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Value updated successfully.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to update value.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    } else {
                        Log.e("Phone Number", "Phone number not present in the staffNumbers database: " + finalStaffPhone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Database Error", error.getMessage());
                }
            });
        } else {
            Log.e("Phone Number", "Phone number is null");
        }
    }


    public void passUserData() {
        String userPhone = profilePhone.getText().toString();
        String userName = profileName.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("fullName").equalTo(userName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String nameFromDB = userSnapshot.child("fullName").getValue(String.class);
                        String emailFromDB = userSnapshot.child("email").getValue(String.class);
                        String passFromDB = userSnapshot.child("password").getValue(String.class);

                        Intent intent = new Intent(getActivity(), EditProfile.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passFromDB);
                        intent.putExtra("phone", userPhone);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database Error", error.getMessage());
            }
        });
    }
}
