package com.example.guttatrener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    // Text variabler
    private TextView dagenField;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setter opp fieldsene
        dagenField = view.findViewById(R.id.DagFragId);

        // Får fatt i verdiene ukenr og dagnr
        String ukeNr = FirstFragmentArgs.fromBundle(getArguments()).getUkeNr();
        String dagNr = FirstFragmentArgs.fromBundle(getArguments()).getDagNr();


        // Setter fieldsene
        dagenField.setText(dagNr);

       // Log.i("Uke", ukeNr);
       // Log.i("Dag", dagNr);

        // Går tilbake til hovedsiden
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }



}