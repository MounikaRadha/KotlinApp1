package com.m0wn1ka.mysecurity1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class PreFeed : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_pre_feed, container, false)
        var saveContactsButton: Button=view.findViewById(R.id.ContactSavingButton)
        saveContactsButton.setOnClickListener(){
            if (container != null) {
                startActivity(Intent(container.context, ContactsSaving::class.java))
            }
        }
        var saveIncidentsButton:Button=view.findViewById(R.id.IncidentsButton)
        saveIncidentsButton.setOnClickListener(){
            if(container!=null){
                startActivity(Intent(container.context,IncidentsSaving::class.java))
            }
        }
        return view
    }

}