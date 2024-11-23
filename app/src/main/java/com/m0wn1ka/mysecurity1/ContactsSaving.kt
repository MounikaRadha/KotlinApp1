package com.m0wn1ka.mysecurity1

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.m0wn1ka.mysecurity1.databinding.ActivityContactsSavingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val AppCompatActivity.dataStore by preferencesDataStore(name = "contact_numbers")
class ContactsSaving : AppCompatActivity() {
    lateinit var contactsSavingBinding:ActivityContactsSavingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        contactsSavingBinding=ActivityContactsSavingBinding.inflate(getLayoutInflater())
        setContentView(contactsSavingBinding.root)
        lifecycleScope.launch { saveDummyContact();showUpdatedEmergencyContactList() }
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
    }
    private suspend fun retrieveContacts(): MutableList<String> {
        val contactNumbers: MutableList<String> = ArrayList()
        try {
            val preferences =dataStore.data.first()

            for (level in 1..3) {
                val keyOfContact =stringPreferencesKey("$level :person")
                val contactNumber =preferences[keyOfContact]
                if (contactNumber!= null) {
                    contactNumbers.add(contactNumber)
                } else {
                    Log.d("retrieveContacts", "No contact found for level $level")
                }
            }
            val dummyKey =stringPreferencesKey("dummyKey")
            val dummy = preferences[dummyKey] ?: "Default Dummy Contact"
            contactNumbers.add(dummy)
        } catch (e: Exception) {
            Log.e("retrieveContacts", "Error retrieving contacts: ${e.message}")
            runOnUiThread {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        return contactNumbers
    }

    private fun saveDummyContact(){
        lifecycleScope.launch{
            val dummyKey= stringPreferencesKey("dummyKey")
            dataStore.edit { preferences->
                preferences[dummyKey]="123123123"
            }

        }
    }
    private fun saveContact(level:Int,contactNumber:String) {
        val Key_of_contact = stringPreferencesKey(level.toString()+" :person")
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[Key_of_contact] = contactNumber
            }
            showUpdatedEmergencyContactList();
        }
    }
}