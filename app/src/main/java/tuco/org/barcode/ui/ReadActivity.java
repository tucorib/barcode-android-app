package tuco.org.barcode.ui;


import android.media.Image;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.util.List;

import tuco.org.barcode.R;

public class ReadActivity extends BarcodeCameraActivity {

    private static final String TAG = "ReadActivity";

    private TextureView textureView;

    private Button takePictureButton;

    protected TextureView getTextureView(){
        return textureView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read);

        textureView = (TextureView) findViewById(R.id.texture);
        takePictureButton = (Button) findViewById(R.id.btn_scan);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    public void getBarcode(FirebaseVisionBarcode barcode){
        Toast.makeText(this, barcode.getDisplayValue(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, barcode.getDisplayValue());
    }
}
