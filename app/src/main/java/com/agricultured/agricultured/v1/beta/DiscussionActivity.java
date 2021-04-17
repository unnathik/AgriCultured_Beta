package com.agricultured.agricultured.v1.beta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiscussionActivity extends AppCompatActivity {
    Button btnSendMsg;
    EditText etMsg;
    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    Toolbar toolbar;

    String userName, selectedTopic, user_msg_key;

    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        btnSendMsg = (Button) findViewById(R.id.buttonSendMessage);
        etMsg = (EditText) findViewById(R.id.editTextMessage);
        lvDiscussion = (ListView) findViewById(R.id.lvDiscussion);
        toolbar = findViewById(R.id.toolbar3);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listConversation);
        lvDiscussion.setAdapter(arrayAdapter);

        userName = getIntent().getExtras().get("user_name").toString();
        selectedTopic = getIntent().getExtras().get("selected_topic").toString();
        setTitle(selectedTopic);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(selectedTopic);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_arrow_back_ios_24);


        dbr = FirebaseDatabase.getInstance().getReference().child(selectedTopic);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        btnSendMsg.setOnClickListener(view -> {
            if (etMsg.getText().length() == 0) {
                Toast.makeText(DiscussionActivity.this, "Please enter a message.", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);

                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", etMsg.getText().toString());
                map2.put("user", userName);
                map2.put("uid", acct.getId().toString());
                dbr2.updateChildren(map2);

                etMsg.setText("");
            }
        });

        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                updateConversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateConversation(DataSnapshot dataSnapshot) {
        String msg, user, uid, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            msg = (String) ((DataSnapshot)i.next()).getValue();
            uid = (String) ((DataSnapshot)i.next()).getValue();
            user = (String) ((DataSnapshot)i.next()).getValue();

            Log.d("User", user);
            Log.d("uid", uid);

            conversation = user + ": " + msg;
            arrayAdapter.insert(conversation, 0);

            arrayAdapter.notifyDataSetChanged();
        }
    }
}