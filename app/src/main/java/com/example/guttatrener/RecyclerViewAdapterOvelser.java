package com.example.guttatrener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterOvelser extends RecyclerView.Adapter<RecyclerViewAdapterOvelser.ViewHolder> {
    public RecyclerViewAdapterOvelser(ArrayList<String> ovelseBilde, ArrayList<String> ovselseNavn, Context context) {
        this.ovelseBildeList = ovelseBilde;
        this.ovselseNavnList = ovselseNavn;
        this.mContext = context;
    }

    private ArrayList<String> ovelseBildeList = new ArrayList<>();
    private ArrayList<String> ovselseNavnList = new ArrayList<>();
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
