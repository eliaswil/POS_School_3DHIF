package at.htlkaindorf.exa_q2_201_contactsapp.bl;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.exa_q2_201_contactsapp.ContactViewHolder;
import at.htlkaindorf.exa_q2_201_contactsapp.MainActivity;
import at.htlkaindorf.exa_q2_201_contactsapp.R;
import at.htlkaindorf.exa_q2_201_contactsapp.io.IOHandler;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<Contact> contacts;
    private List<Contact> filteredContacts;
    public Contact currentContact;

    public ContactAdapter() {
        try {
            this.contacts = IOHandler.readContacts();
        } catch (IOException e) {
            Toast.makeText(MainActivity.mainContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.filteredContacts = new ArrayList<>(contacts);
        sortContacts();
    }

    private void sortContacts(){
        filteredContacts.sort(Comparator.comparing(Contact::getLastname).thenComparing(Contact::getFirstname));
    }

    public void filterContacts(String filter){
        filteredContacts.clear();
        filteredContacts.addAll(contacts);

        filteredContacts.removeIf(contact -> !(contact.getFirstname().toUpperCase().contains(filter.toUpperCase())
                || contact.getLastname().toUpperCase().contains(filter.toUpperCase())));
        sortContacts();
        notifyDataSetChanged();
    }
    public void delete(){

        this.contacts.remove(currentContact);
        this.filteredContacts = new ArrayList<>(contacts);
        sortContacts();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        ImageView ivPicture = view.findViewById(R.id.ivProfilePic);
        TextView tvName = view.findViewById(R.id.tvName);
        LinearLayout linearLayout = view.findViewById(R.id.llParent);

        ContactViewHolder contactViewHolder = new ContactViewHolder(view, ivPicture, tvName, linearLayout);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = filteredContacts.get(position);
        holder.setContact(contact);
        Picasso.get().load(contact.getPicture().toString())
                .placeholder(R.drawable.testpic)
                .into(holder.getIvPicture());   // braucht mit Placeholder etwas länger beim Laden
        String name = contact.getLastname() + ", " + contact.getFirstname();
        holder.getTvName().setText(name);
    }

    @Override
    public int getItemCount() {
        return filteredContacts.size();
    }





}
