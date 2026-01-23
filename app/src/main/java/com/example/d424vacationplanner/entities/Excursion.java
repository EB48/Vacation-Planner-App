package com.example.d424vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions", foreignKeys = @ForeignKey(
        entity = Vacation.class,
        parentColumns = "vacationID",
        childColumns = "vacationID",
        onDelete = ForeignKey.CASCADE),
        indices = @Index("vacationID"))

public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private long excursionDate;
    private int vacationID;


    public Excursion(int excursionID, String excursionName, long excursionDate, int vacationID) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public long getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(long excursionDate) {
        this.excursionDate = excursionDate;
    }
}
