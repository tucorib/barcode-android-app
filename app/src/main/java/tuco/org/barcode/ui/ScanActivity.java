package tuco.org.barcode.ui;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import tuco.org.barcode.R;
import tuco.org.barcode.ui.utils.BarcodeCameraActivity;

public class ScanActivity extends BarcodeCameraActivity {

    private static final String TAG = "ScanActivity";

    private TextureView textureView;

    private ImageButton takePictureButton;

    protected TextureView getTextureView(){
        return textureView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scan);

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
        Intent intent = new Intent(this, SearchActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, barcode.getRawValue());

        startActivity(intent);
    }
}
