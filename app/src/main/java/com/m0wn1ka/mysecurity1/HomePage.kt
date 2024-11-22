package com.m0wn1ka.mysecurity1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class HomePage : Fragment()  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var x = inflater.inflate(R.layout.fragment_home_page, container, false)
       val aboutButton: Button = x.findViewById(R.id.AboutPageButton)
        aboutButton.setOnClickListener(){
            if (container != null) {
                    startActivity(Intent(container.context, AboutPage::class.java))
            }
        }
        return x
    }

}