package com.example.guttatrener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    // Database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Calendar kalenderen = Calendar.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        Log.d("adasdsadad","mordaidsd");
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        logInn(user);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("837754166267-it39ni7tq05958d217r5o77irp69is67.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Bygger google_signin
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.google_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logg inn
                signIn();
            }
        });

        super.onViewCreated(view, savedInstanceState);



    }

    // Logger inn med google
    public void signIn(){
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                // funker
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Log.d("FIREBASEEN", account.getFamilyName());

            }catch (ApiException e){

            }
        }
    }

    public void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sjekker om ny bruker
                            boolean nyBruker = task.getResult().getAdditionalUserInfo().isNewUser();

                            //Log.d("SANTELLERUSANT", String.valueOf(nyBruker));
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(nyBruker){
                                firstTimeUserGoogle(user);
                            }


                            endreFragment();
                        } else {
                            // FEIL
                            Log.d("GOOGLEL", "Feil cred");
                        }

                    }
                });

    }

    public void endreFragment(){
        Navigation.findNavController(getView()).navigate(LoginDirections.actionLoginToFirstFragment(null, null));

    }

    // Kjøres hvis førstegangs bruker med google
    public void firstTimeUserGoogle(FirebaseUser user){
        // lager map
        Map<String, Object> brukeren = new HashMap<>();
        brukeren.put("uid", user.getUid());
        // Finner ukenr
        int uken = kalenderen.get(Calendar.WEEK_OF_YEAR);
        String ukenS = String.valueOf(uken);

        // Finner årstall
        int aaret = kalenderen.get(Calendar.YEAR);
        String aaretS = String.valueOf(aaret);

        DocumentReference dben =db.collection("users").document(user.getUid());
        // lager bruker dokumetet
        dben.set(brukeren);

        // Lager år og uke collections/documents
        Map<String, Object> datoen = new HashMap<>();
        datoen.put("ukenr", ukenS);
        dben.collection(aaretS).document(ukenS).set(datoen);

        // Må heller legge inn sjekker på om dagen finnes når den hentes inn


    }

    // logget inn/ ikkje logget in
    public void logInn(FirebaseUser user){

        // Sjekker om bruker er null


        if (user == null) {
            // bruker ikkje logget inn
            Log.d("NOEAD", "Bruker ikkje logget inn");

        } else {
            // Logget inn allerede
            Log.d("NOEAD", user.getEmail());
            endreFragment();

        }

    }
}
