package com.example.guttatrener;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;

public class SecondFragment extends Fragment {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String ukeNr;
    String dagNr;
    String arrNr;

    // Text variabler
    private TextView dagenField;

    private ArrayList<String> ovelseNavnList = new ArrayList<>();
    private ArrayList<String> imagePlassList = new ArrayList<>();
    private ArrayList<String> idListen = new ArrayList<>();

    private ArrayList<Integer> tallSetList = new ArrayList<>();

    private ArrayList<Integer> tallRepsList = new ArrayList<>();

    private ArrayList<Integer> tallVektList = new ArrayList<>();


    Calendar kalenderen = Calendar.getInstance();

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
                OpenSelectorTraining();
            }
        });

        // Får fatt i verdiene ukenr og dagnr
        ukeNr = SecondFragmentArgs.fromBundle(getArguments()).getUkeNr();
        dagNr = SecondFragmentArgs.fromBundle(getArguments()).getDagNr();
        arrNr = SecondFragmentArgs.fromBundle(getArguments()).getArrNr();


        //Log.d("Brukeren", user.getPhoneNumber());
        // Setter opp fieldsene
        dagenField = view.findViewById(R.id.dagFragId);




        // Henter dagen, uken og året fra firstfragment
        hentOvelser(arrNr, ukeNr, dagNr);

        // Setter fieldsene
        dagenField.setText("Uke "+ukeNr);

       // Log.i("Uke", ukeNr);
       // Log.i("Dag", dagNr);


    }


    private void InitRecyclerView(){
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView);
        RecyclerViewAdaptder adaptder = new RecyclerViewAdaptder(ovelseNavnList, imagePlassList, idListen, tallSetList, tallRepsList, tallVektList, arrNr, ukeNr, dagNr, getContext());
        recyclerView.setAdapter(adaptder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Dialog åpner
    public void OpenSelectorTraining(){
        // Parser dataen til dialogvinduet via en bundle

        Bundle bundle = new Bundle();

        bundle.putString("aaren", arrNr);
        bundle.putString("uken", ukeNr);
        bundle.putString("dagen", dagNr);


        DialogTreningValg dialogTrening = new DialogTreningValg();
        dialogTrening.setArguments(bundle);
        dialogTrening.show(getActivity().getSupportFragmentManager(), "tagen");

    }


    // --------------------------- DATABASE FIRESTORE--------------------------------------

    // Henter valgte øvelser fra valgt dag, uke og år.
    public void hentOvelser(String arret, String uken, String dagen){
        // Sletter gamle data
        ovelseNavnList.clear();
        imagePlassList.clear();
        tallSetList.clear();
        tallRepsList.clear();
        tallVektList.clear();
        idListen.clear();

        db.collection("users").document(user.getUid()).collection(arret)
                .document(uken).collection(dagen).orderBy("dato").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document :task.getResult()){
                                //Log.d("Øvelser", (String) document.get("navn"));


                                ovelseNavnList.add(document.get("navn").toString());
                                imagePlassList.add(document.get("bildePlassering").toString());
                                idListen.add(document.getId());

                                tallSetList.add(Integer.parseInt(String.valueOf(document.get("set"))));

                                tallRepsList.add(Integer.parseInt(String.valueOf(document.get("reps"))));
                                tallVektList.add(Integer.parseInt(String.valueOf(document.get("vekt"))));

                            }
                            InitRecyclerView();
                        }
                    }
                });

    }



}