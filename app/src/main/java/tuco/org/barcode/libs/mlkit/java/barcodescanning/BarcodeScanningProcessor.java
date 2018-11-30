package tuco.org.barcode.libs.mlkit.java.barcodescanning;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.IOException;
import java.util.List;

import tuco.org.barcode.libs.mlkit.common.CameraImageGraphic;
import tuco.org.barcode.libs.mlkit.common.FrameMetadata;
import tuco.org.barcode.libs.mlkit.common.GraphicOverlay;
import tuco.org.barcode.libs.mlkit.java.VisionProcessorBase;

public class BarcodeScanningProcessor extends VisionProcessorBase<List<FirebaseVisionBarcode>> {

    private static final String TAG = "BarcodeScanProcessor";

    private final FirebaseVisionBarcodeDetectorOptions options;
    private final FirebaseVisionBarcodeDetector detector;

    public interface BarcodeScanningCallback {

        void detectBarcode(FirebaseVisionBarcode barcode);

    }

    private final BarcodeScanningCallback callback;

    public BarcodeScanningProcessor(BarcodeScanningCallback callback) {
        this.options = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();
        this.detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);
        this.callback = callback;
    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: " + e);
        }
    }

    @Override
    protected Task detectInImage(FirebaseVisionImage image) {
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(
        @Nullable Bitmap originalCameraImage,
        @NonNull List<FirebaseVisionBarcode> barcodes,
        @NonNull FrameMetadata frameMetadata,
        @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        if (originalCameraImage != null) {
            CameraImageGraphic imageGraphic = new CameraImageGraphic(graphicOverlay, originalCameraImage);
            graphicOverlay.add(imageGraphic);
        }
        for (int i = 0; i < barcodes.size(); ++i) {
            FirebaseVisionBarcode barcode = barcodes.get(i);
            //BarcodeGraphic barcodeGraphic = new BarcodeGraphic(graphicOverlay, barcode);
            //graphicOverlay.add(barcodeGraphic);
            this.callback.detectBarcode(barcode);
        }
        graphicOverlay.postInvalidate();
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Barcode detection failed " + e);
    }
}