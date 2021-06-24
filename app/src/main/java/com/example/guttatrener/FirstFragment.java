package com.example.guttatrener;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // Datafelt
    private TextView ukeNr;
    private TextView arrNr;
    private TextView dag1;
    private TextView dag2;
    private TextView dag3;
    private TextView dag4;
    private Button nextKnapp;





    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.signOut();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        logInn(currentUser, getView());

        if (currentUser != null){
            getInitData();
        }


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ukeNr = view.findViewById(R.id.idUkenr);
        arrNr = view.findViewById(R.id.idArret);

        dag1 = view.findViewById(R.id.dag1);
        dag2 = view.findViewById(R.id.dag2);
        dag3 = view.findViewById(R.id.dag3);
        dag4 = view.findViewById(R.id.dag4);
        nextKnapp = view.findViewById(R.id.nextKnapp);




        // logg ut knapp
        nextKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Navigation.findNavController(getView()).navigate(FirstFragmentDirections.actionFirstFragmentToLogin());

            }
        });

        view.findViewById(R.id.cardDag1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Finne dagen
                CharSequence dagen = dag1.getText();

                // Kjører funksjon endrer dag
                endreTilDag(dagen.toString(), view);
            }
        });

        // Knapp for dag 2
        view.findViewById(R.id.cardDag2).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                // Finne dagen
                CharSequence dagen = dag2.getText();

                // Kjører funksjon endrer dag
                endreTilDag(dagen.toString(), view);


            }
        });

        // Knapp for dag 3
        view.findViewById(R.id.cardDag3).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                // Finne dagen
                CharSequence dagen = dag3.getText();

                // Kjører funksjon endrer dag
                endreTilDag(dagen.toString(), view);

            }
        });

        // Knapp for dag 4
        view.findViewById(R.id.cardDag4).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // Finne dagen
                CharSequence dagen = dag4.getText();

                // Kjører funksjon endrer dag
                endreTilDag(dagen.toString(), view);


            }
        });

        // Knapp tilbake
        view.findViewById(R.id.knappTilbake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endreUke(false);
            }
        });

        view.findViewById(R.id.knappFram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endreUke(true);
            }
        });
    }

    // Funksjon for å endre fragment til dag fragmentet (Secondfragment)
    public void endreTilDag(String dagen, View view){

        // Finne uken
        CharSequence ukeTallet = ukeNr.getText();
        // Fjerner alt i stringen utenom tallet
        String ukeString = ukeTallet.toString().replaceAll("Uke ", "");

        // Finner året
        CharSequence arrTallet = arrNr.getText();

        // Fjerner alt i stringen dagen utenom tallet
        String dagString = dagen.replaceAll("Dag ", "");

        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(dagString, ukeString, arrTallet.toString());
        Navigation.findNavController(view).navigate(action);

    }

    // Endrer uken fram eller tilbake
    public void endreUke(boolean fram){
        // Fjerner uke fra stringen ukeNr
        int uketallet = Integer.parseInt(ukeNr.getText().toString().replaceAll("Uke ", ""));

        // Finner året
        int arrTallet = Integer.parseInt(arrNr.getText().toString());

        // Hvis fram er true gå til neste uke
        if(fram){
            // Gå fram
            // Sjekker om det er nytt år som skal velges fram
            if(uketallet == 52){
                // Sett nytt år fram
                // Sett uken til 1 og år +1
                dbNyUkeAar(1, arrTallet+1);

            } else{
                // Ikke sett nytt år
                // Endre uken til uke +1
                dbNyUkeAar(uketallet+1, arrTallet);
            }


        } else{
            // Gå tilbake
            //Sjekker om det er nytt år som skal velges
            if(uketallet == 1){
                // Sett nytt år tilbake
                // Sett uken til 52 og år -1
                dbNyUkeAar(52, arrTallet-1);
            } else{
                // Ikke sett nytt år
                // Endre uken til uke -1
                dbNyUkeAar(uketallet-1, arrTallet);
            }
        }
    }

    // db setning for nyttår/uke
    public void dbNyUkeAar(int uke, int aar){
        String ukeS = String.valueOf(uke);
        String aarS = String.valueOf(aar);

        // Endrer valgt uke i db
        db.collection("users").document(user.getUid()).update(
                "ukeValgt", uke,
                "aarValgt",aar
        );

        // Endrer fragment dataen. Trenger ikkje engang db innlegging, den taes automatisk ved klikk på dag med data fra uke og år feltene
        ukeNr.setText("Uke "+ukeS);
        arrNr.setText(aarS);


    }

    // logget inn/ ikkje logget in
    public void logInn(FirebaseUser currentUser, View View){

        // Sjekker om bruker er null
        //Log.d("NOEAD", currentUser.getEmail());

        if (currentUser == null) {
            // bruker ikkje logget inn
            Navigation.findNavController(View).navigate(FirstFragmentDirections.actionFirstFragmentToLogin());
        } else {
            // Logget inn allerede

        }

    }

    // Finn valgt år/uke
    public void getInitData(){
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // Sjekk om første gang, hvis ikkje kjør
                if(task.getResult() != null){
                    ukeNr.setText("Uke "+task.getResult().get("ukeValgt").toString());
                    arrNr.setText(task.getResult().get("aarValgt").toString());

                }


            }
            });
        }

}