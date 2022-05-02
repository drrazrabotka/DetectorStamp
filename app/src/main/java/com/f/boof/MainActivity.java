package com.f.boof;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.f.boof.myboof.HelpBoofCv;
import com.f.boof.utility.MyUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Button btn;
    private Button btn2;
    private Bitmap bitmap;

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn_run);


        btn.setOnClickListener(v ->{
            if (allPermissionsGranted()){
                imageOpenResult.launch("image/*");
            }else{
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });

        btn2.setOnClickListener(v -> {
            bitmap = HelpBoofCv.detectStamp(bitmap);
            Glide.with(getApplicationContext()).load(bitmap).into(img);
        });


    }

    private ActivityResultLauncher<String> imageOpenResult =
            registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>(){

                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null)
                        try (InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri)){
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            bitmap = MyUtility.scaleSize(bitmap);
                            Glide.with(getApplicationContext()).load(bitmap).into(img);

                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                }
            });

    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {
                    Toast.makeText(getApplicationContext(), "permission", Toast.LENGTH_SHORT).show();


                }
            });
}