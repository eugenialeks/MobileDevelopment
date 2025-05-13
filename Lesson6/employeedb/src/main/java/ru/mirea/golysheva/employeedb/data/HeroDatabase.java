package ru.mirea.golysheva.employeedb.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Hero.class}, version = 1)
public abstract class HeroDatabase extends RoomDatabase {
    public abstract HeroDao heroDao();
}
