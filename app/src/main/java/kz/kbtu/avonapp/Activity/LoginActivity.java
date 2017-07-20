package kz.kbtu.avonapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import kz.kbtu.avonapp.Fragment.LoginFragment;
import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.result.ResultListener;

public class LoginActivity extends AppCompatActivity implements RouterProvider {

    private Cicerone<Router> cicerone;
    private Router router;
    private Navigator navigator;
    private NavigatorHolder navigatorHolder;


    private void initNavigator(){
        navigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.content_login_activity) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey){
                    case Screen.LOGIN_SCREEN:
                        return LoginFragment.getNewInstance();
                    default:
                        throw new RuntimeException("No such screen key");
                }
            }

            @Override
            protected void showSystemMessage(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                finish();
            }
        };
    }

    private void initCicerone(){
        cicerone = Cicerone.create();
        router = cicerone.getRouter();
        navigatorHolder = cicerone.getNavigatorHolder();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initCicerone();
        initNavigator();
        getRouter().setResultListener(200, new ResultListener() {
            @Override
            public void onResult(Object resultData) {
                Log.d("DEBUG", "resultLoginFragment");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ac_login_container);
        if(fragment != null && fragment instanceof BackButtonListener && ((BackButtonListener)fragment).onBackPressed()){
            return;
        }
        else{
            getRouter().exit();
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
