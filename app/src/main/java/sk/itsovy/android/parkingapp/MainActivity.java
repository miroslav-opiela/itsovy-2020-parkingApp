package sk.itsovy.android.parkingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPlateClickListener{

    private SelectionTracker<Long> selectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFabClick();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        VehiclesAdapter adapter = new VehiclesAdapter();
        adapter.setOnPlateClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider provider = new ViewModelProvider(this);
        VehiclesViewModel vehiclesViewModel = provider.get(VehiclesViewModel.class);
        vehiclesViewModel.getVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                adapter.setCachedVehicles(vehicles);
            }
        });

        selectionTracker = new SelectionTracker.Builder<Long>(
                "my-selection-id",
                recyclerView,
                new VehiclesKeyProvider(recyclerView),
                new VehiclesLookup(recyclerView),
                StorageStrategy.createLongStorage()
        ).build();
        adapter.setSelectionTracker(selectionTracker);
        selectionTracker.addObserver(
                new VehicleSelectionObserver(this, selectionTracker) {
                    @Override
                    protected void onDeleteItems(Iterator<Long> iterator) {
                        remove(iterator);
                    }
                });
    }

    private void processFabClick() {
        DialogFragment insertDialogFragment = new InsertDialogFragment();
        insertDialogFragment.show(getSupportFragmentManager(), "insert");

    }

    private void remove(Iterator<Long> iterator) {
        ViewModelProvider provider = new ViewModelProvider(this);
        VehiclesViewModel vehiclesViewModel = provider.get(VehiclesViewModel.class);

        while(iterator.hasNext()) {
            Long id = iterator.next();
            vehiclesViewModel.delete(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onSettingsClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSettingsClicked() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlateClick(Vehicle vehicle) {
        ViewModelProvider provider = new ViewModelProvider(this);
        VehiclesViewModel vehiclesViewModel = provider.get(VehiclesViewModel.class);

        //TODO urobit select a spocitat cenu parkovania

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean hourlyRate = pref.getBoolean("rate", true);
        String message;
        if (hourlyRate) {
            message = "Plati sa za hodinu";
        } else {
            message = "minutova tarifa";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        vehiclesViewModel.delete(vehicle);
    }
}