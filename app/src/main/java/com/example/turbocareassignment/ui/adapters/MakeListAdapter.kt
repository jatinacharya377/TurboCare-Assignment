package com.example.turbocareassignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.turbocareassignment.R

class MakeListAdapter(var itemList: ArrayList<String>): RecyclerView.Adapter<MakeListAdapter.MakeItemViewHolder>() {

    private lateinit var makeItemClickListener: MakeItemClickListener

    interface MakeItemClickListener {

        fun onMakeItemClick(item: String)
    }

    fun setMakeItemClickListener(makeItemClickListener: MakeItemClickListener) {

        this.makeItemClickListener = makeItemClickListener
    }

    fun updateTheList(itemList: ArrayList<String>) {

        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_make_item, parent, false)
        return MakeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakeItemViewHolder, position: Int) {

        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

    inner class MakeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val makeItemCardView: CardView = itemView.findViewById(R.id.makeItemCardView)
        private val makeTitleTextView: TextView = itemView.findViewById(R.id.makeTitleTextView)

        fun bind(item: String) {

            makeTitleTextView.text = item
            makeItemCardView.setOnClickListener {

                makeItemClickListener.onMakeItemClick(itemList[absoluteAdapterPosition])
            }
        }
    }
}