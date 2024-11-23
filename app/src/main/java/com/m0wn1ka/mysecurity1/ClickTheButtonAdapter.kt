package com.m0wn1ka.mysecurity1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

internal class ClickTheButtonAdapter(
    private val context: Context,
    private val incidentNames: Array<String>,
    private val incidentImages: IntArray
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var incidentImage: ImageView
    private lateinit var incidentName: TextView
    override fun getCount(): Int {
        return incidentNames.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.single_incident, null)
        }
        incidentImage = convertView!!.findViewById(R.id.incidentImage)
        incidentName = convertView.findViewById(R.id.incidentName)
        incidentImage.setImageResource(incidentImages[position])
        incidentName.text = incidentNames[position]
        convertView.setOnClickListener(){
//            Toast.makeText(context, incidentNames[position], Toast.LENGTH_LONG).show()
            val clickTheButton=ClickTheButton();
            clickTheButton.clickButtonListner(incidentNames[position],context)
            Log.d("adapter",incidentNames[position])
        }
        return convertView
    }
}
