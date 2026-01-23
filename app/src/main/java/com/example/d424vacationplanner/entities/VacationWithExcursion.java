package com.example.d424vacationplanner.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VacationWithExcursion {
    @Embedded
    public Vacation vacation;

    @Relation(
            parentColumn = "vacationID", //Vacation PK
            entityColumn = "vacationID" //Excursion FK
    )
    public List<Excursion> excursions;
}
