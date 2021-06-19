package com.example.guttatrener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialogTreningValg extends AppCompatDialogFragment {

    // Lister
    private ArrayList<String> ovelseNavnList = new ArrayList<>();
    private ArrayList<String> bildePlassList = new ArrayList<>();


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_treningvelger, null);

        builder.setView(view)
                .setTitle("Øvelse")
                .setNegativeButton("Tilbake", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Velg", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        initOvelseData(view);


        return builder.create();
    }


    // INit
    private void initOvelseData(View view){
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        ovelseNavnList.add("Benkpress");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Pull ups");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");

        ovelseNavnList.add("Armhevinger");
        bildePlassList.add("https://www.sfi.no/images/trening_icon.png");
        initRecyclerViewTreningValg(view);

    }

    private void initRecyclerViewTreningValg(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerOvelsevalg);
        RecyclerViewAdapterOvelser adaptder = new RecyclerViewAdapterOvelser(bildePlassList, ovelseNavnList, getActivity());
        recyclerView.setAdapter(adaptder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
