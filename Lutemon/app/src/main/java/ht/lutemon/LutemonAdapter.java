package ht.lutemon;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonViewHolder> {
    ArrayList<Lutemon> lutemons;
    Context context;

    public LutemonAdapter(ArrayList<Lutemon> lutemons, Context context) {
        this.lutemons = lutemons;
        this.context = context;
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LutemonViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        int index = holder.getAdapterPosition();
        Lutemon lutemon = lutemons.get(index);
        holder.bind(lutemon);

        holder.layout.setOnClickListener(v-> {
            Log.d("ZZ Adapter", "Position: " + position + ", index: " + index);
            edit(position, lutemon);
        });

        holder.layout.setOnLongClickListener(v-> {
            LutemonStorage.getInstance().removeLutemon(lutemon);
            LutemonStorage.getInstance().save(context);
            notifyItemRemoved(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    private void edit(int position, Lutemon lutemon) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater and inflate and set layout for dialog
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.edit_layout, null);
        EditText editText = (EditText) dialogView.findViewById(R.id.etName);
        editText.setText(lutemon.getName());
        builder.setView(dialogView)
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("UPDATE", (dialog, which) -> {
                    String text = editText.getText().toString();
                    if (!text.isEmpty()) {
                        Log.d("ZZ Adapter: ", "inside dialog: " + text);
                        lutemon.setName(text);
                        //int index = Collections.singletonList(lutemons).indexOf(lutemon);
                        //LutemonStorage.getInstance().update(position, lutemon);
                        LutemonStorage.getInstance().save(context);
                        notifyItemChanged(position);
                    }
                    dialog.dismiss();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
