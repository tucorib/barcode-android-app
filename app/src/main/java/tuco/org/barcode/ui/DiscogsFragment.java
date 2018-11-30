package tuco.org.barcode.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saschpe.discogs.model.release.Artist;
import saschpe.discogs.model.release.Release;
import tuco.org.barcode.R;
import tuco.org.barcode.core.DiscogsService;

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
        final View view = inflater.inflate(R.layout.search_item_discogs, container, false);

        DiscogsService.getInstance(DiscogsFragment.this.getContext()).searchForRelease(id, new Callback<Release>() {
            @Override
            public void onResponse(Call<Release> call, Response<Release> response) {
                Picasso.get().load(response.body().getThumb()).into((ImageView) view.findViewById(R.id.discogs_cover));
                ((TextView) view.findViewById(R.id.discogs_artist)).setText(getArtistLabel(response.body().getArtists()));
                ((TextView) view.findViewById(R.id.discogs_album)).setText(response.body().getTitle());
                ((TextView) view.findViewById(R.id.discogs_year)).setText(Integer.toString(response.body().getYear()));
                ((TextView) view.findViewById(R.id.discogs_misc)).setText("Misc");
            }

            @Override
            public void onFailure(Call<Release> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

        return view;
    }

    private String getArtistLabel(List<Artist> artists){
        List<String> artistNames = new ArrayList<>();
        for(Artist artist: artists){
            artistNames.add(artist.getName());
        }
        return TextUtils.join(", ", artistNames);
    }
}
