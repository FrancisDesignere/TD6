package net.designere.td6.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.designere.td6.R;
import net.designere.td6.model.Contact;
import net.designere.td6.model.ContactsDatas;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CLE_BUNDLE_LST_CONTACT = "LST_CONTACT";

    private Button mBtnAdd;
    private Button mBtnDel;
    private ListView mLvContact;
    private ContactsDatas mContactsDatas = new ContactsDatas();
    private ArrayList<Contact> lstContacts;
    private ContactsArrayAdapter adapter;
    private String msFirstName;
    private String msName;
    private String msPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState != null ){
            lstContacts = savedInstanceState.getParcelableArrayList(CLE_BUNDLE_LST_CONTACT);
        }
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.ma_btnAjout);
        mBtnAdd.setOnClickListener(this);

        mBtnDel = findViewById(R.id.ma_btnSuppr);
        mBtnDel.setOnClickListener(this);

        mLvContact = findViewById(R.id.ma_listView);

        if (lstContacts == null) {
            lstContacts = mContactsDatas.getLstContacts();
            Log.d("debug","onCreate dans if lstContact size : " +lstContacts.size()); // TODO à enlever
        }
        Log.d("debug","onCreate apres if lstContact size : " +lstContacts.size()); // TODO à enlever
        adapter = new ContactsArrayAdapter(this,lstContacts);
        mLvContact.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if ( v.getId()== R.id.ma_btnAjout){
            addContact();

        }else if(v.getId()== R.id.ma_btnSuppr){
            if(lstContacts.size()>0){
                deletion();
            }else{
                Toast.makeText(this,getString(R.string.alertDialogDelNothing_msg),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addContact(){
        AlertDialog addDialog  = createDialog();
        addDialog.show();

        onPause();

        Log.d("debug","valeur dans addContact : " + msName + msFirstName + msPhone);
/*      // j'ai tenté de ne fait l'ajout de contact ici, mais sans succes
        //  en effet, le code s'execute ici sans attendre le retour de la boite de dialog !!
        //      je ne sais pas comment faire autrement
        Log.d("debug","valeur dans addContact : " + msName + msFirstName + msPhone);
        lstContacts.add(new Contact(msName, msFirstName, msPhone));
        adapter.notifyDataSetChanged();
*/
    }

    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView = inflater.inflate(R.layout.dialog_add_contact, null);
        builder.setView(dialogView);

        final TextView tvFirstName = dialogView.findViewById(R.id.dialog_add_firstname);
        final TextView tvName = dialogView.findViewById(R.id.dialog_add_name);
        final TextView tvPhone = dialogView.findViewById(R.id.dialog_add_phonenumber);

        builder.setPositiveButton(getString(R.string.dialogAdd_btnAdd_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    msName = tvName.getText().toString();
                    msFirstName = (String) tvFirstName.getText().toString();
                    msPhone = (String) tvPhone.getText().toString();
                    Log.d("debug","valeur sur clic dialog : " + msName + msFirstName + msPhone);

                    if (mContactsDatas.ctrlUniquePhone(msPhone)){
                        lstContacts.add(new Contact(msName, msFirstName, msPhone));
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getApplicationContext(),getString(R.string.contact_already_exists),Toast.LENGTH_LONG).show();
                    }
                }
            })
            .setNegativeButton(getString(R.string.alertDialog_btnCancel_label), null);
        return builder.create();
    }

    private void deletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alertDialogDel_title));
        builder.setIcon(android.R.drawable.ic_menu_info_details);
        if(adapter.selectedViews.size()==0){
            builder.setMessage(getString(R.string.alertDialogDelAll_msg));
            builder.setPositiveButton(getString(R.string.alertDialogDel_btnDel_label),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lstContacts.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
            );

        }else{
            builder.setMessage(getString(R.string.alertDialogDelSelection_msg) + adapter.selectedViews.size());
            builder.setPositiveButton(getString(R.string.alertDialogDel_btnDel_label),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("debug", "nombre d'element selectionnés : " + adapter.selectedViews.size());// TODO a enlever
                            for (View vSel : adapter.selectedViews){
                                Log.d("debug", "taille lstContact avant : "+lstContacts.size());// TODO a enlever
                                Contact selectedContact = mContactsDatas.getContactByPhone(vSel.getTag().toString());
                                lstContacts.remove(selectedContact);
                                Log.d("debug", "taille lstContact après : "+lstContacts.size());// TODO a enlever
                            }
                            adapter.selectedViews.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
            );
        }

        builder.setNegativeButton(getString(R.string.alertDialog_btnCancel_label),null);
        builder.create();
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(CLE_BUNDLE_LST_CONTACT, lstContacts);
        Log.d("debug","onSaveInstanceState lstContact size : " +lstContacts.size());
    }

    // surcharge des methodes callBack pour loguer les changements d'états
    //      penser à :
    //          - ajouter le log dans le on create
    //          - enlever ces methodes d'ici si déjà overridées ailleurs
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debugEtat","Game Activity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debugEtat","Game Activity onResume");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debugEtat","Game Activity onStop");
    }
    @Override
    protected void onPause() {
        Log.d("debugEtat","Game Activity onPause");
        super.onPause();
    }
    @Override
    protected void onRestart() {
        Log.d("debugEtat","Game Activity onRestart");
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        Log.d("debugEtat","Game Activity onDestroy");
        super.onDestroy();
    }

}
