package com.everis.app_agenda.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.everis.app_agenda.R;
import com.everis.app_agenda.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class AdapterList extends ListAdapter<Contact, AdapterList.MyViewHolder> {

    private Context context;
    //private List<Contact> contactList = new ArrayList<>();
    private OnItemClickListener listener;

    public AdapterList() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getPhone().equals(newItem.getPhone());
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView phone;
        Button btn_edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            phone = itemView.findViewById(R.id.tv_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    //public AdapterList(Context context) {
    //    this.context = context;
    //}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contact = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);

        return new MyViewHolder(contact);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact currentContact = getItem(position);

        holder.name.setText(currentContact.getName());
        holder.phone.setText(String.valueOf(currentContact.getPhone()));
    }

    public Contact getContactAt(int position) {
        return getItem(position);
    }

    public interface OnItemClickListener{
        void OnItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
