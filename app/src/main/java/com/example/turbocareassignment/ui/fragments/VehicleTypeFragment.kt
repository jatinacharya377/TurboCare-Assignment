package com.example.turbocareassignment.ui.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.example.turbocareassignment.R
import java.lang.Exception

class VehicleTypeFragment : Fragment(), View.OnClickListener {

    private lateinit var bikeCardView: CardView
    private lateinit var carCardView: CardView
    private lateinit var continueCardView: CardView
    private lateinit var fourWheelerTextView: TextView
    private lateinit var twoWheelerTextView: TextView
    private lateinit var vehicleTypeListener: VehicleTypeListener
    private var isSelected: Boolean = false
    private var vehicleType: String = ""

    private fun changeCardBackgroundColor(bikeCardViewColor: Int, carCardViewColor: Int, fourWheelerTextColor: Int, twoWheelerTextColor: Int) {

        bikeCardView.setCardBackgroundColor(bikeCardViewColor)
        carCardView.setCardBackgroundColor(carCardViewColor)
        fourWheelerTextView.setTextColor(fourWheelerTextColor)
        isSelected = true
        twoWheelerTextView.setTextColor(twoWheelerTextColor)
    }

    private fun initializeOnClickListener() {

        bikeCardView.setOnClickListener(this)
        carCardView.setOnClickListener(this)
        continueCardView.setOnClickListener(this)
    }

    private fun initializeViews(view: View) {

        bikeCardView = view.findViewById(R.id.bikeCardView)
        carCardView = view.findViewById(R.id.carCardView)
        continueCardView = view.findViewById(R.id.continueCardView)
        fourWheelerTextView = view.findViewById(R.id.fourWheelerTextView)
        twoWheelerTextView = view.findViewById(R.id.twoWheelerTextView)
    }

    private fun setVehicleType(type: String) {

        vehicleType = type
    }

    fun resetTheValues() {

        isSelected = false
        vehicleType = ""
    }

    interface VehicleTypeListener {

        fun onContinue(isSelected: Boolean, vehicleType: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_vehicle_type, container, false)
        initializeViews(view)
        initializeOnClickListener()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {

            vehicleTypeListener = context as VehicleTypeListener

        } catch (e: Exception) {

            throw ClassCastException("$context must implement VehicleTypeListener!")

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.bikeCardView -> {

                context?.let {
                    changeCardBackgroundColor(
                        it.getColor(R.color.red_500),
                        it.getColor(R.color.white),
                        it.getColor(R.color.black),
                        it.getColor(R.color.white),
                    ) }
                setVehicleType("Two Wheeler")

            }

            R.id.carCardView -> {

                context?.let {
                    changeCardBackgroundColor(
                        it.getColor(R.color.white),
                        it.getColor(R.color.teal_500),
                        it.getColor(R.color.white),
                        it.getColor(R.color.black),
                    ) }
                setVehicleType("Four Wheeler")

            }

            R.id.continueCardView -> {

                vehicleTypeListener.onContinue(isSelected, vehicleType)

            }

        }

    }
}