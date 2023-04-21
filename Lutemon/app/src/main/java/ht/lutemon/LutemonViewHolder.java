package ht.lutemon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LutemonViewHolder extends RecyclerView.ViewHolder {

    Lutemon mLutemon;
    LinearLayout layout;
    ImageView imageView;
    TextView textView;

    static String TAG = "ZZ LutemonViewHolder";
    LutemonRepository repository;

    public LutemonViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = (LinearLayout) itemView.findViewById(R.id.layout_for_lutemon);
        imageView = itemView.findViewById(R.id.imageViewLutemon);
        textView = itemView.findViewById(R.id.lutemonInfo);

        repository = new LutemonRepository(((Activity) itemView.getContext()).getApplication());

        itemView.setOnLongClickListener(v-> {
            delete(v.getContext());
            return true;
        });

        itemView.setOnClickListener(v-> edit(v.getContext()));
    }

    /** invoked by long-click item view from recyclerView
     * this method will delete current record (lutemon) from database
     * @param context to build AlertDialog
     */
    private void delete(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This action will remove this recode (lutemon) permanently.");
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("CONFIRM", (dialog, which) -> repository.delete(mLutemon));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /** invoked by clicking item view from recyclerView
     * this method will update name of lutemon
     * @param context to build AlertDialog
     */
    public void edit(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater and inflate and set layout for dialog
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.edit_layout, null);
        EditText editText = (EditText) dialogView.findViewById(R.id.etName);
        editText.setText(mLutemon.getName());
        builder.setView(dialogView)
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("UPDATE", (dialog, which) -> {
                    String text = editText.getText().toString();
                    if (!text.isEmpty()) {
                        mLutemon.setName(text);
                        repository.update(mLutemon);
                    }
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void bind(Lutemon lutemon) {
        mLutemon = lutemon;
        imageView.setImageResource(lutemon.getImageSource());
        textView.setText(lutemon.toString());
        layout.setBackgroundColor(lutemon.getBackground_color());
    }

    static LutemonViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lutemon_layout, parent, false);
        return new LutemonViewHolder(view);
    }
}
