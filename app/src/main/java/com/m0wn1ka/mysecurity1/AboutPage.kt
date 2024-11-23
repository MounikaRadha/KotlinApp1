package com.m0wn1ka.mysecurity1
import android.widget.ArrayAdapter
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.m0wn1ka.mysecurity1.databinding.ActivityAboutPageBinding
import com.m0wn1ka.mysecurity1.databinding.ActivityMainBinding

class AboutPage : AppCompatActivity() {
    lateinit var activityAboutPageBinding:ActivityAboutPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityAboutPageBinding=ActivityAboutPageBinding.inflate(layoutInflater)
        setContentView(activityAboutPageBinding.AboutPageLayout)
        var arrayAdapter: ArrayAdapter<*>
        arrayAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,resources.getStringArray(R.array.about_page_array))
        activityAboutPageBinding.AboutPageList.adapter = arrayAdapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AboutPageLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}