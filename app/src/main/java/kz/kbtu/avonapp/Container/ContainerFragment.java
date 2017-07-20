package kz.kbtu.avonapp.Container;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kz.kbtu.avonapp.Fragment.CatalogFragment;
import kz.kbtu.avonapp.Fragment.DetailFragment;
import kz.kbtu.avonapp.Fragment.LoginFragment;
import kz.kbtu.avonapp.Fragment.VerificationFragment;
import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.LocalCiceroneHolder;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContainerFragment extends Fragment implements RouterProvider, BackButtonListener {
    private static final String EXTRA_NAME = "fc_extra_name";
    private Navigator navigator;
    private LocalCiceroneHolder ciceroneHolder;

    public static ContainerFragment getNewInstance(String name){
        ContainerFragment fragment = new ContainerFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    private Cicerone<Router> getCicerone(){
        return ciceroneHolder.getCicerone(getContainerName());
    }

    private String getContainerName(){
        return getArguments().getString(EXTRA_NAME);
    }

    public ContainerFragment() {
        // Required empty public constructor
        ciceroneHolder = new LocalCiceroneHolder();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_container, container, false);
    }

    private Navigator getNavigator(){
        if(navigator == null){
            navigator = new SupportFragmentNavigator(getChildFragmentManager(), R.id.fc_container) {
                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey){
                        case Screen.CATALOG_SCREEN:
                            return CatalogFragment.getNewInstance(getContainerName());
                        case Screen.DETAIL_SCREEN:
                            return DetailFragment.getNewInstance(getContainerName());
                        case Screen.LOGIN_SCREEN:
                            return LoginFragment.getNewInstance(getContainerName());
                        case Screen.VERIFICATION_SCREEN:
                            return VerificationFragment.getNewInstance(getContainerName());
                        default:
                            throw new RuntimeException("Key not found");
                    }
                }

                @Override
                protected void showSystemMessage(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void exit() {
                    ((RouterProvider)getActivity()).getRouter().exit();
                }
            };
        }
        return navigator;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.fc_container);
        if(fragment != null && fragment instanceof BackButtonListener && ((BackButtonListener)fragment).onBackPressed()){
            return true;
        }
        else{
            ((RouterProvider)getActivity()).getRouter().exit();
            return true;
        }
    }

    @Override
    public Router getRouter() {
        return getCicerone().getRouter();
    }
}
