package com.example.d424vacationplanner.util;

import com.example.d424vacationplanner.entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VacationSearchUtil {

    public static List<Vacation> filterVacations(String query, List<Vacation> vacations) {
        List<Vacation> filteredList = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(vacations);
        } else {
            String filterPattern = query.toLowerCase(Locale.ROOT).trim();

            for (Vacation vacation : vacations) {
                if (vacation.getVacationName()
                        .toLowerCase(Locale.ROOT)
                        .contains(filterPattern)) {
                    filteredList.add(vacation);
                }
            }
        }
        return filteredList;
    }
}
