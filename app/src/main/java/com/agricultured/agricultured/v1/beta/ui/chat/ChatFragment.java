package com.agricultured.agricultured.v1.beta.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.agricultured.agricultured.v1.beta.R;
import com.agricultured.agricultured.v1.beta.ui.calculators.CalculatorsFragment;
import com.agricultured.agricultured.v1.beta.ui.communitychat.CommunityChatFragment;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    private CardView communityChatNav;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.chat_fragment, container, false);

        communityChatNav = root.findViewById(R.id.communityChatBtn);

        communityChatNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CommunityChatFragment());
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}