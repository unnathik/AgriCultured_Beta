package com.agricultured.agricultured.v1.beta.ui.communitychat;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.agricultured.agricultured.v1.beta.DiscussionActivity;
import com.agricultured.agricultured.v1.beta.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CommunityChatFragment extends Fragment {

    private CommunityChatViewModel mViewModel;
    private ListView lvDiscussionTopics;
    ArrayList<String> listOfDiscussions = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().getRoot();

    public static CommunityChatFragment newInstance() {
        return new CommunityChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.community_chat_fragment, container, false);

        lvDiscussionTopics = (ListView) root.findViewById(R.id.lvDiscussionTopics);
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listOfDiscussions);
        lvDiscussionTopics.setAdapter(arrayAdapter);

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                arrayAdapter.clear();
                arrayAdapter.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvDiscussionTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getContext(), DiscussionActivity.class);
                i.putExtra("selected_topic", ((TextView)view).getText().toString());
                i.putExtra("user_name", getUserName());
                startActivity(i);
            }
        });

        return root;
    }

    private String getUserName() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        return acct.getDisplayName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CommunityChatViewModel.class);
        // TODO: Use the ViewModel
    }

}