package com.example.sparrowcampus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DescFragment extends Fragment {
    private static final int REQUEST_CALL = 1;

    private String fullName, des, purl, phone, address, email, qual, special, exp;
    boolean isAvail;

    public DescFragment() {
        // Default constructor
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("staffNumbers");

    public DescFragment(String address, String des, String email, String exp, String fullName, String phone, String purl, String qual, String special, boolean isAvail) {
        this.fullName = fullName;
        this.des = des;
        this.phone = phone;
        this.purl = purl;
        this.address = address;
        this.email = email;
        this.exp = exp;
        this.qual = qual;
        this.special = special;
        this.isAvail = isAvail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desc, container, false);

        CircleImageView profileImg = view.findViewById(R.id.profileImg);
        TextView titleName = view.findViewById(R.id.titleName);
        TextView design = view.findViewById(R.id.design);
        TextView phoneNum = view.findViewById(R.id.phoneNum);
        TextView addressName = view.findViewById(R.id.addressName);
        TextView emailName = view.findViewById(R.id.emailName);
        TextView expName = view.findViewById(R.id.expName);
        TextView qualName = view.findViewById(R.id.qualName);
        TextView specialName = view.findViewById(R.id.specialName);
        TextView isAvailText = view.findViewById(R.id.isAvail);

        titleName.setText(fullName);
        design.setText(des);
        phoneNum.setText(phone);
        Glide.with(requireContext()).load(purl).into(profileImg);
        addressName.setText(address);
        emailName.setText(email);
        expName.setText(exp);
        qualName.setText(qual);
        specialName.setText(special);

        LocalDate currentDate = LocalDate.now();
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        LocalTime currentTime = LocalTime.now();
        LocalTime collegeStart = LocalTime.of(9, 0);
        LocalTime collegeEnd = LocalTime.of(17,0);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean present = snapshot.child(phone).child("avail").getValue(Boolean.class);
                if (present != null) {
                    if (present && !currentTime.isAfter(collegeEnd) && !currentTime.isBefore(collegeStart) && !dayOfWeek.equals("Sunday")) {
                        isAvailText.setText("(Available)");
                    } else if (!present && !currentTime.isAfter(collegeEnd) && !currentTime.isBefore(collegeStart) && !dayOfWeek.equals("Sunday")) {
                        isAvailText.setText("(Unavailable)");
                    } else {
                        isAvailText.setText("(College is Closed!)");
                    }
                } else {
                    isAvailText.setText("(Status unknown)");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });

        phoneNum.setOnClickListener(v -> call());
        //emailName.setOnClickListener(v -> sendEmail());

        return view;
    }

    private void call() {
        if (phone != null && !phone.trim().isEmpty()) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(requireContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        if (email != null && !email.trim().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email));
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
