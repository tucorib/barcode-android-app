package tuco.org.barcode.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

import tuco.org.barcode.R;
import tuco.org.barcode.core.TucothequeService;


public class SearchActivity extends FragmentActivity implements TucothequeService.BarcodeSearchCallback {

    private static final String TAG = "SearchActivity";

    private JSONArray data;

    private ProgressLinearLayout mProgressLayout;

    private ViewPager mPager;

    private SearchAdapter mPagerAdapter;

    private class SearchAdapter extends FragmentPagerAdapter{

        private JSONArray data;

        public SearchAdapter(FragmentManager manager){
            super(manager);
        }

        public void setData(JSONArray data){
            this.data = data;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(this.data == null)
                return 0;
            return this.data.length();
        }

        // Returns the fragment to display for a particular page.
        @Override
        public Fragment getItem(int position) {
            try {
                String source = this.data.getJSONObject(position).getString("source");
                switch (source) {
                    case "discogs":
                        return DiscogsFragment.newInstance(this.data.getJSONObject(position).getInt("id"));
                    default:
                        return null;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        mProgressLayout = (ProgressLinearLayout) findViewById(R.id.progress);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SearchAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        SpringDotsIndicator springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        springDotsIndicator.setViewPager(mPager);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String barcodeRawValue = intent.getStringExtra(SearchManager.QUERY);
            TucothequeService.searchForBarcode(this, barcodeRawValue, this);
        }

    }

    @Override
    public void startSearching() {
        mProgressLayout.showLoading();
    }

    @Override
    public void stopSearching() {
        mProgressLayout.showContent();
    }

    @Override
    public void setResults(JSONArray results) {
        this.mPagerAdapter.setData(results);
    }

}