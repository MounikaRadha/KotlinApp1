package com.m0wn1ka.mysecurity1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView

class ClickTheButton : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         var incidentNames = arrayOf("Cristiano Ronaldo", "Joao Felix", "Bernado Silva", "Andre    Silve")
         var incidentImages = intArrayOf(R.drawable.test_image, R.drawable.rgukt_image, R.drawable.vijayawada_dam, R.drawable.araku_tribes_mage)
        var view= inflater.inflate(R.layout.fragment_click_the_button, container, false)
       var gridView:GridView= view.findViewById(R.id.GridViewContainer)
        if(container!=null) {
            var clickTheButtonAdapter = ClickTheButtonAdapter(container.context, incidentNames, incidentImages)
            gridView.adapter=clickTheButtonAdapter
        }
        return view
    }

}