package tuco.org.barcode.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import tuco.org.barcode.R;
import tuco.org.barcode.core.TucothequeService;

public class DiscogsFragment extends Fragment {

    private static final String TAG = "DiscogsFragment";

    private int id;

    public static DiscogsFragment newInstance(int id) {
        DiscogsFragment fragment = new DiscogsFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.discogs_item, container, false);

        try {
            TucothequeService.loadDiscogsItem(DiscogsFragment.this.getContext(), id, new TucothequeService.DiscogsLoadCallback() {
                @Override
                public void itemLoaded(JSONObject data){
                    try {
                        Picasso.get().load(data.getString("thumb")).into((ImageView) view.findViewById(R.id.discogs_cover));
                        ((TextView) view.findViewById(R.id.discogs_artist)).setText(data.getString("artists_sort"));
                        ((TextView) view.findViewById(R.id.discogs_album)).setText(data.getString("title"));
                        ((TextView) view.findViewById(R.id.discogs_year)).setText(data.getString("year"));
                        ((TextView) view.findViewById(R.id.discogs_misc)).setText("Misc");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return view;
    }
}
