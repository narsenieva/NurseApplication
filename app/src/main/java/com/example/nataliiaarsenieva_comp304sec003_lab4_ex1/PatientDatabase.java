package com.example.nataliiaarsenieva_comp304sec003_lab4_ex1;

// Prepared by: Nataliia Arsenieva - 301043237
// COMP304 (Sec.003) - Lab Assignment 4 - Fall 2021
// Date: 16-11-2021

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Patient.class}, version = 1, exportSchema = false)
public abstract class PatientDatabase extends RoomDatabase {

    public abstract PatientDao patientDao();

    private static volatile PatientDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PatientDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PatientDatabase.class, "PatientDB")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //new PatientDatabase.PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private  PatientDao patientDao;
        private PopulateDbAsyncTask(PatientDatabase db) {
            patientDao = db.patientDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            patientDao.insert(new Patient( "Dave", "Chadder", Departments.Nursing, 123, "A304"));
            patientDao.insert(new Patient("Jack", "Monger", Departments.Surgery, 123, "A111"));
            patientDao.insert(new Patient("Miko", "Yae", Departments.Dentistry, 456, "B412"));
            return null;
        }
    }
}

