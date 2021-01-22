package sk.itsovy.android.parkingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VehiclesViewModel extends AndroidViewModel {

    private VehicleRepository repository;
    private LiveData<List<Vehicle>> vehicles;

    public VehiclesViewModel(@NonNull Application application) {
        super(application);
        repository = new VehicleRepository(application);
        vehicles = repository.getAllVehicles();
    }

    LiveData<List<Vehicle>> getVehicles() {
        return vehicles;
    }

    public void insert(Vehicle vehicle) {
        repository.insert(vehicle);
    }

    public void delete(Vehicle vehicle) {
        repository.delete(vehicle);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
