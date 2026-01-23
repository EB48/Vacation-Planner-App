package com.example.d424vacationplanner.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424vacationplanner.R;
import com.example.d424vacationplanner.dao.VacationDAO;
import com.example.d424vacationplanner.database.VacationDatabaseBuilder;
import com.example.d424vacationplanner.entities.Vacation;
import com.example.d424vacationplanner.entities.VacationWithExcursion;
import com.example.d424vacationplanner.reports.Report;
import com.example.d424vacationplanner.reports.VacationReport;
import com.example.d424vacationplanner.reports.VacationWithExcursionReport;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportDetails extends AppCompatActivity {

    private TextView reportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);

        reportTextView = findViewById(R.id.reportTextView);

        Toolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(getApplicationContext());
            VacationDAO vacationDAO = db.vacationDAO();

            List<VacationWithExcursion> withExcursions = vacationDAO.getVacationsWithExcursions();
            Report<VacationWithExcursion> reportWithExcursions = new VacationWithExcursionReport();
            String outputWithExcursions = reportWithExcursions.generate(withExcursions);

            List<Vacation> withoutExcursions = vacationDAO.getVacationsWithoutExcursions();
            Report<Vacation> reportWithoutExcursions = new VacationReport();
            String outputWithoutExcursions = reportWithoutExcursions.generate(withoutExcursions);

            // Combine both reports
            String finalOutput = outputWithExcursions + "\n_______________________________________\n\n" + outputWithoutExcursions;

            // Update UI on main thread
            runOnUiThread(() -> reportTextView.setText(finalOutput));
        });
    }
}
