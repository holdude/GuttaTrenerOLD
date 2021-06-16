package com.example.guttatrener;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FirstFragment extends Fragment {

    // Datafelt
    private TextView ukeNr;
    private TextView dag1;
    private TextView dag2;
    private TextView dag3;
    private TextView dag4;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






        ukeNr = view.findViewById(R.id.idUkenr);
        dag1 = view.findViewById(R.id.dag1);
        dag2 = view.findViewById(R.id.dag2);
        dag3 = view.findViewById(R.id.dag3);
        dag4 = view.findViewById(R.id.dag4);

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
    }

    // Funksjon for å endre fragment til dag
    public void endreTilDag(String dagen, View view){

        // Finne uken
        CharSequence ukeTallet = ukeNr.getText();

        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(dagen.toString(),ukeTallet.toString());
        Navigation.findNavController(view).navigate(action);

    }
}