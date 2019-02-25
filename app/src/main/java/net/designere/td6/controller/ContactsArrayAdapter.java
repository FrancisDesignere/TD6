package net.designere.td6.controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import net.designere.td6.R;
import net.designere.td6.model.Contact;
import java.util.ArrayList;

/**
 * Créer par Francis Désignère le 19/02/2019.
 */
public class ContactsArrayAdapter extends ArrayAdapter<Contact> {

    private ArrayList<Contact> lstContacts;
    public ArrayList<View> selectedViews = new ArrayList<View>();


    public ContactsArrayAdapter(Context context, ArrayList<Contact> lstContacts) {
        super(context, 0, lstContacts);
        this.lstContacts = lstContacts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_in_list_template,parent,false);
        }

        myViewHolder theViewHolder = (myViewHolder) convertView.getTag();
        if(theViewHolder == null){
            theViewHolder = new myViewHolder();
            theViewHolder.firstName = (TextView) convertView.findViewById(R.id.cilt_firstname);
            theViewHolder.LastName = (TextView) convertView.findViewById(R.id.cilt_name);
            theViewHolder.phoneNumber = (TextView) convertView.findViewById(R.id.cilt_phone );
            convertView.setTag(theViewHolder);
        }

        // récupération des données dans la liste contact et mis à jour du viewHolder
        Contact contact = getItem(position);
        theViewHolder.firstName.setText(contact.getFirstName());
        theViewHolder.LastName.setText(contact.getName());
        theViewHolder.phoneNumber.setText(contact.getPhoneNumber());

        // pose d'une action click (pour selection du delete)
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug", "clic vue : compte selectedViews avant "+selectedViews.size());// TODO a enlever

                if (!selectedViews.contains(v)) {
                    Log.d("debug", "clic vue : à ajouter");// TODO a enlever
                    selectedViews.add(v);
                    v.setBackgroundColor(Color.RED);
                }else{
                    Log.d("debug", "clic vue : à retirer");// TODO a enlever
                    selectedViews.remove(v);
                    v.setBackgroundColor(Color.WHITE);
                }
                Log.d("debug", "clic vue : compte selectedViews apres "+selectedViews.size());// TODO a enlever
            }
        });

        convertView.setBackgroundColor(Color.WHITE); // TODO à valider
        return convertView;
    }

    private class myViewHolder{
        public TextView firstName;
        public TextView LastName;
        public TextView phoneNumber;

        @Override
        public String toString() {
            return (String) this.phoneNumber.getText();
        }
    }
}
