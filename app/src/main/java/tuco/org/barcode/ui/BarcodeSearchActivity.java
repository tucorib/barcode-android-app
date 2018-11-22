package tuco.org.barcode.ui;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tuco.org.barcode.R;
import tuco.org.barcode.core.BarcodeSearchItem;
import tuco.org.barcode.core.BarcodeService;


public class BarcodeSearchActivity extends ListActivity implements BarcodeService.BarcodeSearchCallback {

    private static final String TAG = "BarcodeSearchActivity";

    private final List<BarcodeSearchItem> dataList = new ArrayList<BarcodeSearchItem>();

    private final BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate the layout for each list row
            if (convertView == null) {
                convertView = LayoutInflater.from(BarcodeSearchActivity.this).
                        inflate(R.layout.item_search, parent, false);
            }

            BarcodeSearchItem item = (BarcodeSearchItem) getItem(position);

            Picasso.get().load(item.imageUrl).into((ImageView) convertView.findViewById(R.id.image));
            ((TextView) convertView.findViewById(R.id.name)).setText(item.name);
            ((TextView) convertView.findViewById(R.id.description)).setText(item.description);

            return convertView;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(adapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String barcodeRawValue = intent.getStringExtra(SearchManager.QUERY);
            BarcodeService.searchForBarcode(barcodeRawValue, this);
        }

    }

    @Override
    public void startSearching() {
        dataList.clear();
    }

    @Override
    public void stopSearching() {
        // TODO
    }

    @Override
    public void addBarcodeSearchItem(BarcodeSearchItem item) {
        dataList.add(item);
        adapter.notifyDataSetChanged();
    }


}