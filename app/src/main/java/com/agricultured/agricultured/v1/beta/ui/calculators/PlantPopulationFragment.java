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

public class PlantPopulationFragment extends Fragment {

    private PlantPopulationViewModel mViewModel;
    private EditText rowToRowSpace;
    private EditText plantToPlantSpace;
    private CardView btnPopCalculate;
    private TextView popTextView;

    public static PlantPopulationFragment newInstance() {
        return new PlantPopulationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.plant_population_fragment, container, false);

        rowToRowSpace = root.findViewById(R.id.rowToRowSpace);
        plantToPlantSpace = root.findViewById(R.id.plantToPlantSpace);
        btnPopCalculate = root.findViewById(R.id.plantPopCalculate);
        popTextView = root.findViewById(R.id.resultsPlantPop);

        btnPopCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rowToRowSpace.getText().length() == 0 || plantToPlantSpace.getText().length() == 0) {
                    Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String rowToRowSpaceString = rowToRowSpace.getText().toString();
                    double rowToRowSpaceDouble = Double.parseDouble(rowToRowSpaceString);

                    String plantToPlantSpaceString = plantToPlantSpace.getText().toString();
                    double plantToPlantSpaceDouble = Double.parseDouble(plantToPlantSpaceString);

                    double hectarePlantPop = 10000 / (rowToRowSpaceDouble * plantToPlantSpaceDouble);
                    double acrePlantPop = 4047 / (rowToRowSpaceDouble * plantToPlantSpaceDouble);

                    popTextView.setText("Result:\n" + Math.round(hectarePlantPop * 100.0) / 100.0 + "/ha " + "or " +
                            Math.round(acrePlantPop * 100.0) / 100.0 + "/a");
                }
            }
        });



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlantPopulationViewModel.class);
        // TODO: Use the ViewModel
    }

}