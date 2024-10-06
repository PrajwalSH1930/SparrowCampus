package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sparrowcampus.databinding.ActivityAdminHomePageBinding;

public class AdminHomePage extends AppCompatActivity {

    ActivityAdminHomePageBinding binding;
    TextView adminNameTextView;
    String name;
    String password;
    Long staffCount, userCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adminNameTextView = findViewById(R.id.name);
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }
        showUserData();

        replaceFragment(new AdminHomeFragment());
        binding.bottomNavigationView.setBackground(null);

        // Handle navigation item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new AdminHomeFragment());
            } else if (item.getItemId() == R.id.circular) {
                replaceFragment(new AdminCircularFragment());
            } else if (item.getItemId() == R.id.people) {
                replaceFragment(new AdminFacultyFragment());
            } else if (item.getItemId() == R.id.person) {
                replaceFragment(new AdminProfileFragment());
            } else {
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });

        // Handle button click to replace fragment
        binding.bottomAppBar.setOnClickListener(v -> {
            Fragment fragment = new MapFragment(); // Replace with your actual fragment class
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null) // Optional: Add to back stack
                    .commit();
        });

    }
    public void showUserData() {
        adminNameTextView.setText(name);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("password", password);

        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}