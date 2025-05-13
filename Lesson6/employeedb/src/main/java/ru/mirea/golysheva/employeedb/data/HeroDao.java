package ru.mirea.golysheva.employeedb.data;

import androidx.room.*;
import java.util.List;

@Dao
public interface HeroDao {
    @Query("SELECT * FROM Hero")
    List<Hero> getAll();

    @Query("SELECT * FROM Hero WHERE id = :id")
    Hero getById(long id);

    @Insert
    void insert(Hero hero);

    @Update
    void update(Hero hero);

    @Delete
    void delete(Hero hero);
}