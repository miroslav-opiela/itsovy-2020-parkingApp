package sk.itsovy.android.parkingapp;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

public class Converters {

    @TypeConverter
    public static Timestamp longToTimestamp(Long value) {
        return value == null ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long timestampToLong(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.getTime();
    }

}
