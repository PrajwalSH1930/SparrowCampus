package com.example.sparrowcampus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class StaffAdapter extends FirebaseRecyclerAdapter<Model, StaffAdapter.StaffViewHolder> {

    public StaffAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StaffViewHolder holder, int position, @NonNull Model model) {
        holder.name.setText(model.getFullName());
        holder.des.setText(model.getDes());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        Glide.with(holder.profileImg.getContext()).load(model.getPurl()).into(holder.profileImg);

        LocalDate currentDate = LocalDate.now();
        String dayOfWeek = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        LocalTime currentTime = LocalTime.now();
        LocalTime collegeStart = LocalTime.of(9, 0);
        LocalTime collegeEnd = LocalTime.of(17,0);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("staffNumbers");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean present = snapshot.child(model.getPhone()).child("avail").getValue(Boolean.class);
                if (present != null) {
                    if (present && !currentTime.isAfter(collegeEnd) && !currentTime.isBefore(collegeStart) && !dayOfWeek.equals("Sunday")) {
                        holder.isOnline.setBackgroundResource(R.drawable.is_online_bg);  // Use setBackgroundResource() here
                    } else if(!present && !currentTime.isAfter(collegeEnd) && !currentTime.isBefore(collegeStart) && !dayOfWeek.equals("Sunday")) {
                        holder.isOnline.setBackgroundResource(R.drawable.is_offline_bg);
                    } else{
                        holder.isOnline.setBackgroundResource(R.drawable.is_closed_bg);
                    }
                } else {
                    holder.isOnline.setBackgroundColor(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });

        holder.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.wrapper, new DescFragment(model.getAddress(), model.getDes(), model.getEmail(), model.getExp(), model.getFullName(), model.getPhone(), model.getPurl(), model.getQual(), model.getSpecial(), model.iszAvail()))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        return new StaffViewHolder(view);
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileImg, isOnline;
        TextView name, des, phone, email;
        CardView profileCard;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profileImg);
            name = itemView.findViewById(R.id.name);
            des = itemView.findViewById(R.id.des);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            profileCard = itemView.findViewById(R.id.profileCard);
            isOnline = itemView.findViewById(R.id.isOnline);
        }
    }
}
