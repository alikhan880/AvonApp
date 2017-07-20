package kz.kbtu.avonapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kz.kbtu.avonapp.Interface.BackButtonListener;
import kz.kbtu.avonapp.Interface.RouterProvider;
import kz.kbtu.avonapp.R;
import kz.kbtu.avonapp.Screen;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationFragment extends Fragment implements View.OnClickListener, BackButtonListener{
    private static final String EXTRA_NAME = "extra_name";

    private EditText etPhone;
    private EditText etPassword;
    private Button buttonEnter;
    private Button buttonGetPassword;
    private View rootView;
    private String number;
    private Cicerone<Router> cicerone;
    private Router router;

    public VerificationFragment() {
        // Required empty public constructor
    }

    public static VerificationFragment getNewInstance(String name){
        VerificationFragment fragment = new VerificationFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_verification, container, false);
            initCicerone();
            bindViews(rootView);
            Bundle bundle = getArguments();
            number = bundle.getString("number");
            etPhone.setText(number);
        }
        return rootView;
    }

    private void bindViews(View v){
        etPhone = v.findViewById(R.id.et_phone_number);
        etPassword = v.findViewById(R.id.et_password);
        buttonEnter = v.findViewById(R.id.button_enter);
        buttonGetPassword = v.findViewById(R.id.button_get_password);
        buttonEnter.setOnClickListener(this);
        buttonGetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_enter:
                Log.d("DEBUG", "Sending result from verification");
                router.backTo(Screen.LOGIN_SCREEN);
                router.exitWithResult(200, null);
                break;
            case R.id.button_get_password:
                Toast.makeText(getContext(), "Get password", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initCicerone(){
        Log.d("DEBUG2", getParentFragment().getClass() + "");
        router = ((RouterProvider)getParentFragment()).getRouter();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
