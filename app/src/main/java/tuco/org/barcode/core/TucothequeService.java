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

import tuco.org.barcode.ui.DiscogsFragment;

public class TucothequeService {

    private static final String TAG = "TucothequeService";

    private static final String getServiceUrl(){
        return "http://192.168.0.17:5000";
    }

    public interface BarcodeSearchCallback {

        public void startSearching();

        public void stopSearching();

        public void setResults(JSONArray results);
    }

    public interface DiscogsLoadCallback {

        public void itemLoaded(JSONObject data);

    }

    public static void searchForBarcode(final Context context, String barcodeRawValue, final BarcodeSearchCallback callback){
        callback.startSearching();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            Request.Method.GET,
                getServiceUrl() + "/barcode/" + barcodeRawValue,
            null,
            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        callback.setResults(results);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    finally {
                        callback.stopSearching();
                    }
                }

            },
            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.getMessage(), error);
                    callback.stopSearching();
                }
            }
        );
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public static void loadDiscogsItem(Context context, final int id, final DiscogsLoadCallback callback) throws JSONException {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getServiceUrl() + "/discogs/release/" + id,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.itemLoaded(response);
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
                }
        );
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
