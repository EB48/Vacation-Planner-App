package com.example.d424vacationplanner.reports;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class Report<T> {
    protected String title;

    public Report(String title) {
        this.title = title;
    }

    // Polymorphic method
    public abstract String generate(List<T> items);

    protected String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
    protected String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return sdf.format(new Date(timestamp));
    }

}


