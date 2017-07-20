package kz.kbtu.avonapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kz.kbtu.avonapp.Adapter.AdapterCatalogFragment;
import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Router;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment implements AdapterCatalogFragment.CatalogFragmentListener, BackButtonListener {
    private static final String EXTRA_NAME = "extra_name";

    private List<String> productList;
    private View rootView;
    private RecyclerView recyclerView;
    private AdapterCatalogFragment adapter;

    public CatalogFragment() {
        // Required empty public constructor
        productList = new ArrayList<>();
    }

    private void initList(){
        productList.add("Рубашки");
        productList.add("Футболки");
        productList.add("Кроссовки");
    }

    public static CatalogFragment getNewInstance(String name){
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_catalog, container, false);
            initList();
        }
        adapter = new AdapterCatalogFragment(productList, this);
        recyclerView = rootView.findViewById(R.id.recycler_catalog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void itemClicked(int position) {
        Router router = ((RouterProvider)getParentFragment()).getRouter();
        router.navigateTo(Screen.DETAIL_SCREEN);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
