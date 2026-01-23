package com.example.d424vacationplanner.database;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.example.d424vacationplanner.dao.ExcursionDAO;
import com.example.d424vacationplanner.dao.VacationDAO;
import com.example.d424vacationplanner.entities.Excursion;
import com.example.d424vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }

    public void getAllVacations1(Consumer<List<Vacation>> callback) {
        databaseExecutor.execute(()->{
            List<Vacation> vacations = mVacationDAO.getAllVacations();
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(vacations));
        });
    }

    public void getVacationById(int id, Consumer<Vacation> callback) {
        databaseExecutor.execute(()->{
            Vacation vacation = mVacationDAO.getVacationById(id);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(vacation));
        });
    }

    public void update1(Vacation vacation, Consumer<Vacation> callback) {
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(vacation));
        });
    }

    public void insert1(Vacation vacation, Consumer<Vacation> callback) {
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(vacation));
        });
    }

    public void delete1(Vacation vacation, Consumer<Vacation> callback) {
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(vacation));
        });
    }



    public void getAllExcursions1(Consumer<List<Excursion>> callback) {
        databaseExecutor.execute(()->{
            List<Excursion> excursions = mExcursionDAO.getAllExcursions();
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(excursions));
        });
    }

    public void getAssociatedExcursions1(int vacationID, Consumer<List<Excursion>> callback) {
        databaseExecutor.execute(()->{
            List<Excursion> excursions = mExcursionDAO.getAssociatedExcursions(vacationID);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(excursions));
        });
    }

    public void update1(Excursion excursion, Consumer<Excursion> callback) {
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(excursion));
        });
    }

    public void insert1(Excursion excursion, Consumer<Excursion> callback) {
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(excursion));
        });
    }

    public void delete1(Excursion excursion, Consumer<Excursion> callback) {
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(excursion));
        });
    }

}
