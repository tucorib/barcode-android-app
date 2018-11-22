package tuco.org.barcode.core;

public class BarcodeService {

    public interface BarcodeSearchCallback {

        public void startSearching();

        public void stopSearching();

        public void addBarcodeSearchItem(BarcodeSearchItem item);
    }

    public static void searchForBarcode(String barcodeRawValue, BarcodeSearchCallback callback){
        callback.startSearching();

        // TODO

        callback.stopSearching();
    }
}
