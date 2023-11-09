package com.example.yamenprojet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yamenprojet.R;
import com.google.firebase.Firebase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText oppositeEditText, adjacentEditText;

    Firebase firebase;
    ImageView imageView;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oppositeEditText = findViewById(R.id.oppositeEditText);
        adjacentEditText = findViewById(R.id.adjacentEditText);
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.resultTextView);
    }



    public void calculateHypotenuse(View view) {
        double opposite = Double.parseDouble(oppositeEditText.getText().toString());
        double adjacent = Double.parseDouble(adjacentEditText.getText().toString());
        double hypotenuse = Math.sqrt(opposite * opposite + adjacent * adjacent);
        resultTextView.setText(String.valueOf(hypotenuse));
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void selectImageFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}