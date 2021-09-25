package com.example.calculatingcows.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatingcows.R
import com.example.calculatingcows.data.Cow
import com.example.calculatingcows.databinding.CowItemBinding

class FancyListAdapter : ListAdapter<Cow, FancyListAdapter.FancyViewHolder>(MadeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FancyViewHolder {
        return FancyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FancyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class FancyViewHolder private constructor(private val binding: CowItemBinding): RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(parent: ViewGroup): FancyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CowItemBinding.inflate(layoutInflater, parent, false)
                return FancyViewHolder(binding)
            }
        }

        fun bind(item: Cow) {
            binding.textViewNumber.text = item.number.toString()
            binding.textViewAge.text = item.age.toString()
            binding.textViewDate.text = item.birthDate
            binding.executePendingBindings()
        }
    }
}
class MadeDiffUtil : DiffUtil.ItemCallback<Cow>() {
    override fun areItemsTheSame(oldItem: Cow, newItem: Cow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cow, newItem: Cow): Boolean {
        return oldItem == newItem
    }
}

class AdapterDataChanged(private val layoutManager: RecyclerView.LayoutManager): RecyclerView.AdapterDataObserver(){
    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        layoutManager.scrollToPosition(fromPosition)
        super.onItemRangeMoved(fromPosition, toPosition, itemCount)
    }
}


