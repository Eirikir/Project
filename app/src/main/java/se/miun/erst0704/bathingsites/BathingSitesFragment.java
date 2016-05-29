package se.miun.erst0704.bathingsites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Erik on 29/5 029.
 */
public class BathingSitesFragment extends Fragment {
    public BathingSitesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bathing_sites_fragment, container, false);
    }


}
