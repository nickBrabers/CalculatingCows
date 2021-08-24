package com.example.calculatingcows.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatingcows.R
import com.example.calculatingcows.data.Cow

class SimpleListAdapter : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    var data = listOf<Cow>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        //("Number: ${item.number} Age: ${item.age} Birthdate: ${item.birthDate}")

        val string = holder.res.getString(R.string.item, item.number, item.age, item.birthDate)
        holder.textView.text = string
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item, parent, false) as TextView
        return ViewHolder(view)
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        val res = textView.context.resources
    }
}


