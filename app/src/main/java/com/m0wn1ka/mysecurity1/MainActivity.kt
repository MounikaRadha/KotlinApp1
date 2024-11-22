package com.m0wn1ka.mysecurity1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.m0wn1ka.mysecurity1.databinding.ActivityMainBinding

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
        fragmentTransaction.replace(R.id.frameLayout,clickTheButtonFragment)
        fragmentTransaction.commit()
    }
    fun loadFragment2(){
        //loads the page which has click the button features
        val fragmentManager: FragmentManager =supportFragmentManager
        val fragmentTransaction: FragmentTransaction =fragmentManager.beginTransaction()
        val clickTheButtonFragment=ClickTheButton()
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
}