package com.example.turbocareassignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.turbocareassignment.R
import com.example.turbocareassignment.repository.roomdb.VehicleEntity

class VehicleListAdapter(var vehicleList: ArrayList<VehicleEntity>): RecyclerView.Adapter<VehicleListAdapter.VehicleListViewHolder>() {

    private lateinit var vehicleItemClickListener: VehicleItemClickListener

    interface VehicleItemClickListener {

        fun onVehicleItemClick(id: Long)
    }

    fun setVehicleItemClickListener(vehicleItemClickListener: VehicleItemClickListener) {

        this.vehicleItemClickListener = vehicleItemClickListener
    }

    fun updateTheList(vehicleList: ArrayList<VehicleEntity>) {

        this.vehicleList = vehicleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_vehicle_item, parent, false)
        return VehicleListViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {

        holder.bind(vehicleList[position])
    }

    override fun getItemCount(): Int {

        return vehicleList.size
    }

    inner class VehicleListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val vehicleItemCardView: CardView = itemView.findViewById(R.id.vehicleItemCardView)
        private val vehicleNameTextView: TextView = itemView.findViewById(R.id.vehicleNameTextView)
        private val vehicleNumberTextView: TextView = itemView.findViewById(R.id.vehicleNumberTextView)

        fun bind(entity: VehicleEntity) {

            val vehicleName = entity.manufacturer + " " + entity.model
            vehicleNameTextView.text = vehicleName
            vehicleNumberTextView.text = entity.vehicleNumber
            vehicleItemCardView.setOnClickListener {

                vehicleItemClickListener.onVehicleItemClick(entity.id)
            }
        }
    }
}