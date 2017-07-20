package kz.kbtu.avonapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Router;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements BackButtonListener {
    private static final String EXTRA_NAME = "extra_name";

    private View rootView;
    private EditText etPhone;
    private Button buttonEnter;
    private Router router;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment getNewInstance(String name){
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_login, container, false);
            bindViews(rootView);
            Log.d("DEBUGLOGIN", getParentFragment().getClass() + "");
            router = ((RouterProvider)getParentFragment()).getRouter();
        }
        return rootView;
    }


    private void bindViews(View v){
        etPhone = (EditText)v.findViewById(R.id.et_phone_number);
        buttonEnter = (Button)v.findViewById(R.id.button_enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etPhone.equals("")){
                    String number = etPhone.getText().toString();
                    router.navigateTo(Screen.VERIFICATION_SCREEN, number);
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
