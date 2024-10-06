package com.example.sparrowcampus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class NewFaculty extends AppCompatActivity {

    FloatingActionButton backButton;
    ImageView uploadImage;
    Button addFaculty;
    EditText fullName, qual, email, exp, phone, special, address;
    Uri imageUri;
    Bitmap bitmap;
    Spinner department, des, cat;
    String deptName, desName, catName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_faculty);

        backButton = findViewById(R.id.backButton);
        uploadImage = findViewById(R.id.uploadImage);
        addFaculty = findViewById(R.id.addFaculty);
        fullName = findViewById(R.id.fullName);
        qual = findViewById(R.id.qual);
        email = findViewById(R.id.email);
        exp = findViewById(R.id.exp);
        phone = findViewById(R.id.phone);
        special = findViewById(R.id.special);
        address = findViewById(R.id.address);

        department = findViewById(R.id.my_spinner);
        cat = findViewById(R.id.cat);
        des = findViewById(R.id.des);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dept_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.fac_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.des_list, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        department.setAdapter(adapter);
        cat.setAdapter(adapter2);
        des.setAdapter(adapter3);

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // Display a toast with the selected item
                Toast.makeText(NewFaculty.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Alternatively, you can get the selected item position
                int selectedPosition = parentView.getSelectedItemPosition();
                //Toast.makeText(NewFaculty.this, "Selected Position: " + selectedPosition, Toast.LENGTH_SHORT).show();

                // Handle the selected item here
                deptName = handleSelectedItem(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // Display a toast with the selected item
                Toast.makeText(NewFaculty.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Alternatively, you can get the selected item position
                int selectedPosition = parentView.getSelectedItemPosition();
                //Toast.makeText(NewFaculty.this, "Selected Position: " + selectedPosition, Toast.LENGTH_SHORT).show();

                // Handle the selected item here
                catName = handleSelectedCatItem(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        des.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // Display a toast with the selected item
                Toast.makeText(NewFaculty.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Alternatively, you can get the selected item position
                int selectedPosition = parentView.getSelectedItemPosition();
                //Toast.makeText(NewFaculty.this, "Selected Position: " + selectedPosition, Toast.LENGTH_SHORT).show();

                // Handle the selected item here
                desName = handleSelectedDesItem(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button click
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(NewFaculty.this)
                        .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Log.d("NewFaculty", "Permission Granted");
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_PICK);
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Log.d("NewFaculty", "Permission Denied");
                                Toast.makeText(NewFaculty.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest request, PermissionToken token) {
                                Log.d("NewFaculty", "Permission Rationale Should Be Shown");
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        addFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });
    }

    private String handleSelectedDesItem(String selectedItem) {
        return selectedItem;
    }

    private String handleSelectedCatItem(String selectedItem) {
        String temp = "";
        if (selectedItem.equals("Teaching"))
            temp = "teaching";
        else if (selectedItem.equals("Non Teaching"))
            temp = "nonTeaching";
        return temp;
    }

    private void uploadToFirebase() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading");
        dialog.show();

        StorageReference uploader = FirebaseStorage.getInstance().getReference("Image" + new Random().nextInt(100));
        uploader.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("staff")
                                .child(deptName)
                                .child(catName);

                        DatabaseReference root2 = db.getReference("staffNumbers");


                        NewFacultyDataHolder obj = new NewFacultyDataHolder(
                                address.getText().toString(),
                                desName,
                                email.getText().toString(),
                                exp.getText().toString(),
                                fullName.getText().toString(),
                                phone.getText().toString(),
                                uri.toString(),
                                qual.getText().toString(),
                                special.getText().toString(),
                                true
                        );

                        root.child(String.valueOf(new Random().nextInt(100)+10)).setValue(obj);
                        root2.child(phone.getText().toString()).child("avail").setValue(true);

                        fullName.setText("");
                        //dept.setText(""); // This line is commented out to avoid NullPointerException
                        qual.setText("");
                        email.setText("");
                        exp.setText("");
                        phone.setText("");
                        special.setText("");
                        address.setText("");
                        uploadImage.setImageResource(R.drawable.upload);

                        Toast.makeText(NewFaculty.this, "New Faculty Successfully Added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                dialog.setMessage("Uploaded: " + (int) percent + "%");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                uploadImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String handleSelectedItem(String selectedItem) {
        // Do something with the selected item
        String temp = "";
        if (selectedItem.equals("Civil Engineering"))
            temp = "civil";
        else if (selectedItem.equals("Computer Science and Engineering"))
            temp = "cse";
        else if (selectedItem.equals("Electronics and Communication Engineering"))
            temp = "ece";
        else if (selectedItem.equals("Electrical and Electronics Engineering"))
            temp = "eee";
        else if (selectedItem.equals("Mechanical Engineering"))
            temp = "mech";
        // For example, update UI, make a network request, etc.
        return temp;
    }
}
