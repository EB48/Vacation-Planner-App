package com.example.d424vacationplanner.reports;

import com.example.d424vacationplanner.entities.Excursion;
import com.example.d424vacationplanner.entities.VacationWithExcursion;

import java.util.List;

public class VacationWithExcursionReport extends Report<VacationWithExcursion> {

    public VacationWithExcursionReport() {
        super("Vacation Report (With Excursions)");
    }

    @Override
    public String generate(List<VacationWithExcursion> vacations) {
        StringBuilder report = new StringBuilder();

        report.append(title).append("\n");
        report.append("Generated: ").append(getTimestamp()).append("\n\n");

        if (vacations.isEmpty()) {
            report.append("No vacations with excursions available.\n");
            return report.toString();
        }

        for (VacationWithExcursion vwe : vacations) {
            report.append("Vacation Name: ").append(vwe.vacation.getVacationName()).append("\n");
            report.append("Hotel Name: ").append(vwe.vacation.getVacationHotel()).append("\n");
            report.append("Start Date: ")
                    .append(formatDate(vwe.vacation.getStartDate()))
                    .append("\n");
            report.append("End Date: ")
                    .append(formatDate(vwe.vacation.getEndDate()))
                    .append("\n");

            if (vwe.excursions != null && !vwe.excursions.isEmpty()) {
                report.append("Excursions:\n");
                for (Excursion e : vwe.excursions) {
                    report.append("  - ").append(e.getExcursionName())
                            .append(" (").append(formatDate(e.getExcursionDate())).append(")\n");
                }
            } else {
                report.append("Excursions: None\n");
            }

            report.append("----------------------------------------\n");
        }

        return report.toString();
    }
}

