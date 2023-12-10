package com.rameshwar.theuntoldlinesadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadTextsActivity extends AppCompatActivity {

    EditText texts;
    Button uploadBtn;
    private DatabaseReference reference;
    private StorageReference storageReference;

    Dialog loadingDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_texts);

        texts = findViewById(R.id.uploadTexts);
        uploadBtn = findViewById(R.id.uploadTextsButton);


        getSupportActionBar().setTitle("Upload Texts");

        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.uploading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_round));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (texts.getText().toString().isEmpty()) {
                    texts.setError("Empty");
                    texts.requestFocus();
                } else {
                    uploadData();
                }
            }
        });

    }

    private void uploadData() {

        String name = texts.getText().toString();

        reference = reference.child("Untold").child("texts");

        String title = texts.getText().toString();
        final String uniqkey = reference.push().getKey();


     //   TextsModel textsModel = new TextsModel(title);

        reference.child(uniqkey).setValue(title).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                loadingDialogue.dismiss();
                Toast.makeText(UploadTextsActivity.this, "Upload success!", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                loadingDialogue.dismiss();
                Toast.makeText(UploadTextsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}