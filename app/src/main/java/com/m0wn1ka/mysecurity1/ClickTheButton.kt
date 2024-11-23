package com.m0wn1ka.mysecurity1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment

val AppCompatActivity.dataStore2 by preferencesDataStore(name = "incidents")
class ClickTheButton : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var incidentNames:Array<String>;
       if(requireArguments().getStringArrayList("incidentsArray")==null){
              incidentNames = arrayOf("Cristiano Ronaldo", "Joao Felix", "Bernado Silva", "Andre    Silve")
       }
        else{
              incidentNames = requireArguments().getStringArrayList("incidentsArray")
                  ?.toTypedArray()!!
       }
        var contacts:Array<String>
        if(requireArguments().getStringArrayList("contacts")==null){
            contacts= arrayOf("0","0","0","0")
        }
        else{
            contacts=requireArguments().getStringArrayList("contacts")?.toTypedArray()!!
        }
         var incidentImages = intArrayOf( R.drawable.rgukt_image, R.drawable.vijayawada_dam, R.drawable.araku_tribes_mage,R.drawable.test_image)
        var view= inflater.inflate(R.layout.fragment_click_the_button, container, false)
       var gridView:GridView= view.findViewById(R.id.GridViewContainer)
        if(container!=null) {
            var clickTheButtonAdapter = ClickTheButtonAdapter(container.context, incidentNames, incidentImages)
            gridView.adapter=clickTheButtonAdapter
        }
        return view
    }
    public fun clickButtonListner( incidentName:String,context: Context){
    Log.d("clckTheButtonFragment",incidentName)
            Toast.makeText(context, incidentName, Toast.LENGTH_LONG).show()

    }

}