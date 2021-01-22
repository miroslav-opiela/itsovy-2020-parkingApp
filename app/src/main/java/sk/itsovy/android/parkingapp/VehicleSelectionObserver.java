package sk.itsovy.android.parkingapp;

import android.app.AppComponentFactory;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;

public class VehicleSelectionObserver extends SelectionTracker.SelectionObserver<Long> implements ActionMode.Callback {

    private AppCompatActivity activity;
    private SelectionTracker<Long> selectionTracker;

    private ActionMode actionMode;

    public VehicleSelectionObserver(AppCompatActivity activity,
                                    SelectionTracker<Long> selectionTracker) {
        this.activity = activity;
        this.selectionTracker = selectionTracker;
    }

    @Override
    public void onSelectionChanged() {
        if (selectionTracker.hasSelection()) {
            // ak ide o prvy vyber
            if (actionMode == null) {
                actionMode = activity.startSupportActionMode(this);
            }
            actionMode.setTitle("Selected: " + selectionTracker.getSelection().size());
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // tu pridame ikonku na vymazanie
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // tu vyriesime kliknutie na ikonku a vymazeme vozidla
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        selectionTracker.clearSelection();
        actionMode = null;
    }
}
