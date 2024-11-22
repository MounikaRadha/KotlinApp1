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
//        val url:String="https:///m0wn1ka.github.io"
//        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(urlIntent)
        loadFragment1()
//        activityMainBinding.AboutPageButton.setOnClickListener(){
//            startActivity(Intent(applicationContext,AboutPage::class.java))
//        }
        activityMainBinding.buttonTab2.setOnClickListener(){
            loadFragment2()
        }
        activityMainBinding.buttonTab1.setOnClickListener(){
            loadFragment1()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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