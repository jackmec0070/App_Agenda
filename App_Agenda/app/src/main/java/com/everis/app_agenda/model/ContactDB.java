package com.everis.app_agenda.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {

    private static ContactDB instance;

    public abstract ContactDAO contactDAO();

    public static synchronized ContactDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ContactDB.class, "contact_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbSyncTask(instance).execute();
        }
    };

    private static class PopulateDbSyncTask extends AsyncTask<Void, Void, Void>{
        private ContactDAO contactDAO;

        private PopulateDbSyncTask(ContactDB contactDB){
            contactDAO = contactDB.contactDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
