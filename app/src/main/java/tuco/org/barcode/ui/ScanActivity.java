package tuco.org.barcode.ui;


import android.app.SearchManager;
import android.content.Intent;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import tuco.org.barcode.ui.utils.BarcodeCameraActivity;

public class ScanActivity extends BarcodeCameraActivity {

    private static final String TAG = "ScanActivity";

    public void detectBarcode(FirebaseVisionBarcode barcode){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, barcode.getRawValue());

        startActivity(intent);
    }
}
