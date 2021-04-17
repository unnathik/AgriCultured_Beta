package com.agricultured.agricultured.v1.beta;

import android.graphics.Typeface;
import android.location.Location;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID = "4a8018b7b1640836e960924451e3d0b4";

    public static void setSpanString(String welcome, String name, TextView txt_user) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(welcome);
        SpannableString spannableString = new SpannableString(name);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan,0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        txt_user.setText(builder, TextView.BufferType.SPANNABLE);
    }

    public static String convertUnixToDate(int dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm, EEE");
        String formatted = sdf.format(date);
        return formatted;
    }
}
