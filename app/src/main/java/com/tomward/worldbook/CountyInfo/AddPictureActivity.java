package com.tomward.worldbook.CountyInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tomward.worldbook.R;

public class AddPictureActivity extends AppCompatActivity{
    ImageView image;
    Button choose, upload;
    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    private String countryName ="";
    private String key = "";
    private String userName = "";

    private Uri filePath;
    private static final String TAG = "AddPictureActivity";
    private static final String ADDIMAGE_URL = "https://studev.groept.be/api/a20sd101/AddImage/";

    private StorageReference storageReference;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);

        image = (ImageView)findViewById(R.id.image);
        choose = (Button)findViewById(R.id.choose);
        upload = (Button)findViewById(R.id.upload);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("Key");
        userName = extras.getString("UserName");
        countryName = extras.getString("CountryName");

        storageReference = FirebaseStorage.getInstance().getReference();
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void onBtnBackAddPicture_Clicked(View caller) {
        goToPreviousActivity();
    }

    public void onBtnChoose_Clicked(View caller) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    public void onBtnUpload_Clicked(View caller) {
        progressDialog = new ProgressDialog(AddPictureActivity.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        String url = key + System.currentTimeMillis() + "." + getFileExtension(filePath);
        StorageReference sRef = storageReference.child(key + "/" + url);

        UploadTask uploadTask = sRef.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                //adding reference to database
                StringRequest addImageRequest = new StringRequest(Request.Method.GET, ADDIMAGE_URL + url + "/" + key, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        goToPreviousActivity();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d(TAG, e.getMessage());
                        Toast.makeText(AddPictureActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(addImageRequest);
            }

        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
            }
        });
    }

    private void goToPreviousActivity() {
        Intent intent =new Intent(this, PicturesActivity.class);
        intent.putExtra("CountryName", countryName);
        intent.putExtra("Key", key);
        intent.putExtra("UserName", userName);
        startActivity(intent);
    }
}