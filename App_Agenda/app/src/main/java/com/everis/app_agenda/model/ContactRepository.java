package com.everis.app_agenda.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {

    private ContactDAO contactDAO;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application){
        ContactDB database = ContactDB.getInstance(application);
        contactDAO = database.contactDAO();
        allContacts = contactDAO.getAllContacts();
    }

    public void insert(Contact contact){

        new InsertContactSyncTask(contactDAO).execute(contact);
    }

    public void update(Contact contact){

        new UpdateContactSyncTask(contactDAO).execute(contact);
    }

    public void delete(Contact contact){
        new DeleteContactSyncTask(contactDAO).execute(contact);
    }

    public void deleteAllContacts(){

        new DeleteAllContactSyncTask(contactDAO).execute();
    }

    public LiveData<List<Contact>> getAllContacts(){
        return allContacts;
    }

    private static class InsertContactSyncTask extends AsyncTask<Contact, Void, Void>{

        private ContactDAO contactDAO;

        private InsertContactSyncTask(ContactDAO contactDAO){
            this.contactDAO = contactDAO;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactSyncTask extends AsyncTask<Contact, Void, Void>{

        private ContactDAO contactDAO;

        private UpdateContactSyncTask(ContactDAO contactDAO){
            this.contactDAO = contactDAO;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactSyncTask extends AsyncTask<Contact, Void, Void>{

        private ContactDAO contactDAO;

        private DeleteContactSyncTask(ContactDAO contactDAO){
            this.contactDAO = contactDAO;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.delete(contacts[0]);
            return null;
        }
    }

    private static class DeleteAllContactSyncTask extends AsyncTask<Void, Void, Void>{

        private ContactDAO contactDAO;

        private DeleteAllContactSyncTask(ContactDAO contactDAO){
            this.contactDAO = contactDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDAO.deleteAllContacts();
            return null;
        }
    }
}
