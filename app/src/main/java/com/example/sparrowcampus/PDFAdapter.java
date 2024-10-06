package com.example.sparrowcampus;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PDFAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    private final Context context;
    TextView pdfName, pdfDesc;

    public PDFAdapter(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        pdfName = itemView.findViewById(R.id.pdfName);
        pdfDesc = itemView.findViewById(R.id.pdfDesc);
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
