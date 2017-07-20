package kz.kbtu.avonapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kz.kbtu.avonapp.R;

public class StartActivity extends AppCompatActivity {

    //Test
    boolean check = false;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(!check){
            startActivityForResult(new Intent(this, LoginActivity.class), 100);
        }
        else {
           navigateToMain();
        }
    }

    private void navigateToMain(){
        startActivity(new Intent(this, BottomNavigationActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    navigateToMain();
                }
                break;
        }
    }
}
