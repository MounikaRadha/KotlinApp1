package com.m0wn1ka.mysecurity1

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
import com.m0wn1ka.mysecurity1.databinding.ActivityIncidentsSavingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val AppCompatActivity.dataStore1 by preferencesDataStore(name = "incidents")
class IncidentsSaving : AppCompatActivity() {
    lateinit var incidentsSavingBinding:ActivityIncidentsSavingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        incidentsSavingBinding=ActivityIncidentsSavingBinding.inflate(getLayoutInflater())
        setContentView(incidentsSavingBinding.root)
        lifecycleScope.launch { saveDummyIncident();showUpdatedIncidentsList() }
        incidentsSavingBinding.SaveIncidentButton.setOnClickListener(){
            getAndSaveIncident()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private suspend fun showUpdatedIncidentsList(){
        var arrayAdapter: ArrayAdapter<*>
        arrayAdapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,retrieveIncidents())
        incidentsSavingBinding.IncidentsList.adapter = arrayAdapter
    }
    private fun getAndSaveIncident(){
        val incidentName:String=incidentsSavingBinding.IncidentName.text.toString()
        val incidentlevelNumber:Int=incidentsSavingBinding.LevelOfIncident.text.toString().toInt()
        saveIncident(incidentlevelNumber,incidentName)
        Toast.makeText(this, "incident saved", Toast.LENGTH_SHORT).show()
    }
     suspend fun retrieveIncidents(): ArrayList<String> {
        val incidents: ArrayList<String> = ArrayList()
        try {
            val preferences =dataStore.data.first()

            for (level in 1..3) {
                val keyOfIncident = stringPreferencesKey("$level :incident")
                val incident =preferences[keyOfIncident]
                if (incident!= null) {
                    incidents.add(incident)
                } else {
                    Log.d("retrievIncidents", "No incident found for level $level")
                }
            }
            val dummyKey = stringPreferencesKey("dummyKey")
            val dummy = preferences[dummyKey] ?: "Default Dummy Contact"
            incidents.add(dummy)
        } catch (e: Exception) {
            Log.e("retrieveContacts", "Error retrieving contacts: ${e.message}")
            runOnUiThread {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        return incidents
    }

    private fun saveDummyIncident(){
        lifecycleScope.launch{
            val dummyKey= stringPreferencesKey("dummyincidentKey")
            dataStore.edit { preferences->
                preferences[dummyKey]="test incident"
            }

        }
    }
    private fun saveIncident(level:Int,incident:String) {
        val Key_of_incident = stringPreferencesKey(level.toString()+" :incident")
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[Key_of_incident] = incident
            }
            showUpdatedIncidentsList();
        }
    }
}