package sk.itsovy.android.parkingapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VehiclesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vehicle vehicle);

    @Query("SELECT * FROM vehicles")
    LiveData<List<Vehicle>> getAllVehicles();

    @Query("SELECT * FROM vehicles WHERE plate=:licencePlate")
    LiveData<Vehicle> getByPlate(String licencePlate);

    @Delete
    void deleteVehicle(Vehicle vehicle);

    @Delete
    void deleteVehicle(String plate);

}
