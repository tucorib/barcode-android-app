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

import java.util.ArrayList;
import java.util.List;

import tuco.org.barcode.R;
import tuco.org.barcode.core.TucothequeService;
import tuco.org.barcode.core.searchitems.SearchItem;


public class SearchActivity extends FragmentActivity implements TucothequeService.BarcodeSearchCallback {

    private static final String TAG = "SearchActivity";

    private ProgressLinearLayout mProgressLayout;

    private ViewPager mPager;

    private SearchAdapter mPagerAdapter;

    private class SearchAdapter extends FragmentPagerAdapter{

        private final List<SearchItem> data;

        public SearchAdapter(FragmentManager manager){
            super(manager);
            data = new ArrayList<>();
        }

        public void addItem(SearchItem item){
            this.data.add(item);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        // Returns the fragment to display for a particular page.
        @Override
        public Fragment getItem(int position) {
            return this.data.get(position).buildFragment();
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
    public void addResult(SearchItem item){
        this.mPagerAdapter.addItem(item);
    }

}