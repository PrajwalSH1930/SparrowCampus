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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminHomeFragment extends Fragment {

    TextView civil, cse, ece, eee, me, email, officePhone, mobile1, mobile2;
    private final int REQUEST_CALL=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        civil = view.findViewById(R.id.civil);
        cse = view.findViewById(R.id.cse);
        ece = view.findViewById(R.id.ece);
        eee = view.findViewById(R.id.eee);
        me = view.findViewById(R.id.me);

        email = view.findViewById(R.id.email);
        officePhone = view.findViewById(R.id.officePhone);
        mobile1 = view.findViewById(R.id.mobile1);
        mobile2 = view.findViewById(R.id.mobile2);

        String emailName = email.getText().toString();
        String officePhoneNum = officePhone.getText().toString();
        String mobileNum1 = mobile1.getText().toString();
        String mobileNum2 = mobile2.getText().toString();

        officePhone.setOnClickListener(v -> call(officePhoneNum));
        mobile1.setOnClickListener(v -> call(mobileNum1));
        mobile2.setOnClickListener(v -> call(mobileNum2));
        email.setOnClickListener(v -> sendEmail(emailName));


        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, new CivilHomePage());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new CseHomePage());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new EceHomePage());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });eee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new EeeHomePage());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new MechHomePage());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        return view;
    }


    private void call(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + phone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(requireContext(), "Invalid phone number", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(String email) {
        if (email != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email));
            startActivity(intent);
        } else {
            Toast.makeText(requireContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }

}
