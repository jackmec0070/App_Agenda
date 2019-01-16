package com.everis.app_agenda.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.everis.app_agenda.R;
import com.everis.app_agenda.adapter.AdapterList;
import com.everis.app_agenda.model.Contact;
import com.everis.app_agenda.viewmodel.ContactViewModel;

import java.util.List;

public class ListFragment extends Fragment {

    private OnItemSelectedListener listener;
    private Context context;
    private AdapterList adapterList;
    private RecyclerView rv_list;
    private EditFragment editFragment;
    private ContactViewModel contactViewModel;

    public interface OnItemSelectedListener{
        void onItemSelected(Contact contact);
        Contact getContact();
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startVars(view);
        startAction();
    }

    private void startVars(View view) {
        context = getContext();
        rv_list = view.findViewById(R.id.rv_lista);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterList = new AdapterList();

        adapterList.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void OnItemClick(Contact contact) {
                listener.onItemSelected(contact);
            }
        });

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> contacts) {
                //update recyclerView
                adapterList.submitList(contacts);
                rv_list.setAdapter(adapterList);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv_list.setLayoutManager(layoutManager);
        rv_list.setHasFixedSize(true);
        rv_list.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        rv_list.setAdapter(adapterList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                contactViewModel.delete(adapterList.getContactAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Contato excluído!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv_list);

    }

    private void startAction() {
        getActivity().findViewById(R.id.btn_add_contact).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.tv_titulo_activity).setVisibility(View.VISIBLE);
    }
}
