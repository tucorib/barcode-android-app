package tuco.org.barcode.core;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;
import saschpe.discogs.Discogs;
import saschpe.discogs.model.database.Search;
import saschpe.discogs.model.release.Release;
import tuco.org.barcode.App;

public class DiscogsService {

    private static final String TAG = "DiscogsService";

    private static DiscogsService mInstance;
    private static Context mContext;

    public static synchronized DiscogsService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DiscogsService(context);
        }
        return mInstance;
    }

    private final Discogs client;

    private DiscogsService(Context context) {
        mContext = context;
        client = new Discogs(((App)context.getApplicationContext()).getAssetPropertyManager().getDiscogsUserAgent(), null, null, ((App)context.getApplicationContext()).getAssetPropertyManager().getDiscogsToken());
    }

    public void searchForBarcode(String barcode, Callback<Search> callback){
        Map<String,String> params = new HashMap<>();
        params.put("barcode", barcode);
        params.put("type", "release");

        client.getDatabase().search(params).enqueue(callback);
    }

    public void searchForRelease(int releaseId, Callback<Release> callback){
        client.getRelease().release(Integer.toString(releaseId),null).enqueue(callback);
    }
}
