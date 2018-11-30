package tuco.org.barcode.core;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import saschpe.discogs.model.database.Result;
import saschpe.discogs.model.database.Search;
import tuco.org.barcode.core.searchitems.DiscogsSearchItem;
import tuco.org.barcode.core.searchitems.SearchItem;

public class TucothequeService {

    private static final String TAG = "TucothequeService";

    private static final String getServiceUrl(){
        return "http://192.168.0.17:5000";
    }

    public interface BarcodeSearchCallback {

        public void startSearching();

        public void stopSearching();

        public void addResult(SearchItem item);
    }

    public static void searchForBarcode(final Context context, String barcodeRawValue, final BarcodeSearchCallback callback){
        callback.startSearching();

        DiscogsService.getInstance(context).searchForBarcode(barcodeRawValue, new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, retrofit2.Response<Search> response) {
                for(Result result : response.body().getResults()){
                    callback.addResult(new DiscogsSearchItem(result.getId()));
                }
                callback.stopSearching();
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                callback.stopSearching();
            }
        });
    }
}
