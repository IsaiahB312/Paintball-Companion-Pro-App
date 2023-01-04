package com.example.paintballcompanionpro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayersAdapter1 extends RecyclerView.Adapter<PlayersAdapter1.ViewHolder>{
    private Context context;
    private ArrayList names;

    public PlayersAdapter1(Context context, ArrayList names) {
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.player_list_item_one_life, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(names.get(position)));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageButton life1;
        Boolean life1pressed = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.playerName);
            life1 = itemView.findViewById(R.id.life1);

            life1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (life1pressed == false) {
                        life1.setBackgroundResource(R.drawable.ic_life_lost);
                        life1pressed = true;
                    }
                    else {
                        life1.setBackgroundResource(R.drawable.ic_life);
                        life1pressed = false;
                    }
                }
            });
        }
    }
}
