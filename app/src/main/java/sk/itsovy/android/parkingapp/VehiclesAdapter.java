package sk.itsovy.android.parkingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehiclesAdapter
        extends RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder> {

    // cache verzia dat
    private List<Vehicle> cachedVehicles;

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_layout, parent, false);
        return new VehicleViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.bind(cachedVehicles.get(position));
    }

    @Override
    public int getItemCount() {
        return cachedVehicles.size();
    }

    public static class VehicleViewHolder
            extends RecyclerView.ViewHolder {

        private TextView textView;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewPlate);
        }

        public void bind(Vehicle vehicle) {
            textView.setText(vehicle.getPlate());
        }
    }

}

