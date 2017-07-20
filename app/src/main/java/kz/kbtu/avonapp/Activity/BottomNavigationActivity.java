package kz.kbtu.avonapp.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import kz.kbtu.avonapp.BottomNavigationPresenter;
import kz.kbtu.avonapp.Container.ContainerFragment;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

public class BottomNavigationActivity extends AppCompatActivity implements RouterProvider {

    private Cicerone<Router> cicerone;
    private Router router;
    private NavigatorHolder navigatorHolder;
    private BottomNavigationPresenter presenter;
    private ContainerFragment tabContainerMain;
    private ContainerFragment tabContainerFavorites;
    private ContainerFragment tabContainerProfile;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    presenter.mainChosen();
                    return true;
                case R.id.navigation_dashboard:
                    presenter.favoritesChosen();
                    return true;
                case R.id.navigation_notifications:
                    presenter.profileChosen();
                    return true;
            }
            return false;
        }

    };

    private void initCicerone(){
        cicerone = Cicerone.create();
        router = cicerone.getRouter();
        navigatorHolder = cicerone.getNavigatorHolder();
    }

    private void initDependencies(){
        presenter = new BottomNavigationPresenter(router);
    }

    private void initContainers(){
        FragmentManager fm = getSupportFragmentManager();
        tabContainerMain = (ContainerFragment)fm.findFragmentByTag("MAIN");
        if(tabContainerMain == null){
            tabContainerMain = ContainerFragment.getNewInstance("MAIN");
            fm.beginTransaction()
                    .add(R.id.ab_container, tabContainerMain, "MAIN")
                    .detach(tabContainerMain)
                    .commitNow();
        }
        tabContainerProfile = (ContainerFragment)fm.findFragmentByTag("PROFILE");
        if(tabContainerProfile == null){
            tabContainerProfile = ContainerFragment.getNewInstance("PROFILE");
            fm.beginTransaction()
                    .add(R.id.ab_container, tabContainerProfile, "PROFILE")
                    .detach(tabContainerProfile)
                    .commitNow();
        }
        tabContainerFavorites = (ContainerFragment)fm.findFragmentByTag("FAVORITES");
        if(tabContainerFavorites == null){
            tabContainerFavorites = ContainerFragment.getNewInstance("FAVORITES");
            fm.beginTransaction()
                    .add(R.id.ab_container, tabContainerFavorites, "FAVORITES")
                    .detach(tabContainerFavorites)
                    .commitNow();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initContainers();
        initCicerone();
        initDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public Router getRouter() {
        return router;
    }

    private Navigator navigator = new Navigator() {
        @Override
        public void applyCommand(Command command) {
            if(command instanceof Back){
                finish();
            }
            else if(command instanceof SystemMessage){
                Toast.makeText(BottomNavigationActivity.this, ((SystemMessage)command).getMessage(), Toast.LENGTH_SHORT).show();
            }
            else if(command instanceof Replace){
                FragmentManager fm = getSupportFragmentManager();
                switch (((Replace)command).getScreenKey()){
                    case Screen.CONTAINER_MAIN:
                        fm.beginTransaction()
                                .detach(tabContainerFavorites)
                                .detach(tabContainerProfile)
                                .attach(tabContainerMain)
                                .commitNow();
                        break;
                    case Screen.CONTAINER_FAVORITES:
                        fm.beginTransaction()
                                .detach(tabContainerMain)
                                .detach(tabContainerProfile)
                                .attach(tabContainerFavorites)
                                .commitNow();
                        break;
                    case Screen.CONTAINER_PROFILE:
                        fm.beginTransaction()
                                .detach(tabContainerMain)
                                .detach(tabContainerFavorites)
                                .attach(tabContainerProfile)
                                .commitNow();
                        break;
                }

            }
        }
    };

}
