package com.example.guttatrener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    // Text variabler
    private TextView dagenField;

    private ArrayList<String> ovelseNavnList = new ArrayList<>();
    private ArrayList<String> imagePlassList = new ArrayList<>();
    private ArrayList<String> tekstSetList = new ArrayList<>();
    private ArrayList<Integer> tallSetList = new ArrayList<>();
    private ArrayList<String> tekstRepsList = new ArrayList<>();
    private ArrayList<Integer> tallRepsList = new ArrayList<>();
    private ArrayList<String> tekstVektList = new ArrayList<>();
    private ArrayList<Integer> tallVektList = new ArrayList<>();


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

        initTreningData(view);

        // Setter fieldsene
        dagenField.setText(dagNr);

       // Log.i("Uke", ukeNr);
       // Log.i("Dag", dagNr);

    }

    private void initTreningData(View view){
        ovelseNavnList.add("Benkpress");
        imagePlassList.add("https://www.sfi.no/images/trening_icon.png");
        tallSetList.add(0);
        tallRepsList.add(0);
        tallVektList.add(0);

        ovelseNavnList.add("Dog");
        imagePlassList.add("https://www.sfi.no/images/trening_icon.png");
        tallSetList.add(2);
        tallRepsList.add(3);
        tallVektList.add(30);

        InitRecyclerView(view);

    }

    private void InitRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdaptder adaptder = new RecyclerViewAdaptder(ovelseNavnList, imagePlassList, tallSetList, tallRepsList, tallVektList, getContext());
        recyclerView.setAdapter(adaptder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}