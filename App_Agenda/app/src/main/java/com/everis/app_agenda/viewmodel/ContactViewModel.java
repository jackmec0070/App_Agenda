package com.everis.app_agenda.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.everis.app_agenda.model.Contact;
import com.everis.app_agenda.model.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public void insert(Contact contact){
        repository.insert(contact);
    }

    public void update(Contact contact){

        repository.update(contact);
    }

    public void delete(Contact contact){

        repository.delete(contact);
    }

    public void deleteAllContacts(){

        repository.deleteAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts(){
        return allContacts;
    }
}
