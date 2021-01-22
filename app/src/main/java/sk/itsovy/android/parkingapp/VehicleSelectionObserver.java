package sk.itsovy.android.parkingapp;

import android.app.AppComponentFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.SelectionTracker;

public class VehicleSelectionObserver extends SelectionTracker.SelectionObserver<Long> {

    private AppCompatActivity activity;
    private SelectionTracker<Long> selectionTracker;

    public VehicleSelectionObserver(AppCompatActivity activity,
                                    SelectionTracker<Long> selectionTracker) {
        this.activity = activity;
        this.selectionTracker = selectionTracker;
    }

    @Override
    public void onSelectionChanged() {
        if (selectionTracker.hasSelection()) {
            activity.setTitle("Selected: " + selectionTracker.getSelection().size());
        } else {
            activity.setTitle(R.string.app_name);
        }
    }
}
