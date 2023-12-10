package com.rameshwar.theuntoldlinesadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadDataActivity extends AppCompatActivity {

    ImageView categoryImage,uploadImage;
  //  TextInputEditText categoryName;
    Button uploadBtn;

    private DatabaseReference reference;
    private StorageReference storageReference;
    String downloadUrl = "";
    // ProgressDialog pd;
    Dialog loadingDialogue;

    private final int REQ = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        categoryImage = findViewById(R.id.image);
      //  categoryName = findViewById(R.id.categoryName);
        uploadBtn = findViewById(R.id.addCategoryButton);
        uploadImage = findViewById(R.id.uploadImage);

        getSupportActionBar().setTitle("Upload Image");

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.uploading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_round));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

      //  pd = new ProgressDialog(this);

        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (categoryImage.getDrawable() == null) {
                        categoryImage.requestFocus();
                        Toast.makeText(UploadDataActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    } else if (bitmap == null){
                        uploadData();
                        Toast.makeText(UploadDataActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadImage();

                    }
            }
        });
    }

    private void uploadImage() {
        loadingDialogue.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;

        String name = getIntent().getStringExtra("name");

        filepath = storageReference.child("Untold").child("Category").child(name).child("data").child(finalimg + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UploadDataActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                   loadingDialogue.dismiss();
                    Toast.makeText(UploadDataActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        String name = getIntent().getStringExtra("name");

      //  String title = categoryName.getText().toString();

        reference = reference.child("Untold").child("Category").child(name).child("data");

        final String uniqkey = reference.push().getKey();




        UploadData uploadData = new UploadData(downloadUrl,uniqkey);

        reference.child(uniqkey).setValue(uploadData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loadingDialogue.dismiss();
                Toast.makeText(UploadDataActivity.this, "Upload success!", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                loadingDialogue.dismiss();
                Toast.makeText(UploadDataActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent picImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            categoryImage.setImageBitmap(bitmap);
            uploadImage.setImageBitmap(bitmap);
        }

    }
}