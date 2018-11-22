package tuco.org.barcode.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import tuco.org.barcode.R;

public class ReadActivity extends BarcodeCameraActivity {

    private static final String TAG = "ReadActivity";

    private TextureView textureView;

    private ImageButton takePictureButton;

    protected TextureView getTextureView(){
        return textureView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read);

        textureView = (TextureView) findViewById(R.id.texture);
        takePictureButton = (ImageButton) findViewById(R.id.btn_scan);
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
