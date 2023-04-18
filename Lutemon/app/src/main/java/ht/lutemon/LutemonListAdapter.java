package ht.lutemon;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class LutemonListAdapter extends ListAdapter<Lutemon, LutemonViewHolder> {

    protected LutemonListAdapter(@NonNull DiffUtil.ItemCallback<Lutemon> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LutemonViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        Lutemon currentLutemon = getItem(position);
        holder.bind(currentLutemon);
    }

    static class ItemDiff extends DiffUtil.ItemCallback<Lutemon> {
        @Override
        public boolean areItemsTheSame(@NonNull Lutemon oldItem, @NonNull Lutemon newItem) {
            return oldItem== newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Lutemon oldItem, @NonNull Lutemon newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}
