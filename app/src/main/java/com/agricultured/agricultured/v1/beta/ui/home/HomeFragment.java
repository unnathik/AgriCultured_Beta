package com.agricultured.agricultured.v1.beta.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.agricultured.agricultured.v1.beta.AlarmCursorAdapter;
import com.agricultured.agricultured.v1.beta.ClassifierActivity;
import com.agricultured.agricultured.v1.beta.R;
import com.agricultured.agricultured.v1.beta.RemindersActivity;
import com.agricultured.agricultured.v1.beta.ui.calculators.CalculatorsFragment;
import com.agricultured.agricultured.v1.beta.ui.weather.WeatherFragment;
import com.agricultured.agricultured.v1.beta.data.AlarmReminderContract;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1000;
    private static final int CAMERA_REQUEST_CODE = 10001;
    private ImageView imageView;
    private ListView listView;
    private ListView listViewReminders;
    AlarmCursorAdapter mCursorAdapter;

    private HomeViewModel mViewModel;
    private static final int VEHICLE_LOADER = 0;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        CardView detectionBtn = root.findViewById(R.id.detection_btn);
        CardView weatherBtn = root.findViewById(R.id.weather_btn);
        CardView reminderBtn = root.findViewById(R.id.reminder_btn);
        CardView calculatorBtn = root.findViewById(R.id.calculator_btn);

        TextView txt_user_welcome = (TextView) root.findViewById(R.id.txt_user_welcome);
        if (acct.getDisplayName() != null) {
            txt_user_welcome.setText("Hello, " + acct.getDisplayName() + "!");
        }
        else {
            txt_user_welcome.setText("Hello!");
        }

        listViewReminders = root.findViewById(R.id.listViewReminders);
        View emptyView = root.findViewById(R.id.textView);
        listViewReminders.setEmptyView(emptyView);

        mCursorAdapter = new AlarmCursorAdapter(getContext(), null);
        listViewReminders.setAdapter(mCursorAdapter);

        calculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CalculatorsFragment());
                fragmentTransaction.commit();
            }
        });

        detectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClassifierActivity.class);
                startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeatherFragment());
                fragmentTransaction.commit();
            }
        });

        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RemindersActivity.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);

        return root;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                AlarmReminderContract.AlarmReminderEntry._ID,
                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE
        };

        return new CursorLoader(getContext(),
                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}