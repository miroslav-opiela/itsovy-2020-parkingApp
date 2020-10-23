package sk.itsovy.android.parkingapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VehicleRepository {

    private VehiclesDao vehiclesDao;
    private LiveData<List<Vehicle>> vehicles;

    VehicleRepository(Application application) {
        VehiclesDatabase database = VehiclesDatabase.getDatabase(application);
        vehiclesDao = database.vehiclesDao();

        vehicles = vehiclesDao.getAllVehicles();
    }

    LiveData<List<Vehicle>> getAllVehicles() {
        return vehicles;
    }

    void insert(Vehicle vehicle) {
        VehiclesDatabase.databaseWriteExecutor.execute(
                () -> {
                    vehiclesDao.insert(vehicle);
                }
        );
    }
}
