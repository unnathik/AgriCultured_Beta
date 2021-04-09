package com.agricultured.agricultured.v1.beta.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.agricultured.agricultured.v1.beta.CameraActivity;
import com.agricultured.agricultured.v1.beta.ClassifierActivity;
import com.agricultured.agricultured.v1.beta.HomeActivity;
import com.agricultured.agricultured.v1.beta.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeFragment extends Fragment {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQUEST_CODE = 10001;
    private ImageView imageView;
    private ListView listView;

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        CardView detectionBtn = root.findViewById(R.id.detection_btn);

        TextView txt_user_welcome = (TextView) root.findViewById(R.id.txt_user_welcome);
        txt_user_welcome.setText("Hello, " + acct.getDisplayName() + "!");

        detectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClassifierActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}