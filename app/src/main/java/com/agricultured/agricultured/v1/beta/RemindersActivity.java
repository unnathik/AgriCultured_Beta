package com.agricultured.agricultured.v1.beta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.agricultured.agricultured.v1.beta.data.AlarmReminderContract;
import com.agricultured.agricultured.v1.beta.data.AlarmReminderDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RemindersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private FloatingActionButton mAddReminderButton;
    AlarmCursorAdapter mCursorAdapter;
    AlarmReminderDbHelper alarmReminderDbHelper = new AlarmReminderDbHelper(this);
    ListView reminderListView;
    ProgressDialog prgDialog;
    TextView reminderText;

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders2);

        reminderListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        mCursorAdapter = new AlarmCursorAdapter(this, null);
        reminderListView.setAdapter(mCursorAdapter);

        reminderText = (TextView) findViewById(R.id.reminderText);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(RemindersActivity.this, NewReminderActivity.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);

                intent.setData(currentVehicleUri);
                startActivity(intent);
            }
        });

        mAddReminderButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        mAddReminderButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NewReminderActivity.class);
            startActivity(intent);
        });

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
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

        return new CursorLoader(this,
                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
        if (cursor.getCount() > 0) {
            reminderText.setVisibility(View.VISIBLE);
        } else {
            reminderText.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}