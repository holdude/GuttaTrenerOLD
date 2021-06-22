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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DialogTreningValg extends AppCompatDialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Lister
    private ArrayList<String> ovelseNavnList = new ArrayList<>();
    private ArrayList<String> bildePlassList = new ArrayList<>();
    String arret;
    String uken;
    String dagen;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_treningvelger, null);

        arret = String.valueOf(getArguments().get("aaren"));
        uken = String.valueOf(getArguments().get("uken"));
        dagen = String.valueOf(getArguments().get("dagen"));


        builder.setView(view)
                .setTitle("Øvelse")
                .setNegativeButton("Tilbake", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Velg", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("DAOGAGASG", "RATATATATATAT");


            }
        });
        initOvelseData(view);


        return builder.create();
    }


    // INit
    private void initOvelseData(View view){

        // henter fra databasen med map
        getTreningsOvselser(view);



    }

    private void initRecyclerViewTreningValg(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerOvelsevalg);
        RecyclerViewAdapterOvelser adaptder = new RecyclerViewAdapterOvelser(bildePlassList, ovelseNavnList, dagen, uken, arret, getActivity());
        recyclerView.setAdapter(adaptder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    // database relatert
    // henter treningsøvelser fra db
    public void getTreningsOvselser(View view){
        // Lister for getTreningsOvelse

        db.collection("treningsOkter").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                              //  Log.d("er", "ASD");
                               // Dataen
                                String ovselseNavn =  document.get("navn").toString();
                                String bildePlass = document.get("bildePlassering").toString();

                                ovelseNavnList.add(ovselseNavn);
                                bildePlassList.add(bildePlass);


                            }

                        }
                        initRecyclerViewTreningValg(view);
                    }
                });



    }
}
