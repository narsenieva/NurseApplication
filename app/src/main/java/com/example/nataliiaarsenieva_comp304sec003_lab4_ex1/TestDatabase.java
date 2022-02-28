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

@Database(entities = {Test.class}, version = 1, exportSchema = false)
public abstract class TestDatabase extends RoomDatabase {
    public abstract TestDao testDao();

    private static volatile TestDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TestDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TestDatabase.class, "TestDB")
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
            //new TestDatabase.PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private  TestDao testDao;
        private PopulateDbAsyncTask(TestDatabase db) { testDao = db.testDao(); }
        @Override
        protected Void doInBackground(Void... voids) {
            testDao.insert(new Test(80, 120, 36.2, 4.0, 2, 111));
            testDao.insert(new Test(90, 130, 37.5, 8.0, 2, 111));
            testDao.insert(new Test(80, 124, 36.8, 5.0, 2, 111));
            return null;
        }
    }
}
