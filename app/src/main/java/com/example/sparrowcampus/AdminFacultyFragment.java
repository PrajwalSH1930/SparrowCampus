package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class AdminFacultyFragment extends Fragment {

    CardView cseCard, eceCard, eeeCard, meCard, ceCard, officeCard;
    Button newFaculty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_faculty, container, false);

        cseCard = view.findViewById(R.id.cse);
        eceCard = view.findViewById(R.id.ece);
        eeeCard = view.findViewById(R.id.eee);
        meCard = view.findViewById(R.id.me);
        ceCard = view.findViewById(R.id.ce);
        officeCard = view.findViewById(R.id.office);
        newFaculty = view.findViewById(R.id.newMember);

        cseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FragmentManager and begin a transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new CseFacultyFragment());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        ceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FragmentManager and begin a transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new CeFacultyFragment());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        eceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FragmentManager and begin a transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new EceFacultyFragment());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        eeeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FragmentManager and begin a transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new EeeFacultyFragment());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        meCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the FragmentManager and begin a transaction
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, new MeFacultyFragment());
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        newFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NewFaculty.class);
                startActivity(intent);
            }
        });

        return view;
    }
}