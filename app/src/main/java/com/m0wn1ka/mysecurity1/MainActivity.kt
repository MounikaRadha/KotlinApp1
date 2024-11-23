package com.m0wn1ka.mysecurity1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.m0wn1ka.mysecurity1.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.root)
        loadFragment1()
        activityMainBinding.buttonTab2.setOnClickListener(){ loadFragment2() }
        activityMainBinding.buttonTab1.setOnClickListener(){ loadFragment3() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun loadFragment3(){
        //load the page `start using the app`
        //the page shows users to save contact numbers and incidents
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction: FragmentTransaction =fragmentManager.beginTransaction()
        val clickTheButtonFragment=PreFeed()
        fragmentTransaction.replace(R.id.frameLayout, clickTheButtonFragment)
        fragmentTransaction.commit()
    }
    fun loadFragment2(){
        //ask for sms permissions
        requestSmsPermission()
        //loads the page which has click the button features
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction: FragmentTransaction =fragmentManager.beginTransaction()
//        val clickTheButtonFragment=ClickTheButton()
        val incidents:ArrayList<String>
        val contacts:ArrayList<String>
        runBlocking {
            incidents=retrieveIncidents();
            contacts=retrieveContacts();
        }
        val bundle = Bundle()
        bundle.putStringArrayList("incidentsArray",incidents)
        bundle.putStringArrayList("contactsArray",contacts)
        val clickTheButtonFragment: ClickTheButton = ClickTheButton()
        clickTheButtonFragment.setArguments(bundle)
        fragmentTransaction.replace(R.id.frameLayout,clickTheButtonFragment)
        fragmentTransaction.commit()
    }
    fun loadFragment1(){
        //loads the home page
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction: FragmentTransaction =fragmentManager.beginTransaction()
        val homeFragment=HomePage()
        fragmentTransaction.replace(R.id.frameLayout,homeFragment)
        fragmentTransaction.commit()
    }
    public suspend fun retrieveIncidents(): ArrayList<String> {
        val incidents: ArrayList<String> = ArrayList()
        try {
            val preferences =dataStore.data.first()
            val dummyKey = stringPreferencesKey("dummyKey")
            val dummy = preferences[dummyKey] ?: "Default Dummy incident"
            incidents.add(dummy)
            for (level in 1..3) {
                val keyOfIncident = stringPreferencesKey("$level :incident")
                val incident =preferences[keyOfIncident]
                if (incident!= null) {
                    incidents.add(incident)
                } else {
                    Log.d("retrievIncidents", "No incident found for level $level")
                }
            }

        } catch (e: Exception) {
            Log.e("retrieveIncidents", "Error retrieving incident: ${e.message}")
            runOnUiThread {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        return incidents
    }
    private suspend fun retrieveContacts(): ArrayList<String> {
        val contactNumbers: ArrayList<String> = ArrayList()
        try {
            val preferences =dataStore.data.first()
            val dummyKey =stringPreferencesKey("dummyKey")
            val dummy = preferences[dummyKey] ?: "Default Dummy Contact"
            contactNumbers.add(dummy)
            for (level in 1..3) {
                val keyOfContact =stringPreferencesKey("$level :person")
                val contactNumber =preferences[keyOfContact]
                if (contactNumber!= null) {
                    contactNumbers.add(contactNumber)
                } else {
                    Log.d("retrieveContacts", "No contact found for level $level")
                }
            }

        } catch (e: Exception) {
            Log.e("retrieveContacts", "Error retrieving contacts: ${e.message}")
            runOnUiThread {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        return contactNumbers
    }


    private fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    // Function to request permission
    private fun requestSmsPermission() {
        if (!isSmsPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                101
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "SMS permission given.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "SMS permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    public fun sendMessages(incidentName: String,context: Context){
        try {
        val smsManager: SmsManager =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }
       lifecycleScope.launch {
           var contacts= retrieveContacts()
           for(contact in contacts){
               smsManager.sendTextMessage(contact,null,incidentName,null,null)
               Toast.makeText(context,"send msg to "+contact,Toast.LENGTH_SHORT)
               Log.d("sms","sms of"+contact)
           }
        }
       }
        catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT)
        }

    }

}