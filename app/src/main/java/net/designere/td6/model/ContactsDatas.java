package net.designere.td6.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Créer par Francis Désignère le 19/02/2019.
 */
public class ContactsDatas {
    private ArrayList<Contact> mLstContacts;

    public ContactsDatas() {
        mLstContacts = new ArrayList<Contact>();
        initContactsList();
    }

    private void initContactsList () {
        mLstContacts.add(new Contact("Pierre", "Dupond", "12.34.56.78.90"));
        mLstContacts.add(new Contact("Paul", "Durand", "34.56.78.90.12"));
    }

    public ArrayList<Contact> getLstContacts() {
        return mLstContacts;
    }


    public void addContactsList (String pFirstName, String pName, String pPhoneNumber){
        mLstContacts.add(new Contact(pFirstName,pName,pPhoneNumber));
    }

    public void removeContacts (ArrayList<Contact> pContactsToDel){
        mLstContacts.removeAll(pContactsToDel);

    }

    public Contact getContactByPhone (String pSearchPhone){
        Log.d("debug","num tel Recherché : " + pSearchPhone);
        Iterator<Contact> iter = mLstContacts.iterator();
        while (iter.hasNext()){
            Contact contact = iter.next();
            if (contact.getPhoneNumber().equals(pSearchPhone)){
                return contact;
            }
        }
        return null;
    }

    public boolean ctrlUniquePhone(String pPhoneNumber ){
        for (Contact contact : mLstContacts){
            if (pPhoneNumber.equals(contact.getPhoneNumber())){
                return false;
            }
        }
        return true;
    }

}
