package com.m0wn1ka.mysecurity1

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.m0wn1ka.mysecurity1.databinding.ActivityContactsSavingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val AppCompatActivity.dataStore by preferencesDataStore(name = "contact_numbers")
class ContactsSaving : AppCompatActivity() {
    lateinit var contactsSavingBinding:ActivityContactsSavingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        contactsSavingBinding=ActivityContactsSavingBinding.inflate(getLayoutInflater())
        setContentView(contactsSavingBinding.root)
        lifecycleScope.launch { showUpdatedEmergencyContactList() }
        contactsSavingBinding.SaveContactButton.setOnClickListener(){
            getAndSaveContact()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
   private suspend fun showUpdatedEmergencyContactList(){
       var arrayAdapter: ArrayAdapter<*>
       arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,retrieveContacts())
       contactsSavingBinding.ContactsList.adapter = arrayAdapter
    }
    private fun getAndSaveContact(){
        val contactNumber:String=contactsSavingBinding.ContactNumber.text.toString()
        val contactLevelNumber:Int=contactsSavingBinding.LevelOfContact.text.toString().toInt()
        saveContact(contactLevelNumber,contactNumber)
        Toast.makeText(this, "contact saved", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            showUpdatedEmergencyContactList();
        }

    }

    private suspend fun retrieveContacts():MutableList<String>{
        val contactNumbers: MutableList<String> = ArrayList()
        try {
            for (level in 1..3) {
                val key_of_contact = stringPreferencesKey(level.toString() + " :person")
                val contactsData = dataStore.data.first()
                val contactNumber = contactsData[key_of_contact]
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    contactNumbers.addFirst(contactNumber)
                };
                else {
                    if (contactNumber != null) {
                        contactNumbers.add(-1, contactNumber)
                    }
                    Toast.makeText(this, "unsupported versoin", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch ( e:Exception){
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_LONG).show()
        }
        return contactNumbers
    }
    private fun saveContact(level:Int,contactNumber:String) {
        val Key_of_contact = stringPreferencesKey(level.toString()+" :person")
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[Key_of_contact] = contactNumber
            }
        }
    }
}