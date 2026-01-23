package com.example.d424vacationplanner;

import com.example.d424vacationplanner.entities.Vacation;
import com.example.d424vacationplanner.util.VacationSearchUtil;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchFunctionalityTest {

    private List<Vacation> vacationList;

    @Before
    public void setUp() {
        vacationList = new ArrayList<>();

        vacationList.add(new Vacation(
                1, "Hawaii Trip", "Hotel A",
                1700000000000L, 1700864000000L
        ));

        vacationList.add(new Vacation(
                2, "Paris Vacation", "Hotel B",
                1702000000000L, 1702864000000L
        ));
    }

    @Test
    public void emptySearch_returnsAllVacations() {
        List<Vacation> results =
                VacationSearchUtil.filterVacations("", vacationList);

        assertEquals(2, results.size());
    }

    @Test
    public void validSearch_returnsMatchingVacation() {
        List<Vacation> results =
                VacationSearchUtil.filterVacations("hawaii", vacationList);

        assertEquals(1, results.size());
        assertEquals("Hawaii Trip", results.get(0).getVacationName());
    }
}
