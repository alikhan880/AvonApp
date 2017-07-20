package kz.kbtu.avonapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements BackButtonListener{

    private static final String EXTRA_NAME = "extra_name";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String name){
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
