package com.example.d424vacationplanner.reports;

import com.example.d424vacationplanner.entities.Vacation;

import java.util.List;

public class VacationReport extends Report<Vacation> {

    public VacationReport() {
        super("Vacation Report (No Excursions)");
    }

    @Override
    public String generate(List<Vacation> vacations) {
        StringBuilder report = new StringBuilder();

        report.append(title).append("\n");
        report.append("Generated: ").append(getTimestamp()).append("\n\n");

        if (vacations.isEmpty()) {
            report.append("No vacations available.\n");
            return report.toString();
        }

        for (Vacation v : vacations) {
            report.append("Vacation Name: ").append(v.getVacationName()).append("\n");
            report.append("Hotel Name: ").append(v.getVacationHotel()).append("\n");
            report.append("Start Date: ").append(formatDate(v.getStartDate())).append("\n");
            report.append("End Date: ").append(formatDate(v.getEndDate())).append("\n");
            report.append("----------------------------------------\n");
        }

        return report.toString();
    }
}

