package com.example.guttatrener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdaptder extends RecyclerView.Adapter<RecyclerViewAdaptder.ViewHolder> {

    public RecyclerViewAdaptder(ArrayList<String> ovelseNavnList, ArrayList<String> imagePlassList, ArrayList<String> idlisten,ArrayList<Integer> tallSetList, ArrayList<Integer> tallRepsList, ArrayList<Integer> tallVektList, String arrNr, String ukeNr, String dagNr, Context mContext) {
        this.ovelseNavnList = ovelseNavnList;
        this.imagePlassList = imagePlassList;
        this.idlisten = idlisten;
        this.tekstSetList = tekstSetList;
        this.tallSetList = tallSetList;
        this.tekstRepsList = tekstRepsList;
        this.tallRepsList = tallRepsList;
        this.tekstVektList = tekstVektList;
        this.tallVektList = tallVektList;
        this.mContext = mContext;
        this.arrNr = arrNr;
        this.ukeNr = ukeNr;
        this.dagNr = dagNr;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<String> ovelseNavnList = new ArrayList<>();
    private ArrayList<String> imagePlassList = new ArrayList<>();
    private ArrayList<String> idlisten = new ArrayList<>();
    private ArrayList<String> tekstSetList = new ArrayList<>();
    private ArrayList<Integer> tallSetList = new ArrayList<>();
    private ArrayList<String> tekstRepsList = new ArrayList<>();
    private ArrayList<Integer> tallRepsList = new ArrayList<>();
    private ArrayList<String> tekstVektList = new ArrayList<>();
    private ArrayList<Integer> tallVektList = new ArrayList<>();
    private Context mContext;
    private String arrNr;
    private String ukeNr;
    private String dagNr;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_treningitem, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(imagePlassList.get(position))
                .into(holder.imageM);

        holder.ovelse.setText(ovelseNavnList.get(position));


        holder.tallSet.setText(tallSetList.get(position).toString());

        holder.tallReps.setText(tallRepsList.get(position).toString());

        holder.tallVekt.setText(tallVektList.get(position).toString());


        holder.parent_layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,ovelseNavnList.get(position), Toast.LENGTH_SHORT).show();

            }
        });
        // En refernce på pathen alle queryes skal igjennom her
        DocumentReference dben = db.collection("users").document(user.getUid()).collection(arrNr).document(ukeNr).collection(dagNr).document(idlisten.get(position));

        // Slett knapp
        holder.knappSlett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sletter øvelsen
                dben.delete();
            }
        });

        // Endre setTallet
        holder.tallSet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    int setTall = Integer.parseInt(holder.tallSet.getText().toString());
                    // Endrer SET tallet i db
                    dben.update("set", setTall);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        // Endrer repsTallet

        holder.tallReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    int repsTall = Integer.parseInt(holder.tallReps.getText().toString());
                    dben.update("reps", repsTall);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Endret vekt tallet
        holder.tallVekt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    int vektTall = Integer.parseInt(holder.tallVekt.getText().toString());
                    dben.update("vekt", vektTall);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return ovelseNavnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        // Daten
        CircleImageView imageM;
        RelativeLayout parent_layout;
        TextView ovelse;
        TextView tekstSet;
        EditText tallSet;
        TextView tekstReps;
        EditText tallReps;
        TextView tekstVekt;
        EditText tallVekt;
        Button knappSlett;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageM = itemView.findViewById(R.id.bildeTrening);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            ovelse = itemView.findViewById(R.id.ovelseId);
            tekstSet = itemView.findViewById(R.id.setTrening);
            tallSet = itemView.findViewById(R.id.setTall);
            tekstReps = itemView.findViewById(R.id.repsTrening);
            tallReps = itemView.findViewById(R.id.repsTall);
            tekstVekt = itemView.findViewById(R.id.vektTrening);
            tallVekt = itemView.findViewById(R.id.vektTall);
            knappSlett = itemView.findViewById(R.id.knappSlett);



        }
    }
}
