package com.example.sparrowcampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PeopleFragment extends Fragment {

    CardView cseCard, eceCard, eeeCard, meCard, ceCard, officeCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        cseCard = view.findViewById(R.id.cse);
        eceCard = view.findViewById(R.id.ece);
        eeeCard = view.findViewById(R.id.eee);
        meCard = view.findViewById(R.id.me);
        ceCard = view.findViewById(R.id.ce);
        officeCard = view.findViewById(R.id.office);

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
        /*eceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EceFaculty.class);
                startActivity(intent);
            }
        });

        eeeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EeeFaculty.class);
                startActivity(intent);
            }
        });

        meCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MeFaculty.class);
                startActivity(intent);
            }
        });

        officeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OfficeFaculty.class);
                startActivity(intent);
            }
        });

        ceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CivilFaculty.class);
                startActivity(intent);
            }
        });*/

        return view;
    }
}