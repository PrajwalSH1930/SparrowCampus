package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfileFragment extends Fragment {

    TextView profileName, profilePassword;
    TextView titleName, titleUsername, userCount, staffCount;
    Button logoutButton;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference staffReference = FirebaseDatabase.getInstance().getReference("staff");



    public AdminProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        logoutButton = view.findViewById(R.id.editButton);
        profilePassword = view.findViewById(R.id.password);
        userCount = view.findViewById(R.id.userCount);
        staffCount = view.findViewById(R.id.staffCount);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                userCount.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        staffReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.child("cse").child("teaching").getChildrenCount()+snapshot.child("cse").child("nonTeaching").getChildrenCount()+
                        snapshot.child("ece").child("teaching").getChildrenCount()+ snapshot.child("ece").child("nonTeaching").getChildrenCount()+
                        snapshot.child("eee").child("teaching").getChildrenCount()+ snapshot.child("eee").child("nonTeaching").getChildrenCount()+
                        snapshot.child("ce").child("nonTeaching").getChildrenCount()+snapshot.child("ce").child("teaching").getChildrenCount()+
                        snapshot.child("me").child("nonTeaching").getChildrenCount()+snapshot.child("me").child("teaching").getChildrenCount();

                staffCount.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String password = getArguments().getString("password");

            profileName.setText(name);
            profilePassword.setText(password);
            titleName.setText(name);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AdminLoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Admin Logged Out!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}