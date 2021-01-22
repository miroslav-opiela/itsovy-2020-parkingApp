package sk.itsovy.android.parkingapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Vehicle.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
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
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // instancna premenna
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                VehiclesDao dao = INSTANCE.vehiclesDao();
                // ak to tu nie je tak vytvori sa 4x resp. podla poctu vlakien
                dao.deleteAll();

                for (int i = 10; i < 35; i++) {
                    Vehicle v1 = new Vehicle();
                    v1.setPlate("KE1" + i + "AA");
                    v1.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    dao.insert(v1);
                }

            });
        }
    };
}