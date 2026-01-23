package com.example.d424vacationplanner.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424vacationplanner.R;
import com.example.d424vacationplanner.database.Repository;
import com.example.d424vacationplanner.entities.Excursion;
import com.example.d424vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class VacationDetails extends AppCompatActivity {
    String name;
    String hotel;
    int vacationID;
    EditText editName;
    EditText editHotel;
    Button editStartDate;
    Button editEndDate;
    Repository repository;
    private ExcursionAdapter excursionAdapter;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        editStartDate = findViewById(R.id.button);
        editEndDate = findViewById(R.id.button2);
        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        editName.setText(name);
        editHotel.setText(hotel);


        repository = new Repository(getApplication());

        long startDateMillis = getIntent().getLongExtra("startDate", -1);
        long endDateMillis = getIntent().getLongExtra("endDate", -1);

        if (startDateMillis != -1) {
            myCalendarStart.setTimeInMillis(startDateMillis);
            updateLabelStart();
        }

        if (endDateMillis != -1) {
            myCalendarEnd.setTimeInMillis(endDateMillis);
            updateLabelEnd();
        }


        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vacationID == -1) {
                    Toast.makeText(
                            VacationDetails.this,
                            "Save vacation before adding excursions",
                            Toast.LENGTH_LONG
                    ).show();

                } else {
                    Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);
                    intent.putExtra("vacationID", vacationID);
                    intent.putExtra("vacationStart", myCalendarStart.getTimeInMillis());
                    intent.putExtra("vacationEnd", myCalendarEnd.getTimeInMillis());
                    startActivity(intent);
                }
            }

        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository.getAllExcursions1(excursions -> {
            // Filter excursions for this vacationID
            List<Excursion> filteredExcursions = new ArrayList<>();
            for (Excursion e : excursions) {
                if (e.getVacationID() == vacationID) {
                    filteredExcursions.add(e);
                }
            }

            excursionAdapter.setExcursions(filteredExcursions);

        });

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


        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) {
                    info = "12/12/25";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
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

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) {
                    info = "12/20/25";
                }
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

    }
    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository.getAllExcursions1(excursions -> {
            // Filter excursions for this vacationID
            List<Excursion> filteredExcursions = new ArrayList<>();
            for (Excursion e : excursions) {
                if (e.getVacationID() == vacationID) {
                    filteredExcursions.add(e);
                }
            }

            // Update the adapter on the main thread
            excursionAdapter.setExcursions(filteredExcursions);
        });

    }

    public void vacationHasExcursions(Consumer<Boolean> callback) {
        repository.getAllExcursions1(excursions -> {
            boolean hasExcursions = false;
            for (Excursion e : excursions) {
                if (e.getVacationID() == vacationID) {
                    hasExcursions = true;
                    break;
                }
            }
            // Pass the result to the callback on the main thread
            callback.accept(hasExcursions);
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;
            long startDateMillis = myCalendarStart.getTimeInMillis();
            long endDateMillis = myCalendarEnd.getTimeInMillis();

            if (endDateMillis <= startDateMillis) {
                Toast.makeText(
                        this,
                        "Vacation end date must be after the start date",
                        Toast.LENGTH_LONG
                ).show();
                return true;
            }

            // GET USER INPUT
            String rawName = editName.getText().toString();
            String rawHotel = editHotel.getText().toString();

            // VALIDATION
            if (!isValidText(rawName, 50)) {
                editName.setError("Vacation name required (max 50 characters)");
                editName.requestFocus();
                return true;
            }

            if (!isValidText(rawHotel, 50)) {
                editHotel.setError("Hotel name required (max 50 characters)");
                editHotel.requestFocus();
                return true;
            }

            // SANITIZATION
            String cleanName = sanitize(rawName);
            String cleanHotel = sanitize(rawHotel);

            if (vacationID == -1) {

                vacation = new Vacation(
                        0,
                        cleanName,
                        cleanHotel,
                        startDateMillis,
                        endDateMillis
                );

                repository.insert1(vacation, vacationInsert -> {
                    this.finish();
                });

            } else {
                vacation = new Vacation(
                        vacationID,
                        cleanName,
                        cleanHotel,
                        startDateMillis,
                        endDateMillis
                );

                repository.update1(vacation, updatedVacation -> {
                    this.finish();
                });

            }
            return true;
        }

        if (item.getItemId() == R.id.vacationdelete) {
            vacationHasExcursions(hasExcursions -> {
                if (hasExcursions) {
                    Toast.makeText(
                            this,
                            "Cannot delete a vacation with excursions. Delete all excursions first.",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Vacation")
                            .setMessage("Are you sure you want to delete this vacation?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                    Vacation vacation = new Vacation(
                            vacationID,
                            editName.getText().toString(),
                            editHotel.getText().toString(),
                            myCalendarStart.getTimeInMillis(),
                            myCalendarEnd.getTimeInMillis()
                    );

                    repository.delete1(vacation, deletedVacation -> {
                        finish();
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();

                }
            });

        }

        if (item.getItemId() == R.id.alert) {
            String start = editStartDate.getText().toString(); editEndDate.getText().toString();
            String end = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = sdf.parse(start);
                endDate = sdf.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = startDate.getTime();
            Long trigger2 = endDate.getTime();
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            String vacationName = editName.getText().toString();

            Intent startIntent = new Intent(VacationDetails.this, VacationAlertReceiver.class);
            startIntent.putExtra("key", "Vacation " + vacationName + " is starting");
            PendingIntent startSender = PendingIntent.getBroadcast(
                    VacationDetails.this,
                    ++MainActivity.numAlert,
                    startIntent,
                    PendingIntent.FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, startSender);

            Intent endIntent = new Intent(VacationDetails.this, VacationAlertReceiver.class);
            endIntent.putExtra("key", "Vacation " + vacationName + " is ending");
            PendingIntent endSender = PendingIntent.getBroadcast(
                    VacationDetails.this,
                    ++MainActivity.numAlert,
                    endIntent,
                    PendingIntent.FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger2, endSender);

            return true;

        }

        if (item.getItemId() == R.id.share) {
            repository.getAllExcursions1(excursions -> {
                StringBuilder shareText = new StringBuilder();
                shareText.append("Vacation: " + editName.getText().toString() + "\n");
                shareText.append("Hotel: " + editHotel.getText().toString() + "\n");
                shareText.append("Start Date: " + editStartDate.getText().toString() + "\n");
                shareText.append("End Date: " + editEndDate.getText().toString() + "\n\n");
                shareText.append("Excursions: \n");

                boolean hasExcursions = false;
                for (Excursion e : excursions) {
                    if (e.getVacationID() == vacationID) {
                        hasExcursions = true;

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                        String date = sdf.format(e.getExcursionDate());

                        shareText.append("- " + e.getExcursionName() + " (" + date + ")\n");
                    }
                }

                if (!hasExcursions) {
                    shareText.append("No excursions added.\n");
                }


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation: " + editName.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share Vacation Details");
                startActivity(shareIntent);

            });

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