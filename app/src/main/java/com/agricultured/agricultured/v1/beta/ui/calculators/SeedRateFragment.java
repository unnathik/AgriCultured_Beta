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

public class SeedRateFragment extends Fragment {

    private SeedRateViewModel mViewModel;
    private EditText plantPopulation;
    private EditText germinationPercentage;
    private EditText seedsPerHill;
    private EditText grainWeight1000;
    private CardView seedRateCalculate;
    private TextView resultsSeedRate;

    public static SeedRateFragment newInstance() {
        return new SeedRateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.seed_rate_fragment, container, false);

        plantPopulation = root.findViewById(R.id.plantPopulation);
        germinationPercentage = root.findViewById(R.id.germinationPercentage);
        seedsPerHill = root.findViewById(R.id.seedsPerHill);
        grainWeight1000 = root.findViewById(R.id.grainWeight1000);
        seedRateCalculate = root.findViewById(R.id.seedRateCalculate);
        resultsSeedRate = root.findViewById(R.id.resultsSeedRate);

        seedRateCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plantPopulation.getText().length() == 0 || germinationPercentage.getText().length() == 0
                || seedsPerHill.getText().length() == 0 || grainWeight1000.getText().length() == 0) {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(germinationPercentage.getText().toString()) < 60) {
                    Toast.makeText(getContext(), "Germination percentage must be at least 60", Toast.LENGTH_SHORT).show();
                }
                else {
                    String plantPopulationString = plantPopulation.getText().toString();
                    double plantPopulationDouble = Double.parseDouble(plantPopulationString);

                    String germinationPercentageString = germinationPercentage.getText().toString();
                    double germinationPercentageDouble = Double.parseDouble(germinationPercentageString);

                    String seedsPerHillString = seedsPerHill.getText().toString();
                    double seedsPerHillDouble = Double.parseDouble(seedsPerHillString);

                    String grainWeight1000String = grainWeight1000.getText().toString();
                    double grainWeight1000Double = Double.parseDouble(grainWeight1000String);

                    double kgSeedRate = (plantPopulationDouble * seedsPerHillDouble * grainWeight1000Double* 100) / (germinationPercentageDouble * 1000 * 1000);
                    double lbsSeedRate = (Math.round(kgSeedRate * 100.0) / 100.0) * 2.205;

                    resultsSeedRate.setText("Result:\n" + Math.round(kgSeedRate * 100.0) / 100.0 + "kg/ha" + " or " +
                            Math.round(lbsSeedRate * 100.0) / 100.0 + "lbs/ha");
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SeedRateViewModel.class);
        // TODO: Use the ViewModel
    }

}