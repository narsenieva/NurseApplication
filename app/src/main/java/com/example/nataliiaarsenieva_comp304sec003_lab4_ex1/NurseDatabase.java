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

@Database(entities = {Nurse.class}, version = 1)
public abstract class NurseDatabase extends RoomDatabase{
    //
    private static NurseDatabase INSTANCE;
    private static final String DATABASE_NAME = "NurseDB";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public abstract NurseDao nurseDao();
    //
    public static synchronized NurseDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            //Create database object
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NurseDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return INSTANCE;
    }

    private  static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new NurseDatabase.PopulateDbAsyncTask(INSTANCE).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private  NurseDao nurseDao;
        private PopulateDbAsyncTask(NurseDatabase db) {
            nurseDao = db.nurseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            nurseDao.insert(new Nurse(123, "Katie", "Smith", "abcd", Departments.Nursing));
            nurseDao.insert(new Nurse(456, "John", "Collins", "0000", Departments.Dentistry));
            return null;
        }
    }
}
