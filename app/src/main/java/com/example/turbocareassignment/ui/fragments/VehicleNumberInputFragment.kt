package com.example.turbocareassignment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.turbocareassignment.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class VehicleNumberInputFragment: Fragment() {

    private lateinit var vehicleNumberEditText: EditText
    private lateinit var nextFab: FloatingActionButton
    private lateinit var vehicleNoFragmentListener: VehicleNoFragmentListener

    private fun initializeViews(view: View) {

        nextFab = view.findViewById(R.id.nextFab)
        vehicleNumberEditText = view.findViewById(R.id.vehicleNumberEditText)
    }

    interface VehicleNoFragmentListener {

        fun onNext(vehicleNumber: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_vehicle_number_input, container, false)
        initializeViews(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {

            vehicleNoFragmentListener = context as VehicleNoFragmentListener

        } catch (e: Exception) {

            throw ClassCastException("$context must implement VehicleNoFragmentListener!")

        }

    }

    override fun onResume() {
        super.onResume()

        nextFab.setOnClickListener {

            vehicleNoFragmentListener.onNext(vehicleNumberEditText.text.toString())
        }
    }
}