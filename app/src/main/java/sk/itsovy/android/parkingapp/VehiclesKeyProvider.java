package sk.itsovy.android.parkingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class VehiclesKeyProvider extends ItemKeyProvider<Long> {

    private RecyclerView recyclerView;

    public VehiclesKeyProvider(RecyclerView recyclerView) {
        super(ItemKeyProvider.SCOPE_MAPPED);
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return recyclerView.getAdapter().getItemId(position);
    }

    @Override
    public int getPosition(@NonNull Long key) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForItemId(key);
        if (viewHolder == null) {
            return RecyclerView.NO_POSITION;
        }
        return viewHolder.getAdapterPosition();
    }
}
