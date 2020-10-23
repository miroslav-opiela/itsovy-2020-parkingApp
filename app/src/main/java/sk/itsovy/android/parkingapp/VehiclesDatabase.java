package sk.itsovy.android.parkingapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Vehicle.class}, version = 1, exportSchema = false)
public abstract class VehiclesDatabase extends RoomDatabase {

    // abstraktna metoda
    public abstract VehiclesDao vehiclesDao();

    // instancne premenne
    private static volatile VehiclesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // staticka metoda
    static VehiclesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VehiclesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VehiclesDatabase.class, "vehicles_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}