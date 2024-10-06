package com.example.sparrowcampus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class NotificationFragment extends Fragment {
    RecyclerView pdfRecycleView;
    DatabaseReference pRef;
    Query query;
    ProgressBar progressBar;
    FirebaseRecyclerAdapter<FileInModel, PDFAdapter> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        pdfRecycleView = view.findViewById(R.id.recycleView3);
        progressBar = view.findViewById(R.id.progressBar);

        pdfRecycleView.setHasFixedSize(true);
        pdfRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        pRef = FirebaseDatabase.getInstance().getReference().child("circulars");
        query = pRef.orderByKey();
        //query = pRef.orderByChild("filename");

        setupFirebaseAdapter();


        return view;
    }

    private void setupFirebaseAdapter() {
        FirebaseRecyclerOptions<FileInModel> options = new FirebaseRecyclerOptions.Builder<FileInModel>()
                .setQuery(query, FileInModel.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<FileInModel, PDFAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PDFAdapter holder, int position, @NonNull FileInModel model) {
                holder.pdfName.setText(model.getFilename());
                holder.pdfDesc.setText(model.getFileDesc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(model.getFileUrl()), "application/*");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(getActivity(), "No PDF viewer found! Please install a PDF viewer app.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public PDFAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item, parent, false);
                return new PDFAdapter(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                progressBar.setVisibility(View.GONE);
                // Notify user if no data is available
                if (getItemCount() == 0) {
                    Toast.makeText(getActivity(), "No Updates Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        pdfRecycleView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
            pdfRecycleView.getRecycledViewPool().clear();
            adapter.notifyDataSetChanged();
        }
    }

    public void handleBackPressed() {
        // Handle back press logic here if needed
    }
}
