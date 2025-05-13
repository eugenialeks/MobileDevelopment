package ru.mirea.golysheva.employeedb;

import android.app.Application;
import androidx.room.Room;
import ru.mirea.golysheva.employeedb.data.HeroDatabase;

public class App extends Application {
    private static App instance;
    private HeroDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, HeroDatabase.class, "heroes.db")
                .allowMainThreadQueries()
                .build();
    }

    public static App get() { return instance; }

    public HeroDatabase db() { return database; }
}