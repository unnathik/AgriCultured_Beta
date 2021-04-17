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

public class FertilizerFragment extends Fragment {

    private FertilizerViewModel mViewModel;
    private EditText nitrogenFertilizer;
    private EditText phosphorusFertilizer;
    private EditText potassiumFertilizer;
    private CardView btnFertilizerCalculate;
    private TextView pesticideCombo1TextView;
    private TextView pesticideCombo2TextView;
    private TextView pesticideCombo3TextView;
    private TextView pesticideCombo4TextView;

    public static FertilizerFragment newInstance() {
        return new FertilizerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fertilizer_fragment, container, false);

        nitrogenFertilizer = root.findViewById(R.id.nitrogenFertilizer);
        phosphorusFertilizer = root.findViewById(R.id.phosphorusFertilizer);
        potassiumFertilizer = root.findViewById(R.id.potassiumFertilizer);
        btnFertilizerCalculate = root.findViewById(R.id.fertilizerCalculate);
        pesticideCombo1TextView = root.findViewById(R.id.combination1Fertilizer);
        pesticideCombo2TextView = root.findViewById(R.id.combination2Fertilizer);
        pesticideCombo3TextView = root.findViewById(R.id.combination3Fertilizer);
        pesticideCombo4TextView = root.findViewById(R.id.combination4Fertilizer);

        btnFertilizerCalculate.setOnClickListener(view -> {
            if (nitrogenFertilizer.getText().length() == 0 ||
            phosphorusFertilizer.getText().length() == 0 ||
            potassiumFertilizer.getText().length() == 0) {
                Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                double nitrogenFertilizerDouble = Double.parseDouble(nitrogenFertilizer.getText().toString());
                double phosphorusFertilizerDouble = Double.parseDouble(phosphorusFertilizer.getText().toString());
                double potassiumFertilizerDouble = Double.parseDouble(potassiumFertilizer.getText().toString());

                //urea, DAP, MOP
                double MOPValue = (100.0 / 60.0) * potassiumFertilizerDouble;
                double DAPValue = (100.0 / 46.0) * phosphorusFertilizerDouble;
                double UreaValue = (100.0 / 46.0) * (nitrogenFertilizerDouble - (18.0 / 100.0) * DAPValue);
                pesticideCombo1TextView.setText("Combination 1:\n" + "Urea: " + Math.round(UreaValue * 100.0) / 100.0 + "kg\n"
                + "DAP: " + Math.round(DAPValue * 100.0) / 100.0 + "kg\n" + "MOP: " + Math.round(MOPValue * 100.0) / 100.0 + "kg");

                //urea, SSP, MOP
                double MOPValueCombo2 = (100.0 / 60.0) * potassiumFertilizerDouble;
                double SSPValue = (100.0 / 16.0) * phosphorusFertilizerDouble;
                double UreaValueCombo2 = (100.0 / 46.0) * nitrogenFertilizerDouble;
                pesticideCombo2TextView.setText("Combination 2:\n" + "Urea: " + Math.round(UreaValueCombo2 * 100.0) / 100.0 + "kg\n"
                        + "SSP: " + Math.round(SSPValue * 100.0) / 100.0 + "kg\n" + "MOP: " + Math.round(MOPValueCombo2 * 100.0) / 100.0 + "kg");

                //CAN, SSP, MOP
                double MOPValueCombo3 = (100.0 / 60.0) * potassiumFertilizerDouble;
                double SSPValue3 = (100.0 / 16.0) * phosphorusFertilizerDouble;
                double CANValueCombo3 = (100.0 / 26.0) * nitrogenFertilizerDouble;
                pesticideCombo3TextView.setText("Combination 3:\n" + "CAN: " + Math.round(CANValueCombo3 * 100.0) / 100.0 + "kg\n"
                        + "SSP: " + Math.round(SSPValue3 * 100.0) / 100.0 + "kg\n" + "MOP: " + Math.round(MOPValueCombo3 * 100.0) / 100.0 + "kg");

                //CAN, Rock Phosphate, MOP
                double MOPValueCombo4 = (100.0 / 60.0) * potassiumFertilizerDouble;
                double CANValueCombo4 = (100.0 / 26.0) * nitrogenFertilizerDouble;
                double RockPhosphateCombo4 = (100.0 / 20.0) * phosphorusFertilizerDouble;
                pesticideCombo4TextView.setText("Combination 4:\n" + "CAN: " + Math.round(CANValueCombo4 * 100.0) / 100.0 + "kg\n"
                        + "Rock Phosphate: " + Math.round(RockPhosphateCombo4 * 100.0) / 100.0 + "kg\n" + "MOP: " + Math.round(MOPValueCombo4 * 100.0) / 100.0 + "kg");
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FertilizerViewModel.class);
        // TODO: Use the ViewModel
    }

}