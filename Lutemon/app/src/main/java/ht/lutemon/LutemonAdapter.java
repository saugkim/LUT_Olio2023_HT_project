package ht.lutemon;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    ArrayList<Lutemon> lutemons;

    public LutemonAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LutemonViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        Lutemon lutemon = lutemons.get(position);
        holder.bind(lutemon);
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
