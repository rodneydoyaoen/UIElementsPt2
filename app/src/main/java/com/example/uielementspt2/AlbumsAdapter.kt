package com.example.uielementspt2

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

@Suppress("NAME_SHADOWING")
class CustomAdapter(
        private var itemModel: ArrayList<Modal>,
        private var context: Context
) :
    BaseAdapter() {
    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return itemModel.size
    }
    override fun getItem(position: Int): Any? {
        return itemModel[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_item, parent, false)
        }
        var imageView = convertView?.findViewById<ImageView>(R.id.imageView)
        var textView = convertView?.findViewById<TextView>(R.id.textView)
        imageView?.setImageResource(itemModel[position].image!!)
        textView?.text = itemModel[position].name
        return convertView!!
    }
}