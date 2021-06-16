package com.example.guttatrener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "sd", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Setter opp fieldsene
        dagenField = view.findViewById(R.id.dagFragId);

        // Får fatt i verdiene ukenr og dagnr
        String ukeNr = FirstFragmentArgs.fromBundle(getArguments()).getUkeNr();
        String dagNr = FirstFragmentArgs.fromBundle(getArguments()).getDagNr();


        // Setter fieldsene
        dagenField.setText(dagNr);

       // Log.i("Uke", ukeNr);
       // Log.i("Dag", dagNr);

    }



}