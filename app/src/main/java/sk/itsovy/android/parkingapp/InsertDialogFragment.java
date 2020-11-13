package sk.itsovy.android.parkingapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Timestamp;

public class InsertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.insert_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.insertVehiclePlate)
                .setView(layout)
                .setPositiveButton(R.string.insert, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ViewModelProvider provider = new ViewModelProvider(requireActivity());
                        VehiclesViewModel viewModel = provider.get(VehiclesViewModel.class);

                        EditText editText = layout.findViewById(R.id.insertPlate);
                        Vehicle vehicle = new Vehicle();
                        vehicle.setPlate(editText.getText().toString());
                        vehicle.setTimestamp(new Timestamp(System.currentTimeMillis()));
                        viewModel.insert(vehicle);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }
}
