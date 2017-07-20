package kz.kbtu.avonapp;

import ru.terrakok.cicerone.Router;

/**
 * Created by abakh on 19-Jul-17.
 */

public class BottomNavigationPresenter {
    private Router router;

    public BottomNavigationPresenter(Router router) {
        this.router = router;
    }

    public void mainChosen(){
        router.replaceScreen(Screen.CONTAINER_MAIN);
    }

    public void favoritesChosen(){
        router.replaceScreen(Screen.CONTAINER_FAVORITES);
    }

    public void profileChosen(){
        router.replaceScreen(Screen.CONTAINER_PROFILE);
    }
}
