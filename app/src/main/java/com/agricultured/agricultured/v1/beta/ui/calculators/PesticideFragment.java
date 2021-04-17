package com.agricultured.agricultured.v1.beta.ui.calculators;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agricultured.agricultured.v1.beta.R;

public class PesticideFragment extends Fragment {

    private PesticideViewModel mViewModel;
    private EditText activeIngredient;
    private EditText recommendedQuantity;
    private EditText tankVolume;
    private CardView btnPesticideCalculate;
    private TextView pesticideTextView;

    public static PesticideFragment newInstance() {
        return new PesticideFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pesticide_fragment, container, false);

        activeIngredient = root.findViewById(R.id.activeIngredient);
        recommendedQuantity = root.findViewById(R.id.recommendedQuantity);
        tankVolume = root.findViewById(R.id.tankVolume);
        btnPesticideCalculate = root.findViewById(R.id.pesticideCalculate);
        pesticideTextView = root.findViewById(R.id.resultsPesticide);

        btnPesticideCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeIngredient.getText().length() == 0 || recommendedQuantity.getText().length() == 0
                || tankVolume.getText().length() == 0) {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String activeIngredientString = activeIngredient.getText().toString();
                    double activeIngredientDouble = Double.parseDouble(activeIngredientString);

                    String recommendedQuantityString = recommendedQuantity.getText().toString();
                    double recommendedQuantityDouble = Double.parseDouble(recommendedQuantityString);

                    String tankVolumeString = tankVolume.getText().toString();
                    double tankVolumeDouble = Double.parseDouble(tankVolumeString);

                    double pesticideQuantity = ((tankVolumeDouble * recommendedQuantityDouble) / activeIngredientDouble) * 100;

                    pesticideTextView.setText("Result:\n" + Math.round(pesticideQuantity * 100.0) / 100.0 + "ml " + "or " +
                            Math.round(pesticideQuantity * 100.0) / 100.0 + "g");
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PesticideViewModel.class);
        // TODO: Use the ViewModel
    }

}