package sk.itsovy.android.parkingapp;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehiclesAdapter
        extends RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder> {

    // cache verzia dat
    // mozeme dat = Collections.EMPTY_LIST
    private List<Vehicle> cachedVehicles;
    private OnPlateClickListener listener;
    private SelectionTracker<Long> selectionTracker;

    public VehiclesAdapter() {
        setHasStableIds(true);
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public void setCachedVehicles(List<Vehicle> cachedVehicles) {
        this.cachedVehicles = cachedVehicles;
        notifyDataSetChanged();
    }

    public void setOnPlateClickListener(OnPlateClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_layout, parent, false);
        VehicleViewHolder holder = new VehicleViewHolder(itemLayout);
        holder.setOnPlateClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        boolean isSelected = selectionTracker.isSelected(getItemId(position));
        Log.d("SELECTION", position + " " + getItemId(position) +
                " " + isSelected);
        holder.bind(cachedVehicles.get(position), isSelected);
    }

    @Override
    public long getItemId(int position) {
        return cachedVehicles.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (cachedVehicles == null) {
            return 0;
        }
        return cachedVehicles.size();
    }

    public static class VehicleViewHolder
            extends RecyclerView.ViewHolder {

        private TextView textView;
        private OnPlateClickListener listener;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewPlate);
        }

        public void setOnPlateClickListener(OnPlateClickListener listener) {
            this.listener = listener;
        }

        public void bind(Vehicle vehicle, boolean isSelected) {
            if (isSelected) {
                textView.setTextColor(Color.RED);
            } else {
                textView.setTextColor(Color.BLACK);
            }
            textView.setText(vehicle.getPlate());
            /*textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPlateClick(vehicle);
                }
            });*/
        }
    }

}

