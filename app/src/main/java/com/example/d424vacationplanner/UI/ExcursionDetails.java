package com.example.d424vacationplanner.UI;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d424vacationplanner.R;
import com.example.d424vacationplanner.database.Repository;
import com.example.d424vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    EditText editName;
    Button editDate;
    int excursionID;
    int vacationID;
    long vacationStart;
    long vacationEnd;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        vacationStart = getIntent().getLongExtra("vacationStart", -1);
        vacationEnd = getIntent().getLongExtra("vacationEnd", -1);


        Toolbar toolbar = findViewById(R.id.materialToolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.excursiontext);
        editName.setText(name);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        editDate = findViewById(R.id.button);

        long startDateMillis = getIntent().getLongExtra("startDate", -1);
        if (startDateMillis != -1) {
            myCalendarStart.setTimeInMillis(startDateMillis);
            updateLabelStart();
        }

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editDate.getText().toString();
                if (info.equals("")) {
                    info = "12/12/25";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Excursion excursion;

            // GET USER INPUT
            String rawName = editName.getText().toString();

            // VALIDATION
            if (!isValidText(rawName, 50)) {
                editName.setError("Excursion name required (max 50 characters)");
                editName.requestFocus();
                return true;
            }

            // SANITIZATION
            String cleanName = sanitize(rawName);

            long startDateMillis = myCalendarStart.getTimeInMillis();

            //This code includes the last day for constraint...
            long inclusiveVacationEnd = vacationEnd;

            if (vacationEnd != -1) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(vacationEnd);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                inclusiveVacationEnd = cal.getTimeInMillis();
            }


            if (vacationStart != -1 && vacationEnd != -1) {
                if (startDateMillis < vacationStart || startDateMillis > inclusiveVacationEnd) {
                    Toast.makeText(
                            this,
                            "Excursion date must be within the vacation dates",
                            Toast.LENGTH_LONG
                    ).show();
                    return true;
                }
            }


            if (excursionID == -1) {
                excursion = new Excursion(
                        0,  // counts ID starting from 0
                        cleanName,
                        startDateMillis,
                        vacationID
                );
                repository.insert1(excursion, insertedExcursion -> {
                    // This runs after the insert finishes, on the main thread
                    this.finish();
                });
            } else {
                repository.getVacationById(vacationID, vacation -> {

                    long vacationStart = vacation.getStartDate();
                    long vacationEnd = vacation.getEndDate();

                    Calendar calStart = Calendar.getInstance();
                    calStart.setTimeInMillis(vacationStart);
                    calStart.set(Calendar.HOUR_OF_DAY, 0);
                    calStart.set(Calendar.MINUTE, 0);
                    calStart.set(Calendar.SECOND, 0);
                    calStart.set(Calendar.MILLISECOND, 0);
                    long normalizedVacationStart = calStart.getTimeInMillis();

                    Calendar calEnd = Calendar.getInstance();
                    calEnd.setTimeInMillis(vacationEnd);
                    calEnd.set(Calendar.HOUR_OF_DAY, 23);
                    calEnd.set(Calendar.MINUTE, 59);
                    calEnd.set(Calendar.SECOND, 59);
                    calEnd.set(Calendar.MILLISECOND, 999);
                    long normalizedVacationEnd = calEnd.getTimeInMillis();

                    if (startDateMillis < normalizedVacationStart || startDateMillis > normalizedVacationEnd) {
                        Toast.makeText(
                                this,
                                "Excursion date must be within the vacation dates",
                                Toast.LENGTH_LONG
                        ).show();
                        return; // stop save
                    }

                    Excursion excursion1 = new Excursion(
                            excursionID,
                            cleanName,
                            startDateMillis,
                            vacationID
                    );

                    repository.update1(excursion1, updated -> finish());

                });

            }
        }

            if (item.getItemId() == R.id.delete) {
                // Prevent deleting unsaved excursion
                if (excursionID == -1) {
                    Toast.makeText(this, "Nothing to delete", Toast.LENGTH_SHORT).show();
                    return true;
                }

                new AlertDialog.Builder(this)
                        .setTitle("Delete Excursion")
                        .setMessage("Are you sure you want to delete this excursion?")
                        .setPositiveButton("Delete", (dialog, which) -> {

                            Excursion excursion = new Excursion(
                                    excursionID,
                                    editName.getText().toString(),
                                    myCalendarStart.getTimeInMillis(),
                                    vacationID
                            );

                            repository.delete1(excursion, updatedExcursion -> {
                                this.finish();
                            });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }

            if (item.getItemId() == R.id.alert) {
                String dateFromScreen = editDate.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger = date.getTime();
                String excursionName = editName.getText().toString();

                Intent intent = new Intent(ExcursionDetails.this, ExcursionAlertReceiver.class);
                intent.putExtra("key", "Excursion " + excursionName + " is starting");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this,
                        ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            }

            return super.onOptionsItemSelected(item);

    }

    // USER INPUT VALIDATION
    private boolean isValidText(String input, int maxLength) {
        return input != null && !input.trim().isEmpty() && input.length() <= maxLength;
    }

    // USER INPUT SANITIZATION
    private String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("[<>\"'%;()&+#$*]", "").trim();
    }

}