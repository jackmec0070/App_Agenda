package com.everis.app_agenda.view;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.everis.app_agenda.R;
import com.everis.app_agenda.model.Contact;
import com.everis.app_agenda.model.ContactDAO;
import com.everis.app_agenda.model.ContactRepository;
import com.everis.app_agenda.viewmodel.ContactViewModel;

public class EditFragment extends Fragment {

    private ListFragment.OnItemSelectedListener listener;

    private Button btn_save;
    private EditText et_name;
    private EditText et_phone;
    private ListFragment listFragment;
    private ContactViewModel contactViewModel;
    private Contact contactEdit;
    private ContactActivity contactActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fg_contact_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciarVariaveis(view);
        iniciarAcao();
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof ListFragment.OnItemSelectedListener){
            listener = (ListFragment.OnItemSelectedListener) context;
        } else{
            throw new ClassCastException();
        }
    }

    private void iniciarVariaveis(View view) {
        btn_save = view.findViewById(R.id.btn_save);
        et_name = view.findViewById(R.id.et_name);
        et_phone = view.findViewById(R.id.et_phone);
        contactEdit = listener.getContact();
        contactViewModel = ViewModelProviders.of(getActivity()).get(ContactViewModel.class);
    }

    private void iniciarAcao() {
        getActivity().findViewById(R.id.btn_add_contact).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.tv_titulo_activity).setVisibility(View.INVISIBLE);

        if(contactEdit != null){
            et_name.setText(contactEdit.getName());
            et_phone.setText(contactEdit.getPhone());
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(getContext(), "Preencha todos os dados!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Contact contact = new Contact(name, phone);

                if (contactEdit != null){
                    contact.setId(contactEdit.getId());
                    contactViewModel.update(contact);
                    Toast.makeText(getContext(), "Contato atualizado!", Toast.LENGTH_SHORT).show();
                }else {
                    contactViewModel.insert(contact);
                    Toast.makeText(getContext(),"Contato adicionado!", Toast.LENGTH_SHORT).show();
                }

                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.cl_contact, new ListFragment());
                ft.commit();
            }
        });
    }

}
