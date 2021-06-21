package com.example.guttatrener;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;

public class SecondFragment extends Fragment {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

    FirebaseFirestore db = FirebaseFirestore.getInstance();
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


        //Log.d("Brukeren", user.getPhoneNumber());
        // Setter opp fieldsene
        dagenField = view.findViewById(R.id.dagFragId);

        // Får fatt i verdiene ukenr og dagnr
        String ukeNr = FirstFragmentArgs.fromBundle(getArguments()).getUkeNr();
        String dagNr = FirstFragmentArgs.fromBundle(getArguments()).getDagNr();

        // Henter dataen
        getDagen(dagNr);


        initTreningData(view);

        // Setter fieldsene
        dagenField.setText(ukeNr);

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

    // Dialog åpner
    public void OpenSelectorTraining(){
        DialogTreningValg dialogTrening = new DialogTreningValg();
        dialogTrening.show(getActivity().getSupportFragmentManager(), "tagen");

    }


    // logget inn/ ikkje logget in
    public void logInn(FirebaseUser currentUser, View View){

        // Sjekker om bruker er null
        //Log.d("NOEAD", currentUser.getEmail());

        if (user == null) {
            // bruker ikkje logget inn
            Navigation.findNavController(View).navigate(FirstFragmentDirections.actionFirstFragmentToLogin());
        } else {
            // Logget inn allerede
            Log.d("Brukeren", user.getDisplayName());
        }

    }


    // --------------------------- DATABASE FIRESTORE--------------------------------------
    // Henter alltid default fra dagen/uken i dag
    public void getDagen(String dagen){
        db.collection("users").document(user.getUid()).collection(String.valueOf(kalenderen.get(Calendar.YEAR)))
                .document(String.valueOf(Calendar.WEEK_OF_YEAR)).collection(dagen).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("DBEN", "tasken e null men success");
                            if(task.getResult().isEmpty()){
                                Log.d("DBAN", "Tasken e tom");
                                // Ikkje gjør mer, vent på data fra databasen
                            } else {
                                Log.d("DBEN", "tasken e ikkje tom");
                                // legg inn dataen for løkke og du vet
                            }

                        } else {
                            Log.d("DBEN", "Ikkje success");
                        }
                    }
                });

    }

}