package com.example.guttatrener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterOvelser extends RecyclerView.Adapter<RecyclerViewAdapterOvelser.ViewHolder> {
    public RecyclerViewAdapterOvelser(ArrayList<String> ovelseBilde, ArrayList<String> ovselseNavn, String dagen, String uken, String aaret, Context context) {
        this.ovelseBildeList = ovelseBilde;
        this.ovselseNavnList = ovselseNavn;
        this.mContext = context;
        this.aaret = aaret;
        this.uken = uken;
        this.dagen = dagen;
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<String> ovelseBildeList = new ArrayList<>();
    private ArrayList<String> ovselseNavnList = new ArrayList<>();
    private String aaret;
    private String uken;
    private String dagen;

    private Context mContext;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_treningovelse, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(ovelseBildeList.get(position))
                .into(holder.imageM);

        holder.ovelsenavn.setText(ovselseNavnList.get(position));

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Legg til i dagen
                Map<String, Object> ovelse = new HashMap<>();
                ovelse.put("bildePlassering", ovelseBildeList.get(position));
                ovelse.put("navn", ovselseNavnList.get(position));
                ovelse.put("reps", 0);
                ovelse.put("set", 0);
                ovelse.put("vekt", 0);
                ovelse.put("dato", Timestamp.now());

                db.collection("users").document(user.getUid()).collection(aaret).document(uken)
                        .collection(dagen).document().set(ovelse)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, ovselseNavnList.get(position)+" er lagt til", Toast.LENGTH_SHORT).show();
                            }
                        });


                Toast.makeText(mContext, ovselseNavnList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ovselseNavnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // dataen
        CircleImageView imageM;
        RelativeLayout parent_layout;
        TextView ovelsenavn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageM = itemView.findViewById(R.id.bildeTreninger);
            ovelsenavn = itemView.findViewById(R.id.ovelseId);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }{

    }
}
