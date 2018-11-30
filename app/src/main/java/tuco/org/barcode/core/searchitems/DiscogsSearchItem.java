package tuco.org.barcode.core.searchitems;

import android.support.v4.app.Fragment;

import tuco.org.barcode.ui.DiscogsFragment;

public class DiscogsSearchItem implements SearchItem {

    private final int releaseId;

    public DiscogsSearchItem(int releaseId){
        this.releaseId = releaseId;
    }

    public Fragment buildFragment(){
        return DiscogsFragment.newInstance(releaseId);
    }
}
