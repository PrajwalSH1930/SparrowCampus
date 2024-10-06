package com.example.sparrowcampus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class EeeFacultyFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recView,recView2;

    StaffAdapter adapter,adapter2;


    public EeeFacultyFragment() {

    }

    public static EeeFacultyFragment newInstance(String param1, String param2) {
        EeeFacultyFragment fragment = new EeeFacultyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eee_faculty, container, false);

        recView = (RecyclerView)view.findViewById(R.id.recycleView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference().child("staff").child("eee").child("teaching"), Model.class).build();

        adapter = new StaffAdapter(options);
        recView.setAdapter((adapter));

        recView2 = (RecyclerView)view.findViewById(R.id.recycleView2);
        recView2.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Model> options2 =
                new FirebaseRecyclerOptions.Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference().child("staff").child("eee").child("nonTeaching"), Model.class).build();

        adapter2 = new StaffAdapter(options2);
        recView2.setAdapter((adapter2));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.stopListening();
    }
}