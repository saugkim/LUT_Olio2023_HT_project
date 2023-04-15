package ht.lutemon;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public LutemonViewHolder(@NonNull View itemView) {
        super(itemView);
        layout = (LinearLayout) itemView.findViewById(R.id.layout_for_lutemon);
        imageView = itemView.findViewById(R.id.imageViewLutemon);
        textView = itemView.findViewById(R.id.lutemonInfo);

        itemView.setOnLongClickListener(v->{
           LutemonStorage.getInstance().removeLutemon(mLutemon);
           Helper.save(v.getContext());
           return true;
        });
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
