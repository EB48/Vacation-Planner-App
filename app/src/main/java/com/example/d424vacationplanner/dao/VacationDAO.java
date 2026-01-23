package com.example.d424vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.d424vacationplanner.entities.Vacation;
import com.example.d424vacationplanner.entities.VacationWithExcursion;

import java.util.List;
@Dao
public interface VacationDAO {
    @Insert
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM VACATIONS WHERE vacationID = :id")
    Vacation getVacationById(int id);

    @Transaction
    @Query("SELECT * FROM vacations WHERE vacationID IN (SELECT vacationId FROM excursions)")
    List<VacationWithExcursion> getVacationsWithExcursions();

    @Transaction
    @Query("SELECT * FROM vacations WHERE vacationID NOT IN (SELECT vacationId FROM excursions)")
    List<Vacation> getVacationsWithoutExcursions();

}
