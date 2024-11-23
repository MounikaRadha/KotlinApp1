package com.m0wn1ka.mysecurity1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment

val AppCompatActivity.dataStore2 by preferencesDataStore(name = "incidents")

class ClickTheButton : Fragment() {
        var contacts=arrayOf("1", "1", "1", "1")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var incidentNames: Array<String>;
        if (requireArguments().getStringArrayList("incidentsArray") == null) {
            incidentNames =
                arrayOf("Cristiano Ronaldo", "Joao Felix", "Bernado Silva", "Andre    Silve")
        } else {
            incidentNames = requireArguments().getStringArrayList("incidentsArray")
                ?.toTypedArray()!!
        }
        if (requireArguments().getStringArrayList("contacts") == null) {
            contacts = arrayOf("0", "0", "0", "0")
        } else {
            contacts = requireArguments().getStringArrayList("contacts")?.toTypedArray()!!
        }
        var incidentImages = intArrayOf(
            R.drawable.rgukt_image,
            R.drawable.vijayawada_dam,
            R.drawable.araku_tribes_mage,
            R.drawable.test_image
        )
        var view = inflater.inflate(R.layout.fragment_click_the_button, container, false)
        var gridView: GridView = view.findViewById(R.id.GridViewContainer)
        if (container != null) {
            var clickTheButtonAdapter =
                ClickTheButtonAdapter(container.context, incidentNames, incidentImages)
            gridView.adapter = clickTheButtonAdapter
        }
        return view
    }

    public fun clickButtonListner(incidentName: String, context: Context) {
        Log.d("clckTheButtonFragment", incidentName)
        val mainActivity=MainActivity()
        mainActivity.sendMessages(incidentName,context)
        Toast.makeText(context, incidentName, Toast.LENGTH_LONG).show()
    }
//    public fun sendMessages(incidentName: String,context: Context){
//        val smsManager: SmsManager
//        if (Build.VERSION.SDK_INT>=23) {
//            //if SDK is greater that or equal to 23 then
//            //this is how we will initialize the SmsManager
//                smsManager =context.getSystemService(SmsManager::class.java)//Todo check whether require context need to be replaced with context passed from parent or vice versa
//        }
//        else{
//            //if user's SDK is less than 23 then
//            //SmsManager will be initialized like this
//            smsManager = SmsManager.getDefault()
//        }
//        for(contact in contacts){
//            smsManager.sendTextMessage(contact,null,incidentName,null,null)
//            Toast.makeText(context,"send msg to "+contact,Toast.LENGTH_SHORT)
//        }
//
//    }
}