package com.everis.app_agenda.view;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.everis.app_agenda.R;
import com.everis.app_agenda.model.Contact;
import com.everis.app_agenda.model.ContactDAO;
import com.everis.app_agenda.viewmodel.ContactViewModel;

public class ContactActivity extends AppCompatActivity implements ListFragment.OnItemSelectedListener {

    private Context context;
    private ListFragment listFragment;
    private EditFragment editFragment;
    private Button btn_add;
    private Contact contactEdit = null;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        startVars();
        startAction();
    }


    private void startVars() {
        context = ContactActivity.this;
        btn_add = findViewById(R.id.btn_add_contact);

        listFragment = new ListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.cl_contact, listFragment);
        transaction.commit();

    }

    private void startAction() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactEdit = null;

                FragmentManager fm = getSupportFragmentManager();

                Fragment fg = fm.findFragmentById(R.id.cl_contact);
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fg);
                ft.commit();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.cl_contact, new EditFragment());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemSelected(Contact contact) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.cl_contact, new EditFragment());
        ft.commit();

        contactEdit = contact;
    }

    @Override
    public Contact getContact() {
        return contactEdit;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.cl_contact, new ListFragment());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja realmente excluir todos contatos?");
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contactViewModel.deleteAllContacts();
                        Toast.makeText(getApplicationContext(), "Todos contatos foram excluídos!", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancelar", null);

                dialog.create();
                dialog.show();

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
