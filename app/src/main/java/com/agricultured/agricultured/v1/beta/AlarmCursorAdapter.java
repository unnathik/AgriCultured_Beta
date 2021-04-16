package com.agricultured.agricultured.v1.beta;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agricultured.agricultured.v1.beta.data.AlarmReminderContract;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class AlarmCursorAdapter extends CursorAdapter {
    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;

    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_item, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mTitleText = (TextView) view.findViewById(R.id.recycle_title_reminder);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_dt_reminder);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info);
        mActiveImage = (ImageView) view.findViewById(R.id.activeImage);

        int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
        int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;

        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setActiveImage(active);
    }

    private void setActiveImage(String active) {
        if (active.equals("true")) {
            mActiveImage.setImageResource(R.drawable.on_switch);
        } else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.off_switch);
        }
    }

    private void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if (repeat.equals("true")) {
            mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
        } else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
    }

    private void setReminderDateTime(String dateTime) {
        mDateAndTimeText.setText(dateTime);
    }

    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if (title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }
    }


}
