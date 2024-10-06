package com.example.sparrowcampus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sparrowcampus.databinding.ActivityHomePageBinding;

public class HomePage extends AppCompatActivity {

    ActivityHomePageBinding binding;
    TextView adminNameTextView;
    String name;
    String email;
    String phone;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adminNameTextView = findViewById(R.id.name); // Move findViewById here

        // Retrieve the Intent and extract data
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            phone = intent.getStringExtra("phone");
            password = intent.getStringExtra("password");
        }

        // Set the admin name
        showUserData();

        // Set initial fragment
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        // Handle navigation item selection
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.map) {
                replaceFragment(new MapFragment());
            } else if (item.getItemId() == R.id.people) {
                replaceFragment(new PeopleFragment());
            } else if (item.getItemId() == R.id.person) {
                replaceFragment(new PersonFragment());
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
        bundle.putString("email", email);
        bundle.putString("phone", phone);
        bundle.putString("password", password);

        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        if (currentFragment instanceof NotificationFragment) {
            // Handle back press within the fragment
            ((NotificationFragment) currentFragment).handleBackPressed();
        } else {
            super.onBackPressed();
        }
    }

}
