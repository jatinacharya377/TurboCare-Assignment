package com.example.turbocareassignment.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.turbocareassignment.R
import com.example.turbocareassignment.repository.roomdb.VehicleEntity
import com.example.turbocareassignment.viewmodel.VehicleViewModel

class VehicleProfileActivity : AppCompatActivity() {

    private lateinit var backButtonCardView: CardView
    private lateinit var fuelTypeTextView: TextView
    private lateinit var makeTextView: TextView
    private lateinit var modelTextView: TextView
    private lateinit var transmissionTextView: TextView
    private lateinit var vehicleNameTextView: TextView
    private lateinit var vehicleNumberTextView: TextView
    private lateinit var vehicleViewModel: VehicleViewModel
    private var id: Long = 0

    private fun initializeViews() {

        backButtonCardView = findViewById(R.id.backButtonCardView)
        fuelTypeTextView = findViewById(R.id.fuelTypeTextView)
        makeTextView = findViewById(R.id.makeTextView)
        modelTextView = findViewById(R.id.modelTextView)
        transmissionTextView = findViewById(R.id.transmissionTextView)
        vehicleNameTextView = findViewById(R.id.vehicleNameTextView)
        vehicleNumberTextView = findViewById(R.id.vehicleNumberTextView)
        val intent = intent
        id = intent.getLongExtra("id", -1)
        vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_profile)

        initializeViews()
    }

    override fun onResume() {
        super.onResume()

        backButtonCardView.setOnClickListener { finish() }
        vehicleViewModel.getVehicleProfile(id).observe(this, object: Observer<VehicleEntity> {

            override fun onChanged(entity: VehicleEntity?) {

                fuelTypeTextView.text = entity?.fuelType
                makeTextView.text = entity?.manufacturer
                modelTextView.text = entity?.model
                transmissionTextView.text = entity?.transmission
                val vehicleName = entity?.model + " " + entity?.fuelType
                vehicleNameTextView.text = vehicleName
                vehicleNumberTextView.text = entity?.vehicleNumber
            }
        })
    }
}