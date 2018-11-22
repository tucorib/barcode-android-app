package tuco.org.barcode.ui;

import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;


public abstract class BarcodeCameraActivity extends CameraActivity {

    private static final String TAG = "BarcodeCameraActivity";

    /**
     * Firebase barcode detector options.
     */
    private static final FirebaseVisionBarcodeDetectorOptions DETECTOR_OPTIONS;

    private static final FirebaseVisionBarcodeDetector DETECTOR;

    static {
        DETECTOR_OPTIONS = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();
        DETECTOR = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(DETECTOR_OPTIONS);
    }

    protected void onPictureTaken(Image image, int rotation){
        FirebaseVisionImage fImage = FirebaseVisionImage.fromMediaImage(image, rotation);
        Task<List<FirebaseVisionBarcode>> result = DETECTOR.detectInImage(fImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {

                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        // Task completed successfully
                        for (FirebaseVisionBarcode barcode : barcodes) {
                            getBarcode(barcode);
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e(TAG, e.getMessage(), e);
                    }

                });
    }

    protected abstract void getBarcode(FirebaseVisionBarcode barcode);
}
