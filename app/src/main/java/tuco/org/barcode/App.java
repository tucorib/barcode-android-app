package tuco.org.barcode;

import android.app.Application;

public class App extends Application {

    private AssetPropertyManager assetPropertyManager;

    public void onCreate() {
        super.onCreate();
        assetPropertyManager = new AssetPropertyManager(this);
    }

    public AssetPropertyManager getAssetPropertyManager() {
        return assetPropertyManager;
    }

}
